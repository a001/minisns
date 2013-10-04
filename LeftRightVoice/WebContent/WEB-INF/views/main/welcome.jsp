<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>::: welcome :::</title>
<script type="text/javascript" src="resources/js/jquery-1.9.0.min.js" ></script>
<script type="text/javascript" src="resources/js/jquery.form.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('#searchText').keyup(function(){ // searchText 아이디를 가진 element 에서 keyup 이벤트가 발생하면
		$("#autoText").empty(); // #autoText 아이디를 가진 element 를 비우고
		$.getJSON('autotext.json', {searchText:$("#searchText").val()}, function(data){ //get방식 으로 Json 응답을 요청합니다. 
			$.each(data, function(){ //응답에서 리턴한 결과값의 요소를 반복문 을 돌면서 처리 합니다.
				$("#autoText").append(this.autoText+'</br>'); // autoText 아이디를 가진 element 에 응답 결과값(autoText)을 추가 합니다. 
			});
		});
	});
});

</script>
</head>
<body>
<div>
	<img src="/resources/upload/${user.userPhoto}" width="30" height="30"/><strong><sec:authentication property="principal.username" /></strong>
	Welcome !!
	<c:url value="./secLogout" var="logoutUrl" />
	<a href="${logoutUrl}">LogOUT</a>
	 
</div>
<div>
	<table>
		<tr>
			<td>글번호</td>
			<td>글제목</td>
			<td>작성자</td>
		</tr>
		<c:forEach items="${board}" var="board">
			<tr>
				<td>${board.wno}</td>
				<td><a href="read/${board.wno}">${board.subject}</a></td>
				<td>${board.ownerId}</td>
			</tr>
		</c:forEach>
	</table>
</div> 
<p />
<a href="write">글쓰기</a> <a href="./user/userlist">유저목록</a> <a href="/sns">MINI SNS</a>
<p />
	검색어 자동완성 : <input type="text" id="searchText" /><br />
	<div id="autoText"></div>
	<p />
</body>
</html>