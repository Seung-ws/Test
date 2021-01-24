<%@ page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h2><fmt:message key="DELETE_ARTICLE"/></h2>
            </div>
            <div class="col-md-6">
                <ol class="breadcrumb">
                    <li><fmt:message key="BOARD"/></li>
                    <li class="active"><fmt:message key="DELETE_ARTICLE"/></li>
                </ol>
            </div>
        </div>
    </div>
	<div class="content">
		<h3><fmt:message key="DELETE_MSG"/></h3>
		<form action='<c:url value="/boardDelete"/>' class="form-inline" method="post">
		<input type="hidden" name="board_id" value="${board_id}">
		<input type="hidden" name="reply_number" value="${reply_number}">
		<input type="hidden" name="category_id" value="${category_id}">
		<input type="hidden" name="master_id"  value="${master_id}">
		<div class="form-group">
		<div class="col-sm-8">
		<input type="password" name="board_password" class="form-control" required>
		</div>
		<div class="col-sm-2">
		<input type="submit" class="btn btn-danger" value="<fmt:message key="DELETE_ARTICLE"/>">
		</div>
		</div>
		</form>
		<div class="form-group pc"><div class="col-sm-10">
		<c:if test="${ !empty sessionScope.user_id}">
		로그인 사용자는 아무 비밀번호나 입력해도 글이 삭제됩니다.
		</c:if>
		</div></div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>
