package com.simplefitness.order.dao.intf;

import java.util.List;

import com.simplefitness.common.dao.CommonDao;
import com.simplefitness.order.vo.Order;


public interface OrderDaoIntf extends CommonDao<Order, Integer>{
	
    public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize);
    public List<Order> selectByGym(Integer gymId);
    public boolean updateStatus(Order orderVo);
    public Integer insertGetOrderId(Order orderVo);
    public Long findCountByMem(Integer memId);
}
