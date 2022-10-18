package com.simplefitness.product.service.intf;

import java.util.List;
import java.util.Map;

import com.simplefitness.product.vo.Product;


public interface ProductServiceIntf {
	
	Product selectById(Integer prodId);
	List<Product> findByName(String name,  List<Integer> prodIds);
	
}
