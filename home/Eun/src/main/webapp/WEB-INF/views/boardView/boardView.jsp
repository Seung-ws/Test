<%@ page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
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
		<a href='<c:url value="/board/write/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
		<a href='<c:url value="/board/reply/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
		<a href='<c:url value="/board/update/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
		<a href='<c:url value="/board/delete/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
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
		<%--c:if test="${!empty sessionScope.userid}"--%>
		<c:set var="len" value="${fn:length(board.file_name)}"/>
		<c:set var="filetype" value="${fn:toUpperCase(fn:substring(board.file_name, len-4, len))}"/>
		<c:if test="${(filetype eq '.JPG') or (filetype eq 'JPEG') or (filetype eq '.PNG') or (filetype eq '.GIF')}"><img src='<c:url value="/file/${board.file_id}"/>' class="img-thumbnail"><br></c:if>
		<%--/c:if--%>
		<a href='<c:url value="/file/${board.file_id}"/>'>${board.file_name} (<fmt:formatNumber>${board.file_size}</fmt:formatNumber>byte)</a>
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan=2 align="right">
			<a href='<c:url value="/boardList/cat/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="BOARD_LIST"/></button></a>
			<a href='<c:url value="/board/write/${category_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
			<a href='<c:url value="/board/reply/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
			<a href='<c:url value="/board/update/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
			<a href='<c:url value="/board/delete/${board.board_id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
		</td>
	</tr>
	</table>
</div>
</div>
<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
