<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pathURI" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="<c:url value='/'/>">Home</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>


  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="<c:url value='/boardList/cat/1'/>">게시판 <span class="sr-only">(current)</span></a>
      </li>

      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">참고</a>
      </li>
      
      
      <!-- 세션이 비활성화일 때 로그인 -->
      <c:if test="${empty sessionScope.member_Id }">
      	<li class="nav-item">
      	<!-- c:url value=/login == /myapp/login -->
        	<a class="nav-link" href="<c:url value='/memberLogin'/>">로그인</a>
      	</li>
      </c:if>
      <!--  세션비 활성화일 때 로그아웃 -->
      <c:if test="${not empty sessionScope.member_Id }">
    	  <li class="nav-item">
	      	<a class="nav-link" href="<c:url value='/memberProfile?refurl=${pathURI }'/>">프로필</a>
	      </li>
	      <li class="nav-item">
	      	<a class="nav-link" href="<c:url value='/memberLogout?refurl=${pathURI }'/>">로그아웃</a>
	      </li>
      </c:if>
    </ul>
    <form class="form-inline my-2 my-lg-0"action="<c:url value='/boardSearch/1'/>" method="post" role="form">
      <input type="text" class="form-control" name="keyword" placeholder="키워드를 입력하세요.">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>