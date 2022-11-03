package com.simplefitness.shoppingcart.service;

import java.util.List;

import com.simplefitness.shoppingcart.domain.Product;


public interface ProductService {
	
	Product selectById(Integer prodId);
	List<Product> findByName(String name,  List<Integer> prodIds);
	
}
