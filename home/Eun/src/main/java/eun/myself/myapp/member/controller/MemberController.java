package eun.myself.myapp.member.controller;


import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;

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
	
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLogin(HttpSession session) {
		String member_Id=(String)session.getAttribute("member_Id");
		syslog.getLog("GET-login 진입 & 세션 확인");
		if(member_Id!=null)
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
	
	@RequestMapping(value="/memberLogin",method=RequestMethod.POST)
	public String memberLogin(String remember,String member_Id,String member_Password,
			HttpSession session,Model model)	
	{		
		syslog.getLog("GET->memberLogin 접속");
		//계정정보 탐색
		Member member=memberService.selectMember(member_Id);
		if(member!=null)
		{			
			//DB상 password
			String dbPassword=member.getMember_Password();
			if(dbPassword!=null)
			{
				//로그인 성공
				if(dbPassword.equals(getHash(member_Password,"SHA256")))
				{
					session.setAttribute("member_Id", member.getMember_Id());
					session.setAttribute("member_Username",member.getMember_Username());
	
					if(remember!=null)
					{
						//쿠키생성필요
						syslog.getLog(remember);
						syslog.getLog("자동로그인체크했습니다");
					}
					syslog.getLog("로그인완료");
					
					//redirect 로 uri 갱신
					syslog.getLog("GET->memberLogin redirect/ 나가기");
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
		
		syslog.getLog("GET->memberLogin 으로 다시 리턴");
		return "memberLogin/memberLogin";
	}
	@RequestMapping(value="/memberLogout",method=RequestMethod.GET)
	public String memberLogout(String refurl,HttpSession session) {
		syslog.getLog("GET->memberLogout 진입");
		//세션초기화
		session.invalidate();		
		//redirect 로 uri 갱신
		syslog.getLog("GET->memberLogout 로그아웃성공");
		if(refurl!=null)
		{
			syslog.getLog("GET->memberLogout refurl로 나가기");
			return "redirect:"+refurl;	
		}else
		{
			syslog.getLog("GET->memberLogout home으로 나가기");
			return "home/home";
		}		
	}
	
	@RequestMapping(value="/memberInsert",method=RequestMethod.GET)
	public String signUpMember()
	{
		return "memberInsert/memberInsert";
	}
	@RequestMapping(value="/memberInsert",method=RequestMethod.POST)
	public String signUpMember(String member_Id,String member_Email,String member_Password)
	{		
		Member member=memberService.selectMember(member_Id);
		if(member==null)
		{
			
			Member newMember =new Member();
			String member_Uid=UUID.randomUUID().toString();
			newMember.setMember_Uid(member_Uid);
			newMember.setMember_Gid(member_Uid);
		//	syslog.getLog(user_uid);
			newMember.setMember_Id(member_Id);
		//	syslog.getLog(user_id);
			newMember.setMember_Username(member_Id);			
			newMember.setMember_Email(member_Email);
			//syslog.getLog(user_email);
			newMember.setMember_Password(getHash(member_Password,"SHA256"));
		//	syslog.getLog(user_password);
			
			memberService.insertMember(newMember);
		
			syslog.getLog("id가 생성되었습니다");
		}else
		{
			syslog.getLog("id가 존재합니다");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/memberProfile",method=RequestMethod.GET)
	public String memberProfile(HttpSession session,Model model) {
		String member_Id=(String)session.getAttribute("member_Id");
		
		if(member_Id!=null)
		{
			Member member=memberService.selectMember(member_Id);	
			if(member!=null)
			{
				model.addAttribute("member",member);			
				return"memberProfile/memberProfile";
			}
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value="/memberProfile",method=RequestMethod.POST)
	public String memberProfile(HttpSession session,String member_Username,
			String member_Email,String member_Password)
	{
		syslog.getLog("memberProfile Update 진입");
		String member_Id=(String)session.getAttribute("member_Id");
		if(member_Id!=null)
		{
			Member member=memberService.selectMember(member_Id);
			if(member!=null)
			{
				
				if(member_Username!=null&&member_Username!="")member.setMember_Username(member_Username);
				if(member_Email!=null&&member_Email!="")member.setMember_Email(member_Email);
				if(member_Password!=null&&member_Password!="")member.setMember_Password(getHash(member_Password,"SHA256"));
				syslog.getLog("memberProfile Update 적용");	
				
				boolean updatelog=memberService.updateMember(member);
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
		String member_Id=(String)session.getAttribute("member_Id");
		if(member_Id !=null)
		{
			syslog.getLog("POST->memberDelete 세션 확인");
			try{
				memberService.deleteMember(member_Id);
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
