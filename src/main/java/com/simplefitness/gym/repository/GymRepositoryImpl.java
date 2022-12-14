package com.simplefitness.gym.repository;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.gym.domain.Gym;

@Repository
public class GymRepositoryImpl implements GymRepository {
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}
	
	@Override
	public boolean insert(Gym gym) {
		getSession().persist(gym);
		return true;
	}

	@Override
	public boolean deleteById(Integer id) {
		Gym gym = new Gym();
		gym.setGymId(id);
		getSession().remove(gym);
		return true;
	}

	@Override
	public boolean update(Gym gym) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Gym SET ");
			hql.append("gym_name = :gymName,")
				.append("address = :address,")
				.append("phone = :phone,")
				.append("open_date = :openDate, ")
				.append("open_time = :openTime, ")
				.append("close_time = :closeTime, ")
				.append("max_p = :maxPeople, ")
				.append("intro = :intro ")
				.append("where gym_id = :gymId");

			Query<?> query = getSession().createQuery(hql.toString());
			return query
					.setParameter("gymName", gym.getGymName())
					.setParameter("address", gym.getAddress())
					.setParameter("phone", gym.getPhone())
					.setParameter("openDate", gym.getOpenDate())
					.setParameter("openTime", gym.getOpenTime())
					.setParameter("closeTime", gym.getCloseTime())
					.setParameter("maxPeople", gym.getMaxPeople())
					.setParameter("intro", gym.getIntro())
					.setParameter("gymId", gym.getGymId())
					.executeUpdate() > 0;
	}

	@Override
	public Gym selectById(Integer id) {
		Query<Gym> query = getSession().createQuery("FROM Gym WHERE gym_id = :gymId", Gym.class);
		query.setParameter("gymId", id);	
		Gym gym = query.uniqueResult();
		return gym;
	}

	@Override
	public List<Gym> selectAll() {
		Query<Gym> query = getSession().createQuery("FROM Gym ", Gym.class);
		List<Gym> list = query.list();
		return list;
	}
	
}
