package com.simplefitness.idvproduct.service.intf;

import java.util.List;

import com.simplefitness.idvproduct.vo.IdvProduct;

public interface IdvProductServiceIntf {
	
	public List<IdvProduct> selectByGym(Integer gymId);
	public Long selectCount(Integer gymId, Integer prodId);
}