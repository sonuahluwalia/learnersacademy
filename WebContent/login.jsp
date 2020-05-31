<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.learner.model.Student"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Login</title>
</head>
<body>
,
<h3>Login Learners Academy</h3>
	<c:choose>
		<c:when  test="${sessionScope.login == null}">

			<form method="post" action="login">
				<span class="error">${error_login}</span><br/>
				<span class="success">${success_login}</span><br/>	
				Username: <input type="text" name="username"/><br/>
				Password: <input type="password" name="password"/><br/>	
				<input type="submit"name="login" value="Login"/>
			</form>
		</c:when>
		<c:when  test="${sessionScope.login != null}">
			<form method="post" action="login">
			<input type="submit" name="logout" value="Logout"/>
			</form>
			
		</c:when>
		<c:otherwise>
			Please close the browser and try again.
		</c:otherwise>
	</c:choose>
</body>
</html>