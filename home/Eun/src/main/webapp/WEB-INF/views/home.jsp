<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="false" %>

<html>
<head>
	<title>Eun</title>
	
</head>
<!-- Import : static File -->
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
<link rel="stylesheet" href="./resources/css/home/home.css"/>


<body>
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>
<div class="home-image">
    <h2 class="title"> Eun Media Server</h2>
    <h4 class="powered">
    	<i class="fas fa-aarrow-circle-right"></i>
     	Spring MVC Project
     </h4>
</div>

<hr style="margin:10px 0;">
<div class="row text-center">
    
    <div class="col-sm-6">
        <h3>Bookmark App</h3>
        <p>Bookmar is a Uniform Resource Identifier(URI) that is stored for later retriveal in any of various storage formats.
            You can store your own bookmarks by Bookmark application.
            It's also possible to update or delete your bookmarks.</p>
    </div>
        <div class="col-sm-6">
        <h3> Blog App</h3>
        <p> This application makes it possible to log daily events or write your own interests 
            such as hobbies, techniques, etc.
            Atypical blog combines text, digital images, and link to other blogs, web pages,
            and other media related to its topic </p>
        </div>
</div>

<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
