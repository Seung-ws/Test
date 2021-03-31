<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n/board" />
<html>
<head>
<jsp:include page="/WEB-INF/views/include/staticFile.jsp"/>

<link rel="stylesheet" href="/resources/boardView/css/boardView.css"/>

</head>
<body>
<jsp:include page="/WEB-INF/views/include/Header.jsp"/>
<div class="boardViewPanel">
	<div class="boardViewContents">
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
					<a href='<c:url value="/boardList/cat/${category_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="BOARD_LIST"/></button></a>
					<a href='<c:url value="/boardWrite/${category_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
					<a href='<c:url value="/boardReply/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
					<a href='<c:url value="/boardUpdate/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
					<a href='<c:url value="/boardDelete/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
					</td>
				</tr>
				<tr>
					<td width="20%"><fmt:message key="BOARD_ID"/></td>
					<td>${board.board_Id}</td>
				</tr>
				<tr>
					<td width="20%"><fmt:message key="WRITER"/></td>
					<td>${board.board_Writer}</td>
				</tr>
				<tr>
					<td width="20%"><fmt:message key="WRITE_DATE"/></td>
					<td>${board.board_WriteDate} </td>
				</tr>
				<tr>
					<td><fmt:message key="SUBJECT"/> </td>
					<td>${board.board_Title}</td>
				</tr>
				<tr>
					<td><fmt:message key="CONTENT"/></td>
					<td class="board_content">${board.board_Content}</td>
				</tr>
				<c:if test="${!empty board.file_Name}">
				<tr>
					<td><fmt:message key="FILE"/></td>
					<td>
					<%--c:if test="${!empty sessionScope.user_id}"--%>
					<c:set var="len" value="${fn:length(board.file_Name)}"/>
					<c:set var="file_type" value="${fn:toUpperCase(fn:substring(board.file_Name, len-4, len))}"/>
					<c:if test="${(file_Type eq '.JPG') or (file_Type eq 'JPEG') or (file_Type eq '.PNG') or (file_Type eq '.GIF')}"><img src='<c:url value="/file/${board.file_Id}"/>' class="img-thumbnail"><br></c:if>
					<%--/c:if--%>
					<a href='<c:url value="/file/${board.file_Id}"/>'>${board.file_Name} (<fmt:formatNumber>${board.file_Size}</fmt:formatNumber>byte)</a>
					</td>
				</tr>
				</c:if>
				<tr>
					<td colspan=2 align="right">
						<a href='<c:url value="/boardList/cat/${category_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="BOARD_LIST"/></button></a>
						<a href='<c:url value="/boardWrite/${category_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="WRITE_NEW_ARTICLE"/></button></a>
						<a href='<c:url value="/boardReply/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="REPLY"/></button></a>
						<a href='<c:url value="/boardUpdate/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="UPDATE"/></button></a>
						<a href='<c:url value="/boardDelete/${board.board_Id}"/>'><button type="button" class="btn btn-info"><fmt:message key="DELETE"/></button></a>
					</td>
				</tr>
			
				</table>
				</div>
			</div>
		</div>
			<span>board_Id:${board.board_Id}</span><br>
			<span>category_Id:${board.category_Id}</span><br>
			<span>master_Id:${board.board_Master_Id}</span><br>
			<span>reply_Number:${board.reply_Board_Number}</span><br>
			<span>reply_Board_Step:${board.reply_Board_Step}</span><br>
			<span>reply_Board_StartBoard:${board.reply_Board_StartBoard}</span><br>
	<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
