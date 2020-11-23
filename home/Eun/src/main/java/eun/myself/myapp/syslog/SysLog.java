package eun.myself.myapp.syslog;

import org.springframework.stereotype.Component;

@Component
public class SysLog {

	public void getLog(String str)
	{
		System.out.println(str);
	}
}
