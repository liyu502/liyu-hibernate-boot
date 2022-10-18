package com.simplefitness.order.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.gym.dao.intf.GymDaoIntf;
import com.simplefitness.idvproduct.dao.intf.IdvProductDaoIntf;
import com.simplefitness.mem.dao.intf.MemDaoIntf;
import com.simplefitness.mem.vo.Member;
import com.simplefitness.order.dao.intf.OrderDaoIntf;
import com.simplefitness.order.service.intf.OrderServiceIntf;
import com.simplefitness.order.vo.Order;
import com.simplefitness.order.vo.PageVo;
import com.simplefitness.orderdetail.dao.intf.OrderDetailDaoIntf;

@Service
@Transactional
public class OrderServiceImpl implements OrderServiceIntf {
	@Autowired
	private OrderDaoIntf dao;
	@Autowired
	private OrderDetailDaoIntf detailDao;
	@Autowired
	private IdvProductDaoIntf idvDao;
	@Autowired
	private GymDaoIntf gymDao;
	@Autowired
	private MemDaoIntf memDao;
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public List<Order> findAll() {
		return dao.selectAll();
	}

	@Override
	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize) {
		List<Order> orders = dao.selectByMem(memId, pageNo, pageSize);
		Long totalCount = dao.findCountByMem(memId);
		int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);
		for (int i = 0; i < orders.size(); i++) {
			 orders.get(i).setGymName(gymDao.selectById(orders.get(i).getGymId()).getGymName());
			 orders.get(i).setPageNo(pageNo);
			 orders.get(i).setTotalPage(totalPage);
		}
		 
//		PageVo<Order> po = new PageVo<Order>();
//		po.setPageNo(pageNo);
//		po.setPageSize(pageSize);
//		po.setContent(orders);
//		
//		
//		po.setTotalPage(totalPage);
//		po.setTotalCount(totalCount);
//			
		return orders;
	}

	@Override
	public Order addOrder(Order order) {
		int success = 0;
		for (int i = 0; i < order.getOrderList().size(); i++) {
			Long count = idvDao.selectCount(order.getGymId(), order.getOrderList().get(i).getProdId());
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
		Integer orderId = dao.insertGetOrderId(order);

		for (int i = 0; i < order.getOrderList().size(); i++) {
			for (int j = 0; j < order.getOrderList().get(i).getInCart(); j++) {
				detailDao.insert2(orderId, order.getGymId(), order.getOrderList().get(i).getProdId());
				Integer detailId = detailDao.maxDetailId();
				idvDao.updateStatus(2, detailDao.selectById(detailId).getIdvId());
			}
		}

		
		order.setMemName(memDao.selectById(order.getMemId()).getMemName());
		order.setMemEmail(memDao.selectById(order.getMemId()).getEmail());
		
			try {
				sendEmail(order);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		
		order.setSuccessful(true);
		order.setMessage("租借成功");
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
		return dao.updateStatus(order);
	}

}
