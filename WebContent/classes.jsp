<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.learner.model.Teacher"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Master List Classes</title>
</head>
<body>
	<c:choose>
		<c:when  test="${sessionScope.login != null}">
			<h3>Master List Classes</h3>
			<span class="error">${exception_all_data_classes}</span>
			<span class="error">${exception_logic}</span>
			<div class="wrap">
				<table class="head">
				<form method="post" action="home">
				<input type="submit" name="logout" value="Logout" style="float: left;"/>
				</form>
				<form method="post" action="classes">
					<input type="hidden" name="action" value="display"/>
					<input type="submit"value="Refresh Data"/>
				</form>
				<form method="post" action="home.jsp">
					<input type="submit"value="Home Page"/>
				</form>
				<span class="success">${success_delete}</span>
				<span class="error">${fail_delete}</span></td>
				<span class="error">${exception_delete}</span>
				<span class="error">${class_id_error}</span>		
				<span class="success">${success_insert}</span>
				<span class="error">${fail_insert}</span></td>
				<span class="error">${exception_insert}</span>
				<span class="success">${success_update}</span>
				<span class="error">${fail_update}</span></td>
				<span class="error">${exception_update}</span>
				<span  class="error">${exception_next_class_id}</span>					
					<tr>
						<td>Class Id</td>
						<td>Class Name</td>
						<td></td>
						
					</tr>
				</table>
				<div class="inner_table">
					<table>
						<c:forEach var="clas" items="${classes}" varStatus="status">
							<tr>
								<form action="classes" method="post">
								<td><input type="text" value="${clas.class_id}" disabled/></td>
								<td><input type="text" name="classname" value="${clas.class_name}"  /></td>
								
								<td>
								<input type="hidden" name="classid" value="${clas.class_id}" />
								<input type="hidden" name="action" value="update" />													
								<input name="update" type="submit" value="Update"  style="float: left;"/>
								</form>
								<form action="classes" method="post">
								<input type="hidden" name="action" value="delete" />
								<input type="hidden" name="classid" value="${clas.class_id}" />
								<input type="submit" value="Delete"/>
								</form>
								</td>						
							<tr>
						</c:forEach>
				
					</table>
				</div>
			</div>
		<br />
			<div class="wrap">
				<div class="inner_table">
					<table>
						<tr>
							<form method="post" action="classes">				
							<input type="hidden" name="action" value="insert" /> 					
							<td><input  type="text" name="classid" value="${next_class_id}" disabled="disabled" /></td>
							<td><input type="text" name="classname" placeholder="Name" required/></td>									
							<td><input type="submit" value="Add Class" /></td>							
							</form>					
						</tr>	
		 			</table>
				</div>
			</div>
			</c:when>
		<c:otherwise>
			<jsp:forward page="login.jsp"></jsp:forward>
		</c:otherwise>
	</c:choose>

</body>
</html>