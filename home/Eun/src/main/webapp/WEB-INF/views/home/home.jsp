<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="false" %>

<html>
<head>
	<title>Eun</title>
	
</head>
<!-- Import : static File -->
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>


<link rel="stylesheet" href="./resources/home/css/home.css"/>


<body>
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>
<div class="home-image">
    <h2 class="title"> TestServer</h2>
    <h4 class="powered">
    	<i class="fas fa-aarrow-circle-right"></i>
     	Spring MVC Project
     </h4>
</div>

<hr style="margin:10px 0;">
<div class="row text-center">
    
    <div class="col-sm-6">
        <h3>컨텐츠1</h3>
        <p>컨텐츠 설명란.</p>
    </div>
        <div class="col-sm-6">
        <h3> 컨텐츠 2</h3>
        <p> 컨텐츠 설명란 </p>
        </div>
</div>

<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
