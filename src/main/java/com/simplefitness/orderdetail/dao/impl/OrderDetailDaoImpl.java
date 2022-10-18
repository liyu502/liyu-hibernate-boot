package com.simplefitness.orderdetail.dao.impl;


import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.orderdetail.dao.intf.OrderDetailDaoIntf;
import com.simplefitness.orderdetail.vo.OrderDetail;

@Repository
public class OrderDetailDaoImpl implements OrderDetailDaoIntf {
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public Integer insert2(Integer orderId, Integer gymId, Integer prodId) {
		NativeQuery<?> nativeQuery = getSession().createSQLQuery("insert into order_detail (order_id, idv_id, status) values (:orderId, (select idv_id from idv_prod where gym_id= :gymId and prod_id= :prodId and status = 1 limit 1), :status)")
				.setParameter("orderId", orderId)
				.setParameter("gymId", gymId)
				.setParameter("prodId", prodId)
				.setParameter("status", 0);
				int result = nativeQuery.executeUpdate();
			return result;
	}
	
	@Override
	public Integer maxDetailId() {
		Query<Integer> query = getSession().createQuery("select max(detailId) from OrderDetail", Integer.class);
		Integer id = query.uniqueResult();
		return id;
	}

	@Override
	public boolean update(OrderDetail vo) {
		return false;
	}

	@Override
	public boolean deleteById(Integer id) {
		return false;
	}

	@Override
	public List<OrderDetail> selectAll() {
		return getSession().createQuery("from OrderDetail order by detailId", OrderDetail.class).list();

	}

	@Override
	public OrderDetail selectById(Integer detailId) {
		return getSession().load(OrderDetail.class, detailId);
	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
//		String hql = "select new OrderDetail(od.idvId, od.pickupTime, od.returnTime, od.status, p.prodName) from OrderDetail od join IdvProduct i on od.idvId = i.idvId join Product p on i.prodId = p.prodId where orderId = :orderId";		
		String hql = "from OrderDetail where orderId = :orderId";		
		
		Query<OrderDetail> query = getSession().createQuery(hql, OrderDetail.class).setParameter("orderId", orderId);
		List<OrderDetail> list = query.list();
		return list;
		
	}

	@Override
	public boolean updateStatus(OrderDetail orderDetail) {
		Query<?> query = getSession().createQuery("update OrderDetail set status= :status where detailId = :detailId");

		return query.setParameter("status", orderDetail.getStatus()).setParameter("detailId", orderDetail.getDetailId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean updatePickup(OrderDetail orderDetail) {
		Query<?> query = getSession().createQuery("update OrderDetail set pickupTime = :pickupTime and status= :status where detailId = :detailId");

		return query.setParameter("pickupTime", orderDetail.getPickupTime()).setParameter("status", 1).setParameter("detailId", orderDetail.getDetailId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean updateReturn(OrderDetail orderDetail) {
		Query<?> query = getSession().createQuery("update OrderDetail set returnTime = :returnTime and status= :status where detailId = :detailId");

		return query.setParameter("returnTime", orderDetail.getReturnTime()).setParameter("status", orderDetail.getStatus()).setParameter("detailId", orderDetail.getDetailId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean insert(OrderDetail vo) {
		return false;
	}

	

}
