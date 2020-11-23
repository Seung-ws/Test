package eun.myself.myapp.member.service;

import org.springframework.stereotype.Service;

import eun.myself.myapp.member.model.Member;


public interface IMemberService {
	Member selectMember(String username);
	void memberSignOut();
	void memberSignIn(Member member);
	void memberSignUp(Member member);
	void memberModify(Member member);
	
}
