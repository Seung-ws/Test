package eun.myself.myapp.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eun.myself.myapp.member.dao.IMemberRepository;
import eun.myself.myapp.member.model.Member;
import eun.myself.myapp.member.service.IMemberService;



@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberRepository memberRepository;
	

	@Override
	public void memberSignOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void memberSignIn(Member member) {
		// TODO Auto-generated method stub
		memberRepository.memberSignIn(member);
	}

	@Override
	public void signUpMember(Member member) {
		// TODO Auto-generated method stub
		memberRepository.signUpMember(member);
	}

	@Override
	public void memberModify(Member member) {
		// TODO Auto-generated method stub
		memberRepository.memberModify(member);
	}

	@Override
	public Member selectMember(String username) {
		// TODO Auto-generated method stub		
		return memberRepository.selectMember(username);
	}
	


}
