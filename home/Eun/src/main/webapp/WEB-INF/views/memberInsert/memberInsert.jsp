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
<link rel="stylesheet" href="./resources/memberInsert/css/memberInsert.css"/>
</head>
<body  class="text-center">
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>



<!-- session valid -->
<c:if test="${empty sessionScope.user_name }">
	<div class="signuppanel">
		<!-- 로그인을 포스트로 전달 -->
   		<form class="form-signup" method="post" action="<c:url value='/memberInsert'/>" >	
	  		<img class="mb-4" src="./resources/memberInsert/images/test.png" alt="" width="72" height="72">
	  		<h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>
	  		
            <h2 class="h5 mb-1 font-weight-normal">회원가입</small></h2>
	  		<!-- 로그인 정보 -->	
	  		<label for="inputUserid" class="sr">ID</label>
	  		
	  		<div class="input-group">
	  			<input type="text" name="user_id" class="form-control" placeholder="userid" required autofocus>
	  			<span class="input-group-btn">
        			<button class="btn btn-primary form-btn" type="button">인증</button>
      			</span>
	  		
  			
  			</div>
  			<!--  이메일 등록  -->  			
	  		<label for="inputEmail" class="sr">Email address</label>
	  		
	  		<div class="input-group">
	  			<input type="text" name="user_email" class="form-control" placeholder="email" required>
	  			<span class="input-group-btn">
        			<button class="btn btn-primary form-btn" type="button">인증</button>
      			</span>
  			</div>
	  		<!-- 비밀번호 정보 -->
	  		<label for="inputPassword" class="sr">Password</label>
	  	
		  		<input type="password" name="user_password"  id="inputPassword" class="form-control" placeholder="Password" required>
		  		<input type="password" name="user_validpassword"  id="validPassword" class="form-control" placeholder="valid Password" required>
		  		
	  		    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
	  		<p class="mt-5 mb-3 text-muted">&copy; 2017-2020</p>
		</form>
	</div>
</c:if>

<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>