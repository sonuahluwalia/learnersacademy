<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.learner.model.Student"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Master List Students</title>
</head>
<body>
	<c:choose>
		<c:when  test="${sessionScope.login != null}">

			<h3>Master List Students</h3>
			<span class="error">${exception_all_data_students}</span>
			<span class="error">${exception_logic}</span>
			<div class="wrap">
				<table class="head">
				<form method="post" action="home">
				<input type="submit" name="logout" value="Logout"  style="float: left;"/>
				</form>
				<form method="post" action="students">
					<input type="hidden" name="action" value="display"/>
					<input type="submit"value="Refresh Data"/>
				</form>
				<form method="post" action="home.jsp">
					<input type="submit"value="Home Page"/>
				</form>
				<span class="success">${success_delete}</span>
				<span class="error">${fail_delete}</span></td>
				<span class="error">${exception_delete}</span>
				<span class="error">${student_id_error}</span>		
				<span class="success">${success_insert}</span>
				<span class="error">${fail_insert}</span></td>
				<span class="error">${exception_insert}</span>
				<span class="success">${success_update}</span>
				<span class="error">${fail_update}</span></td>
				<span class="error">${exception_update}</span>
					<tr>
						<td>Student Id</td>
						<td>Student Name</td>
						<td>Class Name</td>
						<td>Age</td>
						<td>Gender</td>
						<td>Maths Grade</td>
						<td>English Grade</td>
						<td>Social Science Grade</td>
						<td>Art Grade</td>
						<td>Science Grade</td>
						<td>Final Grade</td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<div class="inner_table">
					<table>
						<c:forEach var="student" items="${students}" varStatus="status">
							<tr>
								
								<form action="students" method="post">
								<td><input size="8" type="text" value="${student.student_id}" disabled/></td>
								<td><input size="8" type="text" name="studentname" value="${student.student_name}"  /></td>
								<td><input size="8" type="text" name="classname" value="${classes[status.index].class_name}" /></td>
								<td><input size="8" type="text" name="age" value="${student.age}" /></td>
								<td><input size="8" type="text" name="gender" value="${student.gender}" /></td>
								<td><input size="8" type="text" name="mathsgrade" value="${student.maths_grade}" /></td>
								<td><input size="8" type="text" name="englishgrade" value="${student.english_grade}" /></td>
								<td><input size="8" type="text" name="socialsciencegrade" value="${student.social_science_grade}" /></td>
								<td><input size="8" type="text" name="artgrade" value="${student.art_grade}" /></td>
								<td><input size="8" type="text" name="sciencegrade" value="${student.science_grade}" /></td>
								<td><input size="8" type="text" name="finalgrade" value="${student.final_grade}" /></td>
								
								<td>
								<input type="hidden" name="studentid" value="${student.student_id}" />
								<input type="hidden" name="action" value="update" />													
								<input name="update" type="submit" value="Update"  style="float: left;"/>
								</form>
								</td>
								<td>
								<form action="students" method="post">
								<input type="hidden" name="action" value="delete" />
								<input type="hidden" name="studentid" value="${student.student_id}" />
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
							<form method="post" action="students">				
							<input type="hidden" name="action" value="insert" /> 					
							<td><input size="7" type="text" name="studentid" value="${next_student_id}" disabled="disabled" />
							<span  class="error">${exception_next_student_id}</span> </td>					
							<td><input size="7" type="text" name="studentname" placeholder="Name" required/></td>
				
							<td>
							<select  name="classname" id="classname" style="width: 80px;">
								<c:forEach var="classs" items="${classes_names}" varStatus="status">
									  <option  value="${classs}">${classs}</option>
								</c:forEach>
							</select>
							</td>
							
							<td><input size="8" type="text" name="age" placeholder="age" /></td>
							<td><input size="8" type="text" name="gender" placeholder="gender" /></td>
							<td><input size="8" type="text" name="mathsgrade" placeholder="maths grade" /></td>
							<td><input size="8" type="text" name="englishgrade" placeholder="english grade"  /></td>
							<td><input size="8" type="text" name="socialsciencegrade" placeholder="social science grade"  /></td>
							<td><input size="8" type="text" name="artgrade" placeholder="art grade"  /></td>
							<td><input size="8" type="text" name="sciencegrade"  placeholder="science grade"  /></td>
							<td><input size="8" type="text" name="finalgrade"  placeholder="final grade"  /></td>
												
							<td><input type="submit" value="Add Student" /></td>
							<td></td>		
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