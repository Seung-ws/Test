package eun.myself.myapp.member.service;

import org.springframework.stereotype.Service;

import eun.myself.myapp.member.model.Member;


public interface IMemberService {
	Member selectMember(String member_Id);
	boolean deleteMember(String member_Id);
	void logoutMember();
	void insertMember(Member member);
//	void loginMember(Member member);
	boolean updateMember(Member member);
	
}
