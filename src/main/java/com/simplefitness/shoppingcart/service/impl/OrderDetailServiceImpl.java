package com.simplefitness.shoppingcart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.shoppingcart.domain.OrderDetail;
import com.simplefitness.shoppingcart.repository.IdvProductRepository;
import com.simplefitness.shoppingcart.repository.OrderDetailRepository;
import com.simplefitness.shoppingcart.repository.ProductRepository;
import com.simplefitness.shoppingcart.service.OrderDetailService;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private IdvProductRepository idvProductRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.selectAll();
	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
		List<OrderDetail> orderDetails = orderDetailRepository.selectByOrderId(orderId);
		for (int i = 0; i < orderDetails.size(); i++) {
			orderDetails.get(i).setProdName(productRepository.selectById(idvProductRepository.selectById(orderDetails.get(i).getIdvId()).getProdId()).getProdName());
		}
		return orderDetails;
	}

}
