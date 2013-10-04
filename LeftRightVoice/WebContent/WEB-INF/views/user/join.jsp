<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<html>
<head>
<title>::: Join :::</title>
<style>
	.errorMessage { border: 2px solid red; }
</style>
</head>
<body>

<form:form modelAttribute="user" enctype="multipart/form-data">
	<form:label path="username"> <font color="red">*</font>ID : </form:label>
	<form:input path="username"/>
	<form:errors cssClass="errorMessage" path="username" />
	<p/>
	<form:label path="password"> <font color="red">*</font>비밀번호 : </form:label>
	<form:password path="password"/>
	<form:errors cssClass="errorMessage" path="password" />
	<p/>
	<form:label path="email"> <font color="red">*</font>이메일: </form:label>
	<form:input path="email"/>
	<form:errors cssClass="errorMessage" path="email" />
	<p/>
	<form:label path="politicsColor"> <font color="red">*</font>성향 </form:label>
	<form:radiobuttons path="politicsColor" items="${politicsColorList}" itemLabel="name" itemValue="value"/>
	<form:errors cssClass="errorMessage" path="politicsColor" />
	<p/>
	프로필 사진 <input type="file" name="file"/>
	<p/>
	<input type="submit" value="JOIN" />
</form:form>

</body>
</html>