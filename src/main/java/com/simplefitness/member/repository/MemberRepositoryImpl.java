package com.simplefitness.member.repository;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.simplefitness.member.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public boolean insert(Member member) {
		getSession().persist(member);
		return true;
	}

	@Override
	public boolean update(Member member) {
		final StringBuilder hql = new StringBuilder().append("UPDATE Member SET ").append("phone = :phone,")
				.append("email = :email,").append("startDate = :startDate,").append("expireDate = :expireDate,")
				.append("memStatus = :memStatus,").append("where memId = :memId");

		Query<?> query = getSession().createQuery(hql.toString());
		return query.setParameter("phone", member.getPhone()).setParameter("email", member.getEmail())
				.setParameter("startDate", member.getStartDate()).setParameter("expireDate", member.getExpireDate())
				.setParameter("memStatus", member.getMemStatus()).setParameter("memId", member.getMemId())
				.executeUpdate() != 0;
	}

	@Override
	public boolean deleteById(Integer memId) {
		Member member = new Member();
		member.setMemId(memId);
		getSession().remove(member);
		return true;
	}

	@Override
	public Member selectById(Integer memId) {
		return getSession().load(Member.class, memId);
	}

	@Override
	public List<Member> selectAll() {
		return getSession().createQuery("from Member order by memId", Member.class).list();
	}

	@Override
	public Member selectByUsername(String username) {
		return getSession().load(Member.class, username);
	}

	@Override
	public Member selectByEmail(String email) {
		return getSession().load(Member.class, email);
	}

	@Override
	public Member selectForLogin(String username, String memPassword) {
		Query<Member> query = getSession()
				.createQuery("FROM Member WHERE username = :username and memPassword = :password", Member.class)
				.setParameter("username", username).setParameter("password", memPassword);
		Member member = query.uniqueResult();
		return member;
	}

	@Override
	public boolean updateByMem(Member member) {
		final StringBuilder hql = new StringBuilder().append("UPDATE Member SET ").append("memName = :memName,")
				.append("nickname = :nickname,").append("phone = :phone,").append("email = :email,")
				.append("gender = :gender,").append("birth = :birth,").append("where username = :username");

		Query<?> query = getSession().createQuery(hql.toString());
		return query.setParameter("memName", member.getMemName()).setParameter("nickname", member.getNickname())
				.setParameter("phone", member.getPhone()).setParameter("email", member.getEmail())
				.setParameter("gender", member.getGender()).setParameter("birth", member.getBirth())
				.setParameter("username", member.getUsername()).executeUpdate() != 0;
	}

	@Override
	public boolean updatePassByUsername(Member member) {
		Query<?> query = getSession()
				.createQuery("UPDATE Member SET memPassword = :memPassword WHERE username = :username");

		return query.setParameter("memPassword", member.getMemPassword()).setParameter("username", member.getUsername())
				.executeUpdate() != 0;
	}

	@Override
	public Member selectForPass(String username, String email) {

		Query<Member> query = getSession()
				.createQuery("FROM Member WHERE username = :username and email = :email", Member.class)
				.setParameter("username", username).setParameter("email", email);
		Member member = query.uniqueResult();
		return member;
	}

	@Override
	public boolean updateImg(Member member) {
		Query<?> query = getSession().createQuery("UPDATE Member SET pic = :pic WHERE username = :username");

		return query.setParameter("pic", member.getPic()).setParameter("username", member.getUsername())
				.executeUpdate() != 0;
	}

	@Override
	public Member selectPassByUsername(String username) {
		Query<Member> query = getSession()
				.createQuery("select memPassword from Member where username = :username", Member.class)
				.setParameter("username", username);
		Member member = query.uniqueResult();
		return member;
	}

}
