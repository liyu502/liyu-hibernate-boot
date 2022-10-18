package com.simplefitness.idvproduct.dao.intf;

import java.util.List;

import com.simplefitness.common.dao.CommonDao;
import com.simplefitness.idvproduct.vo.IdvProduct;

public interface IdvProductDaoIntf extends CommonDao<IdvProduct, Integer> {
	
	public List<IdvProduct> selectByGym(Integer gymId);
	public Long selectCount(Integer gymId, Integer prodId);
	public boolean updateStatus(Integer status, Integer id);
	
}
