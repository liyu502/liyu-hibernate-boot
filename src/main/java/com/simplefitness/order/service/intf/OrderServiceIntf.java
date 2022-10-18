package com.simplefitness.order.service.intf;

import java.util.List;

import com.simplefitness.order.vo.Order;
import com.simplefitness.order.vo.PageVo;


public interface OrderServiceIntf {

	public List<Order> findAll();

	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize);
	
	public boolean cancelOrder(Order order);

	public Order addOrder(Order order);
}
