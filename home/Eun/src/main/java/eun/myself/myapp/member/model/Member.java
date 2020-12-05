package eun.myself.myapp.member.model;

public class Member {
	String uid;
	String gid;
	String username;
	String password;
	int authstate;
	
	public int getAuthstate() {
		return authstate;
	}
	public void setAuthstate(int authstate) {
		this.authstate = authstate;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}

	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
