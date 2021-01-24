<%@ page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
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
                <h2><fmt:message key="UPDATE_ARTICLE"/></h2>
            </div>
            <div class="col-md-6">
                <ol class="breadcrumb">
                    <li><fmt:message key="BOARD"/></li>
                    <li class="active"><fmt:message key="UPDATE_ARTICLE"/></li>
                </ol>
            </div>
        </div>
    </div>
	<div class="content">
	<form action="<c:url value='/boardUpdate'/>" method="post" enctype="multipart/form-data" class="form-horizontal">
	<c:if test="${!empty categoryList}">
	<div class="form-group">
      <label class="control-label col-sm-2" for="name"><fmt:message key="CATEGORY"/></label>
      <div class="col-sm-4">
        <select name="category_id" id="category_id" class="form-control" required>
        	<c:forEach var="category" items="${categoryList}">
        	<option value="${category.category_id}" ${category.category_id eq board.category_id ? "selected" : ""}>${category.category_name}</option>
        	</c:forEach>
        </select>
      </div>
    </div>
    </c:if>
	<div class="form-group">
      <label class="control-label col-sm-2" for="writer"><fmt:message key="WRITER"/></label>
      <div class="col-sm-2">
        <input type="text" name="writer" id="writer" class="form-control" value="${board.writer}" readonly>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="writer_id"><fmt:message key="EMAIL"/></label>
      <div class="col-sm-4">
        <input type="text" name="writer_id" id="writer_id" class="form-control" value="${board.writer_id}" required readonly>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="board_password"><fmt:message key="PASSWORD"/></label>
      <div class="col-sm-2">
        <input type="password" name="board_password" id="board_password" class="form-control" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="title"><fmt:message key="TITLE"/></label>
      <div class="col-sm-8">
        <input type="text" name="title" id="title" class="form-control" value="${board.title}" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="content"><fmt:message key="CONTENT"/></label>
      <div class="col-sm-8">
        <textarea name="content" rows="15" cols="100" class="form-control">${board.content}</textarea>
      </div>
    </div>
    <!-- c:if test="${!empty user_id}"-->
    <div class="form-group">
      <label class="control-label col-sm-2" for="photo"><fmt:message key="FILE"/></label>
      <div class="col-sm-8">
      	<input type="hidden" name="file_id" value="${board.file_id}">
        <input type="file" id="i_file" name="file">${board.file_name}
      </div>
    </div>
    <!-- /c:if-->
    <div class="form-group">
    	<div class="col-sm-offset-2 col-sm-8">
			<input type="hidden" name="board_id" value="${board.board_id}">
			<input type="hidden" name="master_id" value="${board.master_id}">
			<input type="hidden" name="reply_number" value="${board.reply_number}">
			<input type="hidden" name="reply_step" value="${board.reply_step}">
			<input type="submit" id="i_submit" class="btn btn-info" value="<fmt:message key="UPDATE"/>"> <input type="reset" class="btn btn-info" value="<fmt:message key="CANCEL"/>">
		</div>
	</div>
	</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/include/Footer.jsp"/>
</body>
</html>