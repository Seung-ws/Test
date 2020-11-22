package eun.myself.myapp.member.dao;

import org.springframework.stereotype.Repository;

import eun.myself.myapp.member.model.Member;

@Repository
public interface IMemberRepository {

	void memberSignIn(Member member);
	void memberSignUp(Member member);
}
