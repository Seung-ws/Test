package eun.myself.myapp.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eun.myself.myapp.member.dao.IMemberRepository;
import eun.myself.myapp.member.model.Member;
import eun.myself.myapp.member.service.IMemberService;



@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberRepository memberrepository;
	

	@Override
	public void memberSignOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void memberSignIn(Member member) {
		// TODO Auto-generated method stub
		memberrepository.memberSignIn(member);
	}

	@Override
	public void memberSignUp(Member member) {
		// TODO Auto-generated method stub
		memberrepository.memberSignUp(member);
	}
	


}
