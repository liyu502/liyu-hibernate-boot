package com.simplefitness.product.dao.intf;

import java.util.List;
import java.util.Map;

import com.simplefitness.common.dao.CommonDao;
import com.simplefitness.product.vo.Product;

public interface ProductDaoIntf extends CommonDao<Product, Integer>{
	public List<Product> findByName(String name,  List<Integer> prodIds);	
}
