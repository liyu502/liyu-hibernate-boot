package com.simplefitness.shoppingcart.repository.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.shoppingcart.domain.Order;
import com.simplefitness.shoppingcart.repository.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
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

	@Override
	public List<Order> selectByMem(Integer memId, Integer pageNo, Integer pageSize) {
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
