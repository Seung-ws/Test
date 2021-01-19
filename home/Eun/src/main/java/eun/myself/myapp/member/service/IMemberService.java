package eun.myself.myapp.member.service;

import org.springframework.stereotype.Service;

import eun.myself.myapp.member.model.Member;


public interface IMemberService {
	Member selectMember(String user_id);
	boolean memberDelete(String user_id);
	void memberSignOut();
	void memberSignIn(Member member);
	void signUpMember(Member member);
	boolean memberUpdate(Member member);
	
}
