package com.simplefitness.order.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.order.dao.intf.OrderDaoIntf;
import com.simplefitness.order.vo.Order;

@Repository
public class OrderDaoImpl implements OrderDaoIntf {
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}
	

	@Override
	public Integer insertGetOrderId(Order order) {
		getSession().persist(order);
		Integer orderId = order.getOrderId();
		return orderId;
	}

	@Override
	public boolean update(Order order) {
		return false;
	}

	@Override
	public boolean deleteById(Integer orderId) {
		return false;
	}

	@Override
	public Order selectById(Integer orderId) {
		if (orderId != null) {
			Query<Order> query = this.getSession().createQuery("FROM Order WHERE orderId = :orderId", Order.class);
			query.setParameter("orderId", orderId);
			return query.uniqueResult();
		}
		return null;
	}

	@Override
	public List<Order> selectAll() {
		return getSession().createQuery("from Order order by orderId", Order.class).list();
	}

//	@Override
//	public List<Order> selectByMem(Integer memId) {
////		String hql = "SELECT new com.simplefitness.order.vo.Order(o.orderId as orderId, o.gymId as gymId, o.amount as amount, o.orderDate as orderDate, o.status as status, g.gymName as gymName) FROM Order o join Gym g on o.gymId = g.gymId where memId = :memId order by orderId desc";		
//		String hql = "FROM Order where memId = :memId order by orderId desc";		
//
//		Query<Order> query = getSession().createQuery(hql, Order.class).setParameter("memId", memId);
//		List<Order> list = query.list();
//		return list;
//	}
	@Override
	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize) {
//		String hql = "SELECT new com.simplefitness.order.vo.Order(o.orderId as orderId, o.gymId as gymId, o.amount as amount, o.orderDate as orderDate, o.status as status, g.gymName as gymName) FROM Order o join Gym g on o.gymId = g.gymId where memId = :memId order by orderId desc";		
		String hql = "FROM Order where memId = :memId order by orderId desc";		

		Query<Order> query = getSession().createQuery(hql, Order.class)
				.setParameter("memId", memId)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize);
		List<Order> list = query.list();
		return list;
	}

	@Override
	public Long findCountByMem(Integer memId) {
		String hql = "SELECT count(*) as count FROM Order where memId = :memId";
		Query<Long> query = getSession().createQuery(hql, Long.class).setParameter("memId", memId);
		Long count = query.uniqueResult();
		return count;
	}

	@Override
	public List<Order> selectByGym(Integer gymId) {
		return null;
	}

	@Override
	public boolean updateStatus(Order order) {
		Query<?> query = getSession().createQuery("update Order set status= :status where orderId = :orderId");
		return query.setParameter("status", order.getStatus()).setParameter("orderId", order.getOrderId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean insert(Order vo) {
		return false;
	}



	
}
