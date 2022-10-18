package com.simplefitness.orderdetail.dao.intf;

import java.util.List;

import com.simplefitness.common.dao.CommonDao;
import com.simplefitness.orderdetail.vo.OrderDetail;


public interface OrderDetailDaoIntf extends CommonDao<OrderDetail, Integer>{
	
	public List<OrderDetail> selectByOrderId(Integer orderId);
	public boolean updateStatus(OrderDetail ordetail);
	public boolean updatePickup(OrderDetail ordetail);
	public boolean updateReturn(OrderDetail ordetail);
	public Integer insert2(Integer orderId, Integer gymId, Integer prodId);
	public Integer maxDetailId();
}
