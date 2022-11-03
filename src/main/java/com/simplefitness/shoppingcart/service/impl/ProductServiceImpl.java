package com.simplefitness.shoppingcart.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.shoppingcart.domain.Product;
import com.simplefitness.shoppingcart.repository.ProductRepository;
import com.simplefitness.shoppingcart.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product selectById(Integer prodId) {
		Product product = productRepository.selectById(prodId);
		return product;
	}

	@Override
	public List<Product> findByName(String name,  List<Integer> prodIds) {
		List<Product> products = productRepository.findByName(name, prodIds);
		return products;
	}

}
