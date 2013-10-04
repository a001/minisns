<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>::: 글쓰기 :::</title>
<style>
	.errorMessage { border: 2px solid red; }
</style>
</head>
<body>
<div>
	Welcome, <strong><sec:authentication property="principal.username" /></strong>
	<sec:authorize access="hasRole('ROLE_ADMIN') and fullyAuthenticated">
		<c:url value="/account/home" var="accountUrl" />
		<li><a href="${accountUrl}">Administrator</a></li>
	</sec:authorize>
</div>
<form:form modelAttribute="board">
	<form:label path="subject">글제목</form:label>
	<form:input path="subject"/>
	<form:errors cssClass="errorMessage" path="subject" />
	<p/>
	<form:label path="content">글내용</form:label>
	<form:textarea path="content"/>
	<form:errors cssClass="errorMessage" path="content" />
	<input type="submit" value="글쓰기" />
</form:form>
</body>
</html>