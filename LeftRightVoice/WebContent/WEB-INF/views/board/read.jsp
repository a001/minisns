<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>::: 글읽기 :::</title>
<script type="text/javascript" src="resources/js/jquery-1.9.0.min.js" ></script>
<script type="text/javascript" src="resources/js/jquery.form.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	
});
	

</script>
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
	<table>
		<tr>
			<td>글 제목</td>
			<td>${board.subject}</td>
		</tr>
		<tr>
			<td>글 내용</td>
			<td>${board.content}</td>
		</tr>
	</table>
	<a href="../">목록으로</a>
</body>
</html>