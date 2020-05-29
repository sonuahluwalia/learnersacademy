package com.learner.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.bo.StudentDAO;
import com.learner.dao.ClassDAO;
import com.learner.dao.SubjectDAO;
import com.learner.exception.DAOException;
import com.learner.model.Class;
import com.learner.model.Student;
import com.learner.util.DBConnection;
import com.learner.util.ExceptionHandler;

public class StudentDAOImpl implements StudentDAO {

	@Override
	public int getNextStudentId() throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sql = "{call next_student_id(?)";
		CallableStatement stmt2 = null;
		try {
			stmt2 = connection.prepareCall(sql);
			stmt2.registerOutParameter(1, Types.INTEGER);
			stmt2.execute();
			return stmt2.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}
	
	@Override
	public HashMap<String, ArrayList<?>> getAllStudents() throws DAOException {
		ClassDAO classdao = new ClassDAOImpl();
	
		ArrayList<Student> studentArraylist = new ArrayList<>();
		ArrayList<Class> classesArraylist = new ArrayList<>();

		HashMap<String, ArrayList<?>> map = new HashMap<>();

		Connection connection = DBConnection.getConnection();
		//System.out.println("Connection successful");

		String sql = "select s.student_id, s.student_name, c.class_id, c.class_name, s.age, "
				+ " s.gender, s.maths_grade, s.english_grade, s.social_science_grade, art_grade "
				+ " , science_grade, final_grade from students s"
				+ " left join classes c on s.class_id = c.class_id";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Master Student List has no data");
			} else {
				do {
					studentArraylist.add(new Student(rs.getInt("student_id"), rs.getString("student_name"),
							rs.getInt("class_id"), rs.getInt("age"), rs.getString("gender").charAt(0), 
							rs.getInt("maths_grade"), rs.getInt("english_grade"), 
							rs.getInt("social_science_grade"), rs.getInt("art_grade"), 
							rs.getInt("science_grade"), rs.getInt("final_grade")));
					classesArraylist.add(new Class(rs.getInt("class_id"), rs.getString("class_name")));
				} while (rs.next());
				map.put("students", studentArraylist);
				map.put("classes", classesArraylist);
				map.put("classes_names", classdao.getAllClassesNames());
				return map;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
			
 		}
	}

	@Override
	public int deleteStudent(int student_id) throws DAOException{
		Connection connection = DBConnection.getConnection();
		String sql = "delete from students where student_id=?";

		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, student_id);
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not delete student");
			} else {
				return 1;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}


	@Override
	public boolean checkIfStudentClassExists(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		ClassDAO classdao = new ClassDAOImpl();
		
		int class_id = classdao.getClassIdFromClassName(map.get("classname"));
		
		String sql = "select student_id from students " + " where student_id = ? "
				+ " and class_id = ? ";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, class_id);
			stmt.setInt(2, getNextStudentId());
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}
	
	// TODO insert student

	@Override
	public int insertStudent(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		ClassDAO classdao = new ClassDAOImpl();
		
		int	class_id = classdao.getClassIdFromClassName(map.get("classname"));
		
		String sql = "INSERT INTO STUDENTS (student_id, student_name, class_id, age, " 
				+ " gender, maths_grade, english_grade, social_science_grade, art_grade " + 
				" , science_grade, final_grade)  " + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, getNextStudentId());
			stmt.setString(2, map.get("studentname"));
			stmt.setInt(3, class_id);
			stmt.setInt(4, Integer.parseInt(map.get("age")));
			stmt.setString(5, map.get("gender"));
			stmt.setInt(6, Integer.parseInt(map.get("mathsgrade")));
			stmt.setInt(7, Integer.parseInt(map.get("englishgrade")));
			stmt.setInt(8, Integer.parseInt(map.get("socialsciencegrade")));
			stmt.setInt(9, Integer.parseInt(map.get("artgrade")));
			stmt.setInt(10, Integer.parseInt(map.get("sciencegrade")));
			stmt.setInt(11, Integer.parseInt(map.get("finalgrade")));
				
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Student details are not saved");
			} else {
				return 1;
			}
		} catch (SQLException  e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		} catch (NumberFormatException e) {
			throw new DAOException("Invalid Input");
		}

	}

	@Override
	public int updateStudent(Map<String, String> map) throws DAOException {
		
		ClassDAO classdao = new ClassDAOImpl();
		
		int	class_id = classdao.getClassIdFromClassName(map.get("classname"));

		Connection connection = DBConnection.getConnection();

		String sql = "UPDATE students SET student_name = ?, class_id = ?, age = ?,  " + 
				"gender = ?, maths_grade = ?, english_grade = ?, social_science_grade = ?,  " + 
				"art_grade = ? , science_grade = ?, final_grade = ? WHERE student_id = ? ";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, map.get("studentname"));
			stmt.setInt(2, class_id);
			stmt.setInt(3, Integer.parseInt(map.get("age")));
			stmt.setString(4, map.get("gender"));
			stmt.setInt(5, Integer.parseInt(map.get("mathsgrade")));
			stmt.setInt(6, Integer.parseInt(map.get("englishgrade")));
			stmt.setInt(7, Integer.parseInt(map.get("socialsciencegrade")));
			stmt.setInt(8, Integer.parseInt(map.get("artgrade")));
			stmt.setInt(9, Integer.parseInt(map.get("sciencegrade")));
			stmt.setInt(10, Integer.parseInt(map.get("finalgrade")));
			stmt.setInt(11, Integer.parseInt(map.get("studentid")));
			
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not update student details");
			} else {
				return 1;
			}

		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		} catch (NumberFormatException e) {
			throw new DAOException("Invalid Input");
		}

	}



}
