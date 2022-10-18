package com.simplefitness.orderdetail.service.intf;

import java.util.List;

import com.simplefitness.orderdetail.vo.OrderDetail;

public interface OrderDetailServiceIntf {
	
	public List<OrderDetail> findAll();
	public List<OrderDetail> selectByOrderId(Integer orderId);
	
}
