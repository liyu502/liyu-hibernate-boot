package com.simplefitness.orderdetail.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.idvproduct.dao.intf.IdvProductDaoIntf;
import com.simplefitness.orderdetail.dao.intf.OrderDetailDaoIntf;
import com.simplefitness.orderdetail.service.intf.OrderDetailServiceIntf;
import com.simplefitness.orderdetail.vo.OrderDetail;
import com.simplefitness.product.dao.intf.ProductDaoIntf;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailServiceIntf {
	@Autowired
	private OrderDetailDaoIntf dao;
	@Autowired
	private IdvProductDaoIntf idvDao;
	@Autowired
	private ProductDaoIntf productDao;
	
	@Override
	public List<OrderDetail> findAll() {
		return dao.selectAll();
	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
		List<OrderDetail> orderDetails = dao.selectByOrderId(orderId);
		for (int i = 0; i < orderDetails.size(); i++) {
			orderDetails.get(i).setProdName(productDao.selectById(idvDao.selectById(orderDetails.get(i).getIdvId()).getProdId()).getProdName());
		}
		return orderDetails;
	}

}
