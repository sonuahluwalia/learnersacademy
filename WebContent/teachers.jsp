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
<title>Master List Teachers</title>
</head>
<body>
	<c:choose>
		<c:when  test="${sessionScope.login != null}">
			<h3>Master List Teachers</h3>
			<span class="error">${exception_all_data_teachers}</span>
			<span class="error">${exception_logic}</span>
			<div class="wrap">
				<table class="head">
				<form method="post" action="teachers">
				<input type="submit" name="logout" value="Logout" style="float: left;"/>
				</form>
				<form method="post" action="teachers" ">
					<input type="hidden" name="action" value="display"/>
					<input type="submit"value="Refresh Data"/>
				</form>
				<form method="post" action="home.jsp">
					<input type="submit"value="Home Page"/>
				</form>
				<span class="success">${success_delete}</span>
				<span class="error">${fail_delete}</span></td>
				<span class="error">${exception_delete}</span>
				<span class="error">${teacher_id_error}</span>		
				<span class="success">${success_insert}</span>
				<span class="error">${fail_insert}</span></td>
				<span class="error">${exception_insert}</span>
				<span class="success">${success_update}</span>
				<span class="error">${fail_update}</span></td>
				<span class="error">${exception_update}</span>
					<tr>
						<td>Teacher Id</td>
						<td>Teacher Name</td>
						<td>Class Name</td>
						<td>Subject Name</td>
						<td>Qualification</td>
						<td>Experience Years</td>
						<td>Age</td>
						<td>Gender</td>
						<td></td>
						<td></td>
						
					</tr>
				</table>
				<div class="inner_table">
					<table>
						<c:forEach var="teacher" items="${teachers}" varStatus="status">
							<tr>
								<form action="teachers" method="post">
								<td><input size="8" type="text" value="${teacher.teacher_id}" disabled/></td>
								<td><input size="8" type="text" name="teachername" value="${teacher.teacher_name}"  /></td>
								<td><input size="8" type="text" name="classname" value="${classes[status.index].class_name}" /></td>
								<td><input size="8" type="text" name="subjectname" value="${subjects[status.index].subject_name}" /></td>
								<td><input size="8" type="text" name="qualification" value="${teacher.qualification}" /></td>
								<td><input size="8" type="text" name="experience_years" value="${teacher.experience_years}" /></td>
								<td><input size="8" type="text" name="age" value="${teacher.age}" /></td>
								<td><input size="8" type="text" name="gender" value="${teacher.gender}" /></td>
								
								<td>
								<input type="hidden" name="teacherid" value="${teacher.teacher_id}" />
								<input type="hidden" name="action" value="update" />													
								<input name="update" type="submit" value="Update"  style="float: left;"/>
								</form>
								</td>
								<td>
								<form action="teachers" method="post">
								<input type="hidden" name="action" value="delete" />
								<input type="hidden" name="teacherid" value="${teacher.teacher_id}" />
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
							<form method="post" action="teachers">
						
							<input type="hidden" name="action" value="insert" /> 
							
							<td><input size="7" type="text" name="teacherid" value="${next_teacher_id}" disabled="disabled" />
							<span  class="error">${exception_next_teacher_id}</span> </td>
							
							<td><input size="7" type="text" name="teachername" placeholder="Name" required/></td>
				
							<td>
							<select  name="classname" id="classname" style="width: 80px;">
								<c:forEach var="classs" items="${classes_names}" varStatus="status">
									  <option  value="${classs}">${classs}</option>
								</c:forEach>
							</select>
							</td>
							
							<td>
							<select name="subjectname" id="subjectname" style="width: 80px;">
								<c:forEach var="subject" items="${subjects_names}" varStatus="status">
									  <option value="${subject}">${subject}</option>
								</c:forEach>
							</select>
							</td>
							<td><input size="7" type="text" name="qualification" placeholder="Qualification" required/></td>
							<td><input size="7" type="text" name="experience_years" placeholder="Experience" required/></td>
							<td><input size="7" type="text" name="age" placeholder="Age" required/></td>
							<td><input size="7" type="text" name="gender" placeholder="Gender" required/></td>
							
		
				
							
							<td><input type="submit" value="Add Teacher" /></td>
									
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