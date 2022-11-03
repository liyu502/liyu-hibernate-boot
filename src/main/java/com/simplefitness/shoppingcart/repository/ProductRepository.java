package com.simplefitness.shoppingcart.repository;

import java.util.List;

import com.simplefitness.common.CommonDao;
import com.simplefitness.shoppingcart.domain.Product;

public interface ProductRepository extends CommonDao<Product, Integer>{
	public List<Product> findByName(String name,  List<Integer> prodIds);	
}
