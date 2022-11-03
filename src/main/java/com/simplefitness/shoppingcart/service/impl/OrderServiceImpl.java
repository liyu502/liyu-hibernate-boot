package com.simplefitness.shoppingcart.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.gym.repository.GymRepository;
import com.simplefitness.member.repository.MemberRepository;
import com.simplefitness.shoppingcart.domain.Order;
import com.simplefitness.shoppingcart.domain.OrderDetail;
import com.simplefitness.shoppingcart.repository.IdvProductRepository;
import com.simplefitness.shoppingcart.repository.OrderRepository;
import com.simplefitness.shoppingcart.repository.OrderDetailRepository;
import com.simplefitness.shoppingcart.service.OrderService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository oDetailRepository;
	@Autowired
	private IdvProductRepository idvProductRepository;
	@Autowired
	private GymRepository gymRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	public static AllInOne allInOne;

	@Override
	public List<Order> findAll() {
		return orderRepository.selectAll();
	}

	@Override
	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize) {
		List<Order> orders = orderRepository.selectByMem(memId, pageNo, pageSize);
		Long totalCount = orderRepository.findCountByMem(memId);
		int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);
		for (int i = 0; i < orders.size(); i++) {
			 orders.get(i).setGymName(gymRepository.selectById(orders.get(i).getGymId()).getGymName());
			 orders.get(i).setPageNo(pageNo);
			 orders.get(i).setTotalPage(totalPage);
		}
		 
		return orders;
	}

	@Override
	public Order addOrder(Order order) {
		int success = 0;
		for (int i = 0; i < order.getOrderList().size(); i++) {
			Long count = idvProductRepository.selectCount(order.getGymId(), order.getOrderList().get(i).getProdId());
			Integer inCart = order.getOrderList().get(i).getInCart();
			if(inCart <= count) {
				success += 1;
 			}
		}
		
		if(success < order.getOrderList().size()) {
			order.setMessage("租借數量大於庫存，請重新選取");
			order.setSuccessful(false);
			return order;
		}
		
		
		order.setStatus(2);
		Integer orderId = orderRepository.insertGetOrderId(order);

		for (int i = 0; i < order.getOrderList().size(); i++) {
			for (int j = 0; j < order.getOrderList().get(i).getInCart(); j++) {
				oDetailRepository.insert2(orderId, order.getGymId(), order.getOrderList().get(i).getProdId());
				Integer detailId = oDetailRepository.maxDetailId();
				idvProductRepository.updateStatus(2, oDetailRepository.selectById(detailId).getIdvId());
			}
		}

		
		order.setMemName(memberRepository.selectById(order.getMemId()).getMemName());
		order.setMemEmail(memberRepository.selectById(order.getMemId()).getEmail());
		
			try {
				sendEmail(order);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		
		order.setSuccessful(true);
		order.setMessage("租借成功");
		
		if("信用卡支付".equals(order.getPayfor())) {
			order.setStatus(1);
			ecpayValidation(order);
		}

		return order;
		
	}
	
	@Value("${spring.mail.username}")
    private String from;
	private void sendEmail(Order order)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = order.getMemEmail();
		String senderName = "Simple Fitness";
		String subject = "訂單成立通知";
		String content = "<html><body>Hi, [[name]] 感謝您的預約 <br>本次租借金額為 $"+ order.getAmount() + "，請至會員中心查看：<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">查詢我的訂單</a></h3>" + "Thank you.<br><img src='cid:picture' width='150' /></body></html>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(from, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", order.getMemName());
		content = content.replace("[[URL]]", "http://127.0.0.1:5500/html/order.html");

		helper.setText(content, true); // true:HTML
		FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/logo.png"));
		helper.addInline("picture", file);

		ExecutorService executor = Executors.newWorkStealingPool();

		executor.execute(() -> mailSender.send(message));
	}

	@Override
	public boolean cancelOrder(Order order) {
		order.setStatus(0);
		orderRepository.updateStatus(order);
		List<OrderDetail> idvIds = oDetailRepository.selectIdvId(order.getOrderId());
		for(int i = 0; i < idvIds.size(); i++) {
			idvProductRepository.updateStatus(1, idvIds.get(i).getIdvId());
		}
		return true;
	}

	@Override
	public Order ecpayValidation(Order order) {
		List<String> productName = order.getOrderList().stream().map(e->e.getProdName()).collect(Collectors.toList());
		
		Optional<String> reduce = productName.stream().reduce((String acc, String curr) -> {
			return acc + "#" + curr;
		});
		String name = reduce.get();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //綠界規定日期模式
		String orderDate = sdf.format(new Date());
		AllInOne allInOne = new AllInOne("");
		AioCheckOutALL aioCheckOutALL = new AioCheckOutALL();
		aioCheckOutALL.setMerchantTradeNo("SF" + order.getOrderId());  //訂單編號必須數字+英文字母 
		System.out.println("OrderID: "+ order.getOrderId());
		aioCheckOutALL.setMerchantTradeDate(orderDate);
		aioCheckOutALL.setTotalAmount(order.getAmount() +""); //加總
		aioCheckOutALL.setTradeDesc("test"); //訂單描述
		aioCheckOutALL.setItemName(name); //商品名稱
		aioCheckOutALL.setClientBackURL("http://127.0.0.1:5500/html/rentFront.html"); //按個按鈕返回商城
		aioCheckOutALL.setReturnURL("http://127.0.0.1:5500/html/rentFront.html"); //結束後回到自動商城
		aioCheckOutALL.setNeedExtraPaidInfo("N");
		
		String checkValue = allInOne.aioCheckOut(aioCheckOutALL, null);
		order.setAio(aioCheckOutALL);
		order.setMessage(checkValue);
		return order;
		
	}

	@Override
	public Order selectById(Integer orderId) {
		Order order =  orderRepository.selectById(orderId);
		order.setMemName(memberRepository.selectById(order.getMemId()).getMemName());
		order.setGymName(gymRepository.selectById(order.getGymId()).getGymName());
		return order;
		
	}

}
