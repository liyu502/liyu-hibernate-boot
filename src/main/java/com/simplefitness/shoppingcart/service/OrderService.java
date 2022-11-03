package com.simplefitness.shoppingcart.service;

import java.util.List;

import com.simplefitness.shoppingcart.domain.Order;


public interface OrderService {

	public List<Order> findAll();

	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize);
	
	public boolean cancelOrder(Order order);

	public Order addOrder(Order order);
	
	public Order ecpayValidation(Order order);
	
	public Order selectById(Integer orderId) ;
}
