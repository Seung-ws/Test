package eun.myself.myapp.member.dao;

import org.springframework.stereotype.Repository;

import eun.myself.myapp.member.model.Member;

@Repository
public interface IMemberRepository {
	Member selectMember(String user_id);	
	void memberSignIn(Member member);
	void signUpMember(Member member);
	void memberUpdate(Member member);
	void memberDelete(String user_id);
}
