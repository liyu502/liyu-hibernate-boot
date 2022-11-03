package com.simplefitness.shoppingcart.repository;

import java.util.List;

import com.simplefitness.common.CommonDao;
import com.simplefitness.shoppingcart.domain.IdvProduct;

public interface IdvProductRepository extends CommonDao<IdvProduct, Integer> {
	
	public List<IdvProduct> selectByGym(Integer gymId);
	public Long selectCount(Integer gymId, Integer prodId);
	public boolean updateStatus(Integer status, Integer id);
}
