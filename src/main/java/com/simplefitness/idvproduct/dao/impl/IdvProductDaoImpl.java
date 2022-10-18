package com.simplefitness.idvproduct.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.idvproduct.dao.intf.IdvProductDaoIntf;
import com.simplefitness.idvproduct.vo.IdvProduct;

@Repository
public class IdvProductDaoImpl  implements IdvProductDaoIntf{
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public boolean insert(IdvProduct idvProduct) {
		getSession().persist(idvProduct);
		return true;
	}

	@Override
	public boolean update(IdvProduct idvProduct) {
		final StringBuilder hql = new StringBuilder().append("UPDATE IdvProduct SET ").append("gymId = :gymId,")
				.append("prodId = :prodId,").append("status = :status,").append("where idvId = :idvId");

		Query<?> query = getSession().createQuery(hql.toString());
		return query.setParameter("gymId", idvProduct.getGymId()).setParameter("prodId", idvProduct.getProdId())
				.setParameter("status", idvProduct.getStatus()).setParameter("idvId", idvProduct.getIdvId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean deleteById(Integer idvId) {
		IdvProduct idvProduct = new IdvProduct();
		idvProduct.setIdvId(idvId);
		getSession().remove(idvProduct);
		return true;
	}

	@Override
	public IdvProduct selectById(Integer idvId) {
		return getSession().load(IdvProduct.class, idvId);
	}

	@Override
	public List<IdvProduct> selectAll() {
		return getSession().createQuery("from IdvProduct order by idvId", IdvProduct.class).list();
	}

	@Override
	public List<IdvProduct> selectByGym(Integer gymId) {
		String hql = "select new IdvProduct(prodId, count(*) as count) from IdvProduct where status = :status and gymId = :gymId group by prodId";		
		
		Query<IdvProduct> query = getSession().createQuery(hql, IdvProduct.class).setParameter("status", 1).setParameter("gymId", gymId);
		List<IdvProduct> list = query.list();
		return list;
	}


	@Override
	public Long selectCount(Integer gymId, Integer prodId) {
		String hql = "select count(*) as count from IdvProduct where status = :status and gymId = :gymId and prodId = :prodId";
		
		Query<Long> query = getSession().createQuery(hql, Long.class)
				.setParameter("status", 1)
				.setParameter("gymId", gymId)
				.setParameter("prodId", prodId);
		Long count = query.uniqueResult();
		return count;
	}

	@Override
	public boolean updateStatus(Integer status, Integer idvId) {
		Query<?> query = getSession().createQuery("update IdvProduct set status= :status where idvId = :idvId");
		
		return query.setParameter("status", status)
					.setParameter("idvId", idvId)
					.executeUpdate() != 0;
	}	


	
}
