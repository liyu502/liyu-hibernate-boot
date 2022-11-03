package com.simplefitness.shoppingcart.repository;

import java.util.List;

import com.simplefitness.common.CommonDao;
import com.simplefitness.shoppingcart.domain.Order;


public interface OrderRepository extends CommonDao<Order, Integer>{
	
    public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize);
    public boolean updateStatus(Order orderVo);
    public Integer insertGetOrderId(Order orderVo);
    public Long findCountByMem(Integer memId);
}
