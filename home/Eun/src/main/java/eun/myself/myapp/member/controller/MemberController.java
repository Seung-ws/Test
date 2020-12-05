package eun.myself.myapp.member.controller;


import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
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
	public String memberLogin(String remember,String username,String password,HttpSession session,Model model)	
	{
		
		//계정정보 탐색
		Member member=memberService.selectMember(username);
		if(member!=null)
		{
			//DB상 password
			String dbPassword=member.getPassword();
			if(dbPassword!=null)
			{
				//로그인 성공
				if(dbPassword.equals(getHash(password,"SHA256")))
				{
					session.setAttribute("username", username);
					session.setAttribute("uid",member.getUid());
					session.setAttribute("gid", member.getGid());
					
					
					
					if(remember!=null)
					{
						//쿠키생성필요
						syslog.getLog(remember);
						syslog.getLog("자동로그인체크");
					}
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
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String signUpMember(String username,String password)
	{
		
		Member member=memberService.selectMember(username);
		if(member==null)
		{
			
			Member newmember =new Member();
			String uid=UUID.randomUUID().toString();
			newmember.setUid(uid);
			newmember.setUsername(username);
			newmember.setPassword(getHash(password,"SHA256"));
			
			memberService.signUpMember(newmember);
			syslog.getLog("id가 생성되었습니다");
		}else
		{
			syslog.getLog("id가 존재합니다");
		}
		
		
		
		return "redirect:/";
	}
	
	
	
	
	public String getHash(String str, String hashType)
    {
        String result="";
        try{
            MessageDigest messageDigest=MessageDigest.getInstance(hashType);
            messageDigest.update(str.getBytes("UTF-8"));

            byte[] data=messageDigest.digest();
            int dataLength=data.length;

            for(int i=0;i<dataLength;i++)
            {
                result+=(Integer.toString((data[i]&0xff) + 0x100, 16).substring(1));
                //System.out.println(Integer.toHexString(data[i]&0xff));
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            //hash타입을 찾지 못했을 때;
            e.printStackTrace();
            return null;
        };
        return result;
    }
}
