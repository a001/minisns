<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>::: Log In :::</title>
</head>
<body>
<form method="POST" action="/j_spring_security_check">
	<label for="id">userid:</label>
	<input type="text" name="j_username"/>
		
	<br />
	<label for="password">pwd :</label>
	<input type="password" name="j_password" />
	<br />
	<label for="_spring_security_remember_me">Remember Me?</label>
	<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true" />
	<br />
	<a href="/user/join">JOIN</a> <input type="submit" value="login" />
</form>

</body>
</html>