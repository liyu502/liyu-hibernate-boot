package com.simplefitness.shoppingcart.repository;

import java.util.List;

import com.simplefitness.common.CommonDao;
import com.simplefitness.shoppingcart.domain.OrderDetail;


public interface OrderDetailRepository extends CommonDao<OrderDetail, Integer>{
	
	public List<OrderDetail> selectByOrderId(Integer orderId);
	public boolean updateStatus(OrderDetail ordetail);
	public boolean updatePickup(OrderDetail ordetail);
	public boolean updateReturn(OrderDetail ordetail);
	public Integer insert2(Integer orderId, Integer gymId, Integer prodId);
	public Integer maxDetailId();
	public List<OrderDetail> selectIdvId(Integer orderId);

}
