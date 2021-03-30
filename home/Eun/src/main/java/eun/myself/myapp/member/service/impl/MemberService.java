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
	public void logoutMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertMember(Member member) {
		// TODO Auto-generated method stub
		memberRepository.insertMember(member);
	}



	@Override
	public boolean updateMember(Member member) {
		// TODO Auto-generated method stub
		try {
			memberRepository.updateMember(member);
		}catch(Exception e)
		{
			return false;	
		}
		return true;
		
	}

	@Override
	public Member selectMember(String member_Id) {
		// TODO Auto-generated method stub		
		return memberRepository.selectMember(member_Id);
	}

	@Override
	public boolean deleteMember(String member_Id) {
		// TODO Auto-generated method stub
		try {
			memberRepository.deleteMember(member_Id);
			
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}




}
