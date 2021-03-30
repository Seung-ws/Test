package eun.myself.myapp.member.dao;

import org.springframework.stereotype.Repository;

import eun.myself.myapp.member.model.Member;

@Repository
public interface IMemberRepository {
	Member selectMember(String member_Id);	
	void insertMember(Member member);
//	void loginMember(Member member);
	void updateMember(Member member);
	void deleteMember(String member_Id);
}
