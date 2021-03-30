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
		syslog.getLog("GET-login ���� & ���� Ȯ��");
		if(member_Id!=null)
		{
			syslog.getLog("GET-login -> /memberProfile");
			return "redirect:/memberProfile";
		}
		else
		{
			syslog.getLog("GET-login ���� ���� ->/memberlogin");
			return "memberLogin/memberLogin";	
		}		
	}
	
	@RequestMapping(value="/memberLogin",method=RequestMethod.POST)
	public String memberLogin(String remember,String member_Id,String member_Password,
			HttpSession session,Model model)	
	{		
		syslog.getLog("GET->memberLogin ����");
		//�������� Ž��
		Member member=memberService.selectMember(member_Id);
		if(member!=null)
		{			
			//DB�� password
			String dbPassword=member.getMember_Password();
			if(dbPassword!=null)
			{
				//�α��� ����
				if(dbPassword.equals(getHash(member_Password,"SHA256")))
				{
					session.setAttribute("member_Id", member.getMember_Id());
					session.setAttribute("member_Username",member.getMember_Username());
	
					if(remember!=null)
					{
						//��Ű�����ʿ�
						syslog.getLog(remember);
						syslog.getLog("�ڵ��α���üũ�߽��ϴ�");
					}
					syslog.getLog("�α��οϷ�");
					
					//redirect �� uri ����
					syslog.getLog("GET->memberLogin redirect/ ������");
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
		
		syslog.getLog("GET->memberLogin ���� �ٽ� ����");
		return "memberLogin/memberLogin";
	}
	@RequestMapping(value="/memberLogout",method=RequestMethod.GET)
	public String memberLogout(String refurl,HttpSession session) {
		syslog.getLog("GET->memberLogout ����");
		//�����ʱ�ȭ
		session.invalidate();		
		//redirect �� uri ����
		syslog.getLog("GET->memberLogout �α׾ƿ�����");
		if(refurl!=null)
		{
			syslog.getLog("GET->memberLogout refurl�� ������");
			return "redirect:"+refurl;	
		}else
		{
			syslog.getLog("GET->memberLogout home���� ������");
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
		
			syslog.getLog("id�� �����Ǿ����ϴ�");
		}else
		{
			syslog.getLog("id�� �����մϴ�");
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
		syslog.getLog("memberProfile Update ����");
		String member_Id=(String)session.getAttribute("member_Id");
		if(member_Id!=null)
		{
			Member member=memberService.selectMember(member_Id);
			if(member!=null)
			{
				
				if(member_Username!=null&&member_Username!="")member.setMember_Username(member_Username);
				if(member_Email!=null&&member_Email!="")member.setMember_Email(member_Email);
				if(member_Password!=null&&member_Password!="")member.setMember_Password(getHash(member_Password,"SHA256"));
				syslog.getLog("memberProfile Update ����");	
				
				boolean updatelog=memberService.updateMember(member);
				if(updatelog)
				{
					syslog.getLog("memberProfile Update ����");	
					//������Ʈ ����
					return "redirect:/memberProfile";
				}
				syslog.getLog("memberProfile Update ����");
				//������Ʈ ����
			}
			
		}
		return "redirect:/memberProfile";
	}
	
	@RequestMapping(value="/memberDelete",method=RequestMethod.POST)
	public String memberDelete(HttpSession session) {
		String member_Id=(String)session.getAttribute("member_Id");
		if(member_Id !=null)
		{
			syslog.getLog("POST->memberDelete ���� Ȯ��");
			try{
				memberService.deleteMember(member_Id);
				session.invalidate();
				syslog.getLog("POST->memberDelete ����");
				return "redirect:/";
			}catch(Exception e)
			{
				syslog.getLog("POST->memberDelete ���� ����");				
			}
		}
		syslog.getLog("POST->memberDelete ����");
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
            //hashŸ���� ã�� ������ ��;
            e.printStackTrace();
            return null;
        };
        return result;
    }
}
