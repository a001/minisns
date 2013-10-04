<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::: User List :::</title>
<script type="text/javascript" src="../resources/js/jquery-1.9.0.min.js" ></script>
<script type="text/javascript" src="../resources/js/jquery.form.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#checkall").click(function(){
		if(this.checked){
			$(":checkbox").prop("checked", true);
		}else{
			$(":checkbox").prop("checked", false);
		}
	});
});
</script>
</head>
<body>
<form method="POST">
유저 목록 <p />
<!-- 모델에 추가된 현재 로그인한 SecurityUser 의 친구목록을 자동 checked 한다 -->
<!-- items의 userlist 는 전체 userlist이고 path의 user는 friendlist가 셋팅된 현재 로그인한 SecurityUser이다 -->
<input type="checkbox" id="checkall" name="checkall" /><p />
<form:checkboxes id="userlist" name="userlist" items="${userlist}" path="user.friendlist" delimiter="<br />" />
<p />
<input type="submit" value="팔로우" />
</form>
<br />
</body>
</html>