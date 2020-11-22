package eun.myself.myapp.member.service;

import org.springframework.stereotype.Service;

import eun.myself.myapp.member.model.Member;


public interface IMemberService {
	void memberSignOut();
	void memberSignIn(Member member);
	void memberSignUp(Member member);
	
}
