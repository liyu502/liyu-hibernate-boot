package com.simplefitness.product.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.product.dao.intf.ProductDaoIntf;
import com.simplefitness.product.vo.Product;

@Repository
public class ProductDaoImpl implements ProductDaoIntf{
	@PersistenceContext
	private Session session = null;
	public Session getSession() {
		return session;
	}
	
	@Override
	public boolean insert(Product product) {
		getSession().persist(product);
		return true;
	}

	@Override
	public boolean update(Product product) {
		final StringBuilder hql = new StringBuilder().append("UPDATE Product SET ").append("prodName = :prodName,")
				.append("price = :price,").append("intro = :intro,").append("pic = :pic,")
				.append("where prodId = :prodId");
		Query<?> query = getSession().createQuery(hql.toString());
		return query.setParameter("prodName", product.getProdName()).setParameter("price", product.getPrice())
				.setParameter("intro", product.getIntro()).setParameter("pic", product.getPic())
				.setParameter("prodId", product.getProdId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean deleteById(Integer prodId) {
		Product product = new Product();
		product.setProdId(prodId);
		getSession().remove(product);
		return true;
	}

	@Override
	public Product selectById(Integer prodId) {
		return getSession().load(Product.class, prodId);
	}

	@Override
	public List<Product> selectAll() {
		return getSession().createQuery("from Product order by prodId", Product.class).list();
	}

	@Override
	public List<Product> findByName(String name,  List<Integer> prodIds) {
		System.out.println(prodIds.get(0));
		final StringBuilder hql = new StringBuilder().append("FROM Product where prodName like :name and (");
		for (int i = 1; i <= prodIds.size(); i++) {
			if (i != prodIds.size()) {
				hql.append("prodId = ?" + i + " or ");
			} else {
				hql.append("prodId = ?"+ i+ ") ");
			}
			
		}
		hql.append("order by prodId");
		Query<?> query = getSession().createQuery(hql.toString());
		query.setParameter("name", "%"+name+"%");
		for (int i = 0; i < prodIds.size(); i++) {
			query.setParameter(i + 1, prodIds.get(i));
		}
		return (List<Product>) query.list();
	}



}
