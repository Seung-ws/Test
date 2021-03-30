package eun.myself.myapp.member.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Member {
	String member_Uid;
	String member_Gid;
	String member_Id;
	String member_Username;
	String member_Password;	
	String member_Email;
	String member_Image;
	Timestamp member_CreateDate;
	public String getMember_Uid() {
		return member_Uid;
	}
	public void setMember_Uid(String member_Uid) {
		this.member_Uid = member_Uid;
	}
	public String getMember_Gid() {
		return member_Gid;
	}
	public void setMember_Gid(String member_Gid) {
		this.member_Gid = member_Gid;
	}
	public String getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}
	public String getMember_Username() {
		return member_Username;
	}
	public void setMember_Username(String member_Username) {
		this.member_Username = member_Username;
	}
	public String getMember_Password() {
		return member_Password;
	}
	public void setMember_Password(String member_Password) {
		this.member_Password = member_Password;
	}
	public String getMember_Email() {
		return member_Email;
	}
	public void setMember_Email(String member_Email) {
		this.member_Email = member_Email;
	}
	public String getMember_Image() {
		return member_Image;
	}
	public void setMember_Image(String member_Image) {
		this.member_Image = member_Image;
	}
	public Timestamp getMember_CreateDate() {
		return member_CreateDate;
	}
	public void setMember_CreateDate(Timestamp member_CreateDate) {
		this.member_CreateDate = member_CreateDate;
	}

	
	

}
