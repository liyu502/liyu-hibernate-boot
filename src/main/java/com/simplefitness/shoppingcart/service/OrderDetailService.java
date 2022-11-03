package com.simplefitness.shoppingcart.service;

import java.util.List;

import com.simplefitness.shoppingcart.domain.OrderDetail;

public interface OrderDetailService {
	
	public List<OrderDetail> findAll();
	public List<OrderDetail> selectByOrderId(Integer orderId);
	
}
