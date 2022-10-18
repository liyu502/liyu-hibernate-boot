package com.simplefitness.product.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.product.dao.intf.ProductDaoIntf;
import com.simplefitness.product.service.intf.ProductServiceIntf;
import com.simplefitness.product.vo.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductServiceIntf{
	@Autowired
	private ProductDaoIntf dao;

	
	@Override
	public Product selectById(Integer prodId) {
		Product product = dao.selectById(prodId);
		return product;
	}


	@Override
	public List<Product> findByName(String name,  List<Integer> prodIds) {
		List<Product> products = dao.findByName(name, prodIds);
		return products;
	}

}
