<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.learner.model.Subject"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Master List Subjects</title>
</head>
<body>
	<c:choose>
		<c:when  test="${sessionScope.login != null}">
			<form method="post" action="subjects">
			<input type="submit" name="logout" value="Logout"/>
			</form>
			<h3>Master List Subjects</h3><span class="error">${exception_subjects}</span>
			<div class="wrap">
				<table class="head">
				<form method="post" action="subjects" style="float: left;">
					<input type="hidden" name="action" value="display"/>
					<input type="submit"value="Refresh Data"/>
				</form>
				<form method="post" action="home.jsp">
					<input type="submit"value="Home Page"/>
				</form>
				
				<span class="success">${success_delete}</span>
				<span class="error">${fail_delete}</span></td>
				<span class="error">${exception_delete}</span>
				<span class="error">${subject_id_error}</span>		
				<span class="success">${success_insert}</span>
				<span class="error">${fail_insert}</span></td>
				<span class="error">${exception_insert}</span>
				<span class="success">${success_update}</span>
				<span class="error">${fail_update}</span></td>
				<span class="error">${exception_update}</span>
					<tr>
						<td>Subject Id</td>
						<td>Subject Name</td>
						<td>Class Name</td>
						<td></td>
					</tr>
				</table>
				<div class="inner_table">
					<table>
						<c:forEach var="subject" items="${subjects}" varStatus="status">
		
							<tr>
								<form action="subjects" method="post">
								<td><input type="text" value="${subject.subject_id}" disabled/></td>
								<td><input type="text" name="subjectname" value="${subject.subject_name}"  /></td>
								<td><input type="text" name="classname" value="${classes[status.index].class_name}" /></td>
								
								<td>
								<input type="hidden" name="subjectid" value="${subject.subject_id}" />
								<input type="hidden" name="action" value="update" />													
								<input name="update" type="submit" value="Update"  style="float: left;"/>
								</form>
								<form action="subjects" method="post">
								<input type="hidden" name="action" value="delete" />
								<input type="hidden" name="subject_id" value="${subject.subject_id}"/>
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
							<form method="post" action="subjects">
						
							<input type="hidden" name="action" value="insert" /> 
							
							<td><input type="text" name="subjectid" value="${next_subject_id}" disabled="disabled" />
							<span  class="error">${exception_next_subject_id}</span> </td>
							
							<td><input type="text" name="subjectname" placeholder="Enter Subject Name" required/></td>
				
							<td>
							<select name="classname" id="classname">
								<c:forEach var="classs" items="${classes_names}" varStatus="status">
									  <option value="${classs}">${classs}</option>
								</c:forEach>
							</select>
							</td>
							
							
							<td><input type="submit" value="Add Subject" /> 
							</td>
									
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