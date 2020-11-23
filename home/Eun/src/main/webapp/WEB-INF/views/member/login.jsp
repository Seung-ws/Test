<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n/member" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
<link rel="stylesheet" href="./resources/css/login/login.css"/>
</head>
<body  class="text-center">
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>



<!-- session valid -->
<c:if test="${empty sessionScope.username }">
	<div class="loginpanel">
		<!-- 로그인을 포스트로 전달 -->
   		<form class="form-signin" method="post" action="<c:url value='/login'/>" >	
	  		<img class="mb-4" src="./resources/images/login/test.png" alt="" width="72" height="72">
	  		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
	  		
            <h2 class="h5 mb-1 font-weight-normal"><fmt:message key="${not empty message ? message : 'BLANK'}"/></small></h2>
	  		<!-- 로그인정보 -->	
	  		<label for="inputEmail" class="sr-only">Email address</label>
	  		<input type="email" id="inputEmail" name="username" class="form-control" placeholder="Email address" required autofocus>
	  		
	  		<!-- 로그인정보2 -->
	  		<label for="inputPassword" class="sr-only">Password</label>
	  	
	  		<input type="password" name="password"  id="inputPassword" class="form-control" placeholder="Password" required>
	  		<div class="checkbox mb-3">
    			<label>
	      			<input type="checkbox" value="remember-me"> Remember me
	    		</label>
   			</div>
	  		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	  		<p class="mt-5 mb-3 text-muted">&copy; 2017-2020</p>
		</form>
	</div>
</c:if>

<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>