package eun.myself.myapp.webdl.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WebdlController {

	public String test() {
		  HttpURLConnection conn = null;
		    StringBuilder contents = new StringBuilder();
		    try {
		        conn = (HttpURLConnection)new URL("https://www.youtube.com/watch?v=ustEdRG7DEE").openConnection();
		        conn.setConnectTimeout(10000);
		        conn.setReadTimeout(10000);

		        InputStream is = conn.getInputStream();

		        String enc = conn.getContentEncoding();

		        if (enc == null) {
		            Pattern p = Pattern.compile("charset=(.*)");
		            Matcher m = p.matcher(conn.getHeaderField("Content-Type"));
		            if (m.find()) {
		                enc = m.group(1);
		            }
		        }

		        if (enc == null)
		            enc = "UTF-8";

		        BufferedReader br = new BufferedReader(new InputStreamReader(is, enc));

		        String line = null;


		        while ((line = br.readLine()) != null) {
		            contents.append(line);
		            contents.append("\n");

		        }
		    }catch(IOException e){

		    }

        return contents.toString();
		        
	}
	public void test2() {
		   List<String> urlList = new ArrayList<String>();
		    Pattern urlencod = Pattern.compile("\"url_encoded_fmt_stream_map\":\"([^\"]*)\"");
		    Matcher urlencodMatch = urlencod.matcher(test());
		    
		    System.out.println("step"+1);
		    if (urlencodMatch.find()) {
		        String url_encoded_fmt_stream_map;
		        url_encoded_fmt_stream_map = urlencodMatch.group(1);
		        Pattern encod = Pattern.compile("url=(.*)");
		        Matcher encodMatch = encod.matcher(url_encoded_fmt_stream_map);
		        System.out.println("step"+2);
		        if (encodMatch.find()) {
		            String sline = encodMatch.group(1);
		            String[] urlStrings = sline.split("url=");
		            System.out.println("step"+3);
		            for (String urlString : urlStrings) {
		            	System.out.println("step"+4);
		                String url = null;
		                urlString = StringEscapeUtils.unescapeJava(urlString);
		                Pattern link = Pattern.compile("([^&,]*)[&,]");
		                Matcher linkMatch = link.matcher(urlString);
		                if (linkMatch.find()) {
		                	System.out.println("step"+5);
		                    url = linkMatch.group(1);
		                    try {
								url = URLDecoder.decode(url, "UTF8");
								System.out.println("step"+6);
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								System.out.println("step"+"error");
								e.printStackTrace();
							}
		                }
		                System.out.println("step"+7);
		                urlList.add(url);
		            }
		        }
		    }
		    System.out.println(urlList.size());
		    for(String str: urlList)
		    {
		    	System.out.println(str);
		    }
		    
	}
	public String test3() {
		//"\"url_encoded_fmt_stream_map\":\"([^\"]*)\""
		StringBuffer sb = new StringBuffer();

		String p="(?<=watch\\\\?v=|/videos/|embed\\\\/|youtu.be\\\\/|\\\\/v\\\\/|\\\\/e\\\\/|watch\\\\?v%3D|watch\\\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\\u200C\\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\\\&\\\\?\\\\n]*";
	    Pattern urlencod = Pattern.compile(p);
	    Matcher m = urlencod.matcher(test());
	    if(m.find()){

            sb.append(m.group(0));

            System.out.println("==="+m.group(0));

            for(int i=0;i<=m.groupCount();i++){    

                System.out.println(i+"==="+m.group(i));

                sb.append(m.group(i));

            }

        }

	    return sb.toString();
	    
	}
	public static String expandUrl(String shortenedUrl)  {
        URL url;
        String expandedURL = "";
        try {
            url = new URL(shortenedUrl);
            // open connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); 
            // stop following browser redirect
            httpURLConnection.setInstanceFollowRedirects(false);
            // extract location header containing the actual destination URL
            expandedURL = httpURLConnection.getHeaderField("Location");
            httpURLConnection.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
         return expandedURL;
    }
	

	@RequestMapping(value="/webdl",method=RequestMethod.GET)
	public String home(Model model){
	   //System.out.println(test());

		return "webdl/webdl";
	}
	
	
}

