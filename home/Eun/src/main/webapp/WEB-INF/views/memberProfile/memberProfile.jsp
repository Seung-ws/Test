<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="false" %>
<html lang="en">

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.79.0">
    <title>Member</title>

	<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
    


 
    <!-- Custom styles for this template -->
    <link href="./resources/memberProfile/css/memberProfile.css" rel="stylesheet">
    <style>
    .img-fluid {
	  max-width: 100%;
	  height: 100%;
	}
    .rounded-circle {
	  border-radius: 50% !important;
	}
	.mx-auto {
  		margin-left: auto !important;
	}
    .img-profile {
    max-width: 10rem;
    max-height: 10rem;
    border: 0.5rem solid rgba(255, 255, 255, 0.2);
  }
   
    </style>
    <script type="text/javascript">
	$(document).ready(function(){
		 $("#update").click(function () {
			 $("#memberProfilePanel").attr("action","/myapp/memberProfile");
			 $("#memberProfilePanel").attr("method","post");  		 
			 	
		 });
		 $("#delete").click(function () {
			 $("#memberProfilePanel").attr("action","/myapp/memberDelete");
			 $("#memberProfilePanel").attr("method","post");  		 
			 	
		 });
  			
   	
	});
   
    </script>
    
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/include/Header.jsp"/>
    <!-- session valid -->
	<c:if test="${empty sessionScope.member_Id }">
	
		
		<div class=" container bg-light">
		
		  <main>
		    <div class="py-5 text-center">
		      <img class="d-block mx-auto mb-4" src="./resources/memberProfile/images/test.png" alt="" width="72" height="57">
		      <h2>Edit Profile</h2>
		      <p class="lead">show Profile and Modify Member data</p>
		    </div>
		
		    <div class="row g-3">
		      <div class="col-md-5 col-lg-4 order-md-last">
		        <h4>Member Image</h4>
		      	<div class="card p-2">
		        	<img class="img-fluid img-profile rounded-circle mx-auto mb-2"  src="./resources/memberProfile/images/test.png"/>
				</div>
		        <form class="card p-2">
		          <div class="input-group">
		            <input type="file" class="form-control-file" placeholder="Select your Image">
		            <hr>
		            <button type="submit" class=" btn-block btn btn-secondary">submit</button>
		          </div>
		        </form>
		      </div>
		      <div class="col-md-7 col-lg-8">
		        <h4 class="mb-3">My Info</h4>
		        <form  id="memberProfilePanel"class="needs-validation" novalidate>
		        
		          <div class="row g-3">
		            <div class="col-sm-6 " >
		              <label for="firstName" class="form-label">username</label>
		            
		              <input type="text" class="form-control" name="member_Username"
		               id="username" placeholder="username" value="${member.member_Username }" required>

		              <div class="invalid-feedback">
		                Valid first name is required.
		              </div>  
		            </div>
		            
					<div class="col-sm-2">
					  <label for="firstName" class="form-label">&nbsp;</label>
		              <input type="button" class="form-control btn btn-primary" value="..."/>		                 
					</div>
					
				
					<div class="col-12">
		              <label for="password" class="form-label">Password <span class="text-muted">(Optional)</span></label>
		              <input type="password" class="form-control" name="member_Password" id="password" placeholder="passwordc">
		              <label for="password" class="form-label">Password valid<span class="text-muted">(Optional)</span></label>
		              <input type="password" class="form-control"  id="password valid" placeholder="passwordc">
		            </div>
		            
		            <div class="col-12">
		              <label for="email" class="form-label">Email <span class="text-muted">(Optional)</span></label>
		              <input type="email" class="form-control" name="member_Email" placeholder="you@example.com" value="${member.member_Email }">
		              <div class="invalid-feedback">
		                Please enter a valid email address for shipping updates.
		              </div>
		            </div>
		
		      
		            
		          </div>
		          <div class="col-13">
		          <label>&nbsp;</label>
		 		  <button id="update"class="w-100 btn btn-primary btn-lg" >Update</button>
		 		  </div>
		 		
		          <hr class="my-4">
		
		          <hr class="my-4">
		
		          <h4 class="mb-3">MemberDelete</h4>
		
		
		          <button id="delete" class="btn btn-primary btn-lg" type="submit">Delete</button>
		       
		          <hr class="my-4">
		          </form>
		      </div>
		    </div>
		  </main>
		
		  <footer class="my-5 pt-5 text-muted text-center text-small">
		    <p class="mb-1">&copy; 2017â2020 Company Name</p>
		    <ul class="list-inline">
		      <li class="list-inline-item"><a href="#">Privacy</a></li>
		      <li class="list-inline-item"><a href="#">Terms</a></li>
		      <li class="list-inline-item"><a href="#">Support</a></li>
		    </ul>
		  </footer>
		</div>
	</c:if>


	<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
      <script src="./js/memberProfile.js"></script>

  </body>
</html>
