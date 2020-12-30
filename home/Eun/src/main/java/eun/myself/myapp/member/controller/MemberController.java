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
	public String memberLogin(HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		syslog.getLog("GET-login 진입 & 세션 확인");
		if(userid!=null)
		{
			syslog.getLog("GET-login -> /memberProfile");
			return "redirect:/memberProfile";
		}
		else
		{
			syslog.getLog("GET-login 세션 없음 ->/memberlogin");
			return "memberLogin/memberLogin";	
		}
		
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String memberLogin(String remember,String userid,String password,HttpSession session,Model model)	
	{		
		//계정정보 탐색
		Member member=memberService.selectMember(userid);
		if(member!=null)
		{
			//DB상 password
			String dbPassword=member.getPassword();
			if(dbPassword!=null)
			{
				//로그인 성공
				if(dbPassword.equals(getHash(password,"SHA256")))
				{
					session.setAttribute("userid", userid);
	
					
					
					
					if(remember!=null)
					{
						//쿠키생성필요
						syslog.getLog(remember);
						syslog.getLog("자동로그인체크했습니다");
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
		return "memberLogin/memberLogin";
	}
	@RequestMapping(value="/login.logout",method=RequestMethod.GET)
	public String memberLogout(String refurl,HttpSession session) {		
		//세션초기화
		session.invalidate();
		syslog.getLog("사용자 로그아웃");
		//redirect 로 uri 갱신
		if(refurl!=null)
		{
			return "redirect:"+refurl;	
		}else
		{
			return "home/home";
		}
		
	}
	@RequestMapping(value="/memberInsert",method=RequestMethod.GET)
	public String signUpMember()
	{
		return "memberInsert/memberInsert";
	}
	@RequestMapping(value="/memberInsert",method=RequestMethod.POST)
	public String signUpMember(String userid,String email,String password)
	{		
		Member member=memberService.selectMember(userid);
		if(member==null)
		{
			
			Member newmember =new Member();
			String uid=UUID.randomUUID().toString();
			newmember.setUid(uid);
			newmember.setUsername(userid);
			newmember.setEmail(email);
			newmember.setPassword(getHash(password,"SHA256"));
			
			memberService.signUpMember(newmember);
			syslog.getLog("id가 생성되었습니다");
		}else
		{
			syslog.getLog("id가 존재합니다");
		}
		
		
		
		return "redirect:/";
	}
	@RequestMapping(value="/memberProfile",method=RequestMethod.GET)
	public String memberProfile(HttpSession session,Model model) {
		String userid=(String)session.getAttribute("userid");
		
		if(userid!=null)
		{
			Member member=memberService.selectMember(userid);	
			if(member!=null)
			{
				
				model.addAttribute("member",member);
			
				return"memberProfile/memberProfile";
			}
		}
		return "redirect:/login";
	}
	@RequestMapping(value="/memberProfile",method=RequestMethod.POST)
	public String memberProfile(HttpSession session,String username,String email,String password)
	{
		syslog.getLog("memberProfile Update 진입");
		String userid=(String)session.getAttribute("userid");
		if(userid!=null)
		{
			Member member=memberService.selectMember(userid);
			if(member!=null)
			{
				
				if(username!=null&&username!="")member.setUsername(username);
				if(email!=null&&email!="")member.setEmail(email);
				if(password!=null&&password!="")member.setPassword(getHash(password,"SHA256"));
				syslog.getLog("memberProfile Update 적용");	
				boolean updatelog=memberService.memberUpdate(member);
				if(updatelog)
				{
					syslog.getLog("memberProfile Update 적용");	
					//업데이트 성공
					return "redirect:/memberProfile";
				}
				syslog.getLog("memberProfile Update 실패");
				//업데이트 실패
			}
			
		}
		return "redirect:/memberProfile";
	}
	@RequestMapping(value="/memberDelete",method=RequestMethod.POST)
	public String memberDelete(HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		if(userid !=null)
		{
			syslog.getLog("POST->memberDelete 세션 확인");
			try{
				memberService.memberDelete(userid);
				session.invalidate();
				syslog.getLog("POST->memberDelete 성공");
				return "redirect:/";
			}catch(Exception e)
			{
				syslog.getLog("POST->memberDelete 삭제 실패");
				
			}
		}
		syslog.getLog("POST->memberDelete 실패");
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
