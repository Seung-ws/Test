<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ tag body-content="empty" %>
<%@ attribute name="reply_num" type="java.lang.Integer"%>
<%@ attribute name="reply_step" type="java.lang.Integer"%>
<%
if(reply_num==0){ 
	//out.print("메인글 임");
    out.print(""); //메인 글임을 나타냄 > 
} else {
	//out.print("메인글 아님");
    for(int i=0; i<reply_step; i++) {
        out.print("&nbsp;");  //공백
    }
    out.print("└");//답변글임을 나타냄 
}//end if
%>
