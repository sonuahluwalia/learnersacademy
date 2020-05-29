<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
</head>
<body>
	<c:choose>
		<c:when  test="${sessionScope.login != null}">
			<h3>Home Page</h3>
			<form method="post" action="home">
			<input type="submit" name="logout" value="Logout"/>
			</form>
			Welcome to the Learner's Academy.<br/>
			Please choose any operation below:<br/>
			<form method="post" action="subjects">
			<input type="hidden" name="action" value="display"/>
			1. Set up a master list of all the subjects -->
			<input type="submit"value="Subjects"/>
			</form>
			<form method="post" action="teachers">
			<input type="hidden" name="action" value="display"/>
			2. Set up a master list of all the teachers -->
			<input type="submit"value="Teachers"/>
			</form>
			<form method="post" action="classes">
			<input type="hidden" name="action" value="display"/>
			3. Set up a master list of all the classes -->
			<input type="submit"value="Classes"/>
			</form>
			<form method="post" action="students">
			<input type="hidden" name="action" value="display"/>
			4. Set up a master list of all the students -->
			<input type="submit"value="Students"/>
			</form>
		</c:when>
		<c:otherwise>
			<jsp:forward page="login.jsp"></jsp:forward>
		</c:otherwise>
	</c:choose>

</body>
</html>