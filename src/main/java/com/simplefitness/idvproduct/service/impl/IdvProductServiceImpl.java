package com.simplefitness.idvproduct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplefitness.idvproduct.dao.intf.IdvProductDaoIntf;
import com.simplefitness.idvproduct.service.intf.IdvProductServiceIntf;
import com.simplefitness.idvproduct.vo.IdvProduct;

@Service
@Transactional
public class IdvProductServiceImpl implements IdvProductServiceIntf{
	@Autowired
	private IdvProductDaoIntf dao;


	@Override
	public List<IdvProduct> selectByGym(Integer gymId) {
		return dao.selectByGym(gymId);
	}


	@Override
	public Long selectCount(Integer gymId, Integer prodId) {
		return dao.selectCount(gymId, prodId);
	}


	
	
}

