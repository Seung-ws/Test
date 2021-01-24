<%@ page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jk" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="i18n/board" />

<html>
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>
<div class="container">
	<div class="pg-opt">
	    <div class="row">
	        <div class="col-md-6 pc">
	            <h2><fmt:message key="BOARD_LIST"/> 
	            <c:if test="${empty name}">
	            <small style="color:red;"><fmt:message key="LOGIN"/></small>
	            </c:if>
	            </h2>
	        </div>
	        <div class="col-md-6">
	            <ol class="breadcrumb">
	                <li><fmt:message key="BOARD"/></li>
	                <li class="active"><fmt:message key="BOARD_LIST"/></li>
	            </ol>
	        </div>
	    </div>
    </div>
	${message}
	<div class="content">
		<form action="<c:url value='/board/search/1'/>" method="get">
			<div class="pull-right" style="margin-bottom: 5px;">
			<div class="col-xs-9">
		        <input type="text" name="keyword" class="form-control">
		    </div>
		        <input type="submit" class="btn btn-warning" value="<fmt:message key="SEARCH"/>">
			</div>
		</form>
	    <table class="table table-hover table-bordered">
		<thead>
		<tr>
			<!-- td class="pc"><fmt:message key="NO"/></td-->
			<td><fmt:message key="BOARD_ID"/></td>
			<td class="pc"><fmt:message key="WRITER"/></td>
			<td><fmt:message key="SUBJECT"/></td>
			<td class="pc"><fmt:message key="WRITE_DATE"/></td>
			<td class="pc"><fmt:message key="READ_COUNT"/></td>
			<!--td class="pc"><fmt:message key="FILE"/></td-->
		</tr>
		</thead>
		<c:set var="seq" value="${(page-1)*10}" scope="page" />
		<c:forEach var="board" items="${boardList}">
		<tr>
			<c:set var="seq" value="${seq + 1}" scope="page"/>
			<!-- td class="pc">${seq}</td-->
			<td>${board.board_id}<!-- (${board.category_id})--></td>
			<td class="pc">${board.writer}</td>
			<td>
			<jk:reply reply_num="${board.reply_number}" reply_step="${board.reply_step}"/>
			<a href='<c:url value="/board/${board.board_id}"/>'>${board.title}</a>
			</td>
			<td class="pc"><fmt:formatDate value="${board.writeDate}" pattern="YYYY-MM-dd"/></td>
			<td class="pc">${board.read_count}</td>
			<!-- td class="pc"><span class="glyphicon glyphicon-file"></span-->
		</tr>
		</c:forEach>
		</table>

		<table class="table">
		<tr>
			<td align="left">
				<jk:paging category_id="${category_id}" totalPageCount="${totalPageCount}" nowPage="${page}"/>
			</td>
			<td align="right">
				<a href='<c:url value="/boardWrite/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
			</td>
		</tr>
		</table>
	</div>
</div>
<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
