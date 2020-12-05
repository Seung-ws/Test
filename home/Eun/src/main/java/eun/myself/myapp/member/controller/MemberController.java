package eun.myself.myapp.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eun.myself.myapp.member.model.Member;
import eun.myself.myapp.member.service.IMemberService;
import eun.myself.myapp.syslog.SysLog;

@Controller
public class MemberController {
	@Autowired
	IMemberService memberService;
	@Autowired
	SysLog syslog;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String memberLogin() {
		
		return "member/login";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String memberLogin(String username,String password,HttpSession session,Model model)	
	{
		syslog.getLog("사용자:"+username);
		//계정정보 탐색
		Member member=memberService.selectMember(username);
		if(member!=null)
		{
			//DB상 password
			String dbPassword=member.getPassword();
			if(dbPassword!=null)
			{
				//로그인 성공
				if(dbPassword.contentEquals(password))
				{
					session.setAttribute("username", username);
					session.setAttribute("uid",member.getUid());
					session.setAttribute("gid", member.getGid());
					syslog.getLog("로그인완료");
					//redirect 로 uri 갱신
					return "redirect:/";
					
				}
				else {
					model.addAttribute("message", "WRONG_PASSWORD");
				}
			}
		}else {
			model.addAttribute("message","USER_NOT_FOUND");
		}
		session.invalidate();					
		return "member/login";
	}
	@RequestMapping(value="/login.logout",method=RequestMethod.GET)
	public String memberLogout(String refurl,HttpSession session) {		
		//세션초기화
		session.invalidate();
		syslog.getLog("사용자 로그아웃");
		//redirect 로 uri 갱신
		return "redirect:"+refurl;
	}
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String signUpMember()
	{
		return "member/signup";
	}
}
