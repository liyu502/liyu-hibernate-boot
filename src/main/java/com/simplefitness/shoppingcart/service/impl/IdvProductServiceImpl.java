package com.simplefitness.shoppingcart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.shoppingcart.domain.IdvProduct;
import com.simplefitness.shoppingcart.repository.IdvProductRepository;
import com.simplefitness.shoppingcart.service.IdvProductService;

@Service
@Transactional
public class IdvProductServiceImpl implements IdvProductService{
	@Autowired
	private IdvProductRepository idvProductRepository;

	@Override
	public List<IdvProduct> selectByGym(Integer gymId) {
		return idvProductRepository.selectByGym(gymId);
	}

	@Override
	public Long selectCount(Integer gymId, Integer prodId) {
		return idvProductRepository.selectCount(gymId, prodId);
	}
	
}

