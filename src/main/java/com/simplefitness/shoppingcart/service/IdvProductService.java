package com.simplefitness.shoppingcart.service;

import java.util.List;

import com.simplefitness.shoppingcart.domain.IdvProduct;

public interface IdvProductService {
	
	public List<IdvProduct> selectByGym(Integer gymId);
	public Long selectCount(Integer gymId, Integer prodId);
}