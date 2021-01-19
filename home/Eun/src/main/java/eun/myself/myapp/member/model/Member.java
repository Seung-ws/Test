package eun.myself.myapp.member.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Member {
	String user_uid;
	String user_gid;
	String user_id;
	String user_name;
	String user_password;	
	String user_email;
	String user_profileimage;
	Timestamp user_createdate;

	
	public String getUser_uid() {
		return user_uid;
	}
	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}
	public String getUser_gid() {
		return user_gid;
	}
	public void setUser_gid(String user_gid) {
		this.user_gid = user_gid;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_profileimage() {
		return user_profileimage;
	}
	public void setUser_profileimage(String user_profileimage) {
		this.user_profileimage = user_profileimage;
	}
	public Timestamp getUser_createdate() {
		return user_createdate;
	}
	public void setUser_createdate(Timestamp user_createdate) {
		this.user_createdate = user_createdate;
	}

	

}
