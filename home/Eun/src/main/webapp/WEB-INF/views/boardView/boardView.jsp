<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n/board" />
<html>
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>
<div class="container">
    <div class="pg-opt">
        <div class="row">
            <div class="col-md-6 pc">
                <h2><fmt:message key="CONTENT"/></h2>
            </div>
            <div class="col-md-6">
                <ol class="breadcrumb">
                    <li><fmt:message key="BOARD"/></li>
                    <li class="active"><fmt:message key="CONTENT"/></li>
                </ol>
            </div>
        </div>
    </div>
	<div class="content">

	<table class="table table-bordered">
	<tr class="pc">
		<td colspan=2 align="right">
		<a href='<c:url value="/boardList/cat/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="BOARD_LIST"/></button></a>
		<a href='<c:url value="/boardWrite/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
		<a href='<c:url value="/boardReply/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
		<a href='<c:url value="/boardUpdate/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
		<a href='<c:url value="/boardDelete/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
		</td>
	</tr>
	<tr>
		<td width="20%"><fmt:message key="BOARD_ID"/></td>
		<td>${board.board_id}</td>
	</tr>
	<tr>
		<td width="20%"><fmt:message key="WRITER"/></td>
		<td>${board.writer}</td>
	</tr>
	<tr>
		<td width="20%"><fmt:message key="WRITE_DATE"/></td>
		<td><fmt:formatDate value="${board.writeDate}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><fmt:message key="SUBJECT"/> </td>
		<td>${board.title}</td>
	</tr>
	<tr>
		<td><fmt:message key="CONTENT"/></td>
		<td class="board_content">${board.content}</td>
	</tr>
	<c:if test="${!empty board.file_name}">
	<tr>
		<td><fmt:message key="FILE"/></td>
		<td>
		<%--c:if test="${!empty sessionScope.user_id}"--%>
		<c:set var="len" value="${fn:length(board.file_name)}"/>
		<c:set var="file_type" value="${fn:toUpperCase(fn:substring(board.file_name, len-4, len))}"/>
		<c:if test="${(file_type eq '.JPG') or (file_type eq 'JPEG') or (file_type eq '.PNG') or (file_type eq '.GIF')}"><img src='<c:url value="/file/${board.file_id}"/>' class="img-thumbnail"><br></c:if>
		<%--/c:if--%>
		<a href='<c:url value="/file/${board.file_id}"/>'>${board.file_name} (<fmt:formatNumber>${board.file_size}</fmt:formatNumber>byte)</a>
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan=2 align="right">
			<a href='<c:url value="/boardList/cat/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="BOARD_LIST"/></button></a>
			<a href='<c:url value="/boardWrite/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
			<a href='<c:url value="/boardReply/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
			<a href='<c:url value="/boardUpdate/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
			<a href='<c:url value="/boardDelete/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
		</td>
	</tr>

	</table>
</div>
</div>
			<span>boardid:${board.board_id}</span><br>
			<span>category_id:${board.category_id}</span><br>
			<span>master_id:${board.master_id}</span><br>
			<span>reply_number:${board.reply_number}</span><br>
			<span>reply_step:${board.reply_step}</span><br>
			<span>reply_parents_number:${board.reply_parents_number}</span><br>
		
<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
