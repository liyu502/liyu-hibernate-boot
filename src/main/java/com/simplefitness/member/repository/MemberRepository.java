package com.simplefitness.member.repository;

import com.simplefitness.common.CommonDao;
import com.simplefitness.member.domain.Member;

public interface MemberRepository extends CommonDao<Member, Integer> {
	
	public Member selectByUsername(String memUsername);
	public Member selectByEmail(String memEmail);
	public Member selectForLogin(String memUsername, String memPassword);
	public boolean updateByMem(Member memVo);
	public boolean updatePassByUsername(Member memVo);
	public Member selectForPass(String memUsername, String memEmail);
	public boolean updateImg(Member memVo);
	public Member selectPassByUsername(String memUsername);

}

