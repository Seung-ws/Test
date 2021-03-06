<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n/member" />

    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
<link rel="stylesheet" href="./resources/memberLogin/css/memberLogin.css"/>

</head>
<body  class="text-center">
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>



<!-- session valid -->
<c:if test="${empty sessionScope.user_id }">
	<div class="loginpanel ">
		<!-- 로그인을 포스트로 전달 -->
   		<form class="form-signin" method="post" action="<c:url value='/memberLogin'/>" >	
	  		<img class="mb-4" src="./resources/memberLogin/images/test.png" alt="" width="72" height="72">
	  		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
	  		
            <h2 class="h5 mb-1 font-weight-normal"><fmt:message key="${not empty message ? message : 'BLANK'}"/></h2>
	  		<!-- 로그인정보 -->	
	  		<label for="inputEmail" class="sr-only">Email address</label>
	  		<input type="text" id="inputEmail" name="user_id" class="form-control" placeholder="userid" required autofocus>
	  		
	  		<!-- 로그인정보2 -->
	  		<label for="inputPassword" class="sr-only">Password</label>
	  	
	  		<input type="password" name="user_password"  id="inputPassword" class="form-control" placeholder="Password" required>
	  		<div class="checkbox mb-3">
    			<label>
	      			<input type="checkbox" name="remember" value="remember-me"> Remember me
	    		</label>
   			</div>
	  		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	  		<button class="btn btn-lg btn btn-secondary btn-block" type="button" onclick="location.href='<c:url value='/memberInsert'/>'">Sign Up</button>
	  		<p class="mt-5 mb-3 text-muted">&copy; 2017-2020</p>
		</form>
	</div>
</c:if>

<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>