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
<c:out value="${name }"/>
</body>
</html>
