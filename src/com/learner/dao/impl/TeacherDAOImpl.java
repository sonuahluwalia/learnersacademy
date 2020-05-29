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

import com.learner.dao.ClassDAO;
import com.learner.dao.SubjectDAO;
import com.learner.dao.TeacherDAO;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;
import com.learner.model.Class;
import com.learner.model.Subject;
import com.learner.model.Teacher;
import com.learner.util.DBConnection;
import com.learner.util.ExceptionHandler;

public class TeacherDAOImpl implements TeacherDAO {

	public HashMap<String, ArrayList<?>> getAllTeachers() throws DAOException {
		ClassDAO classdao = new ClassDAOImpl();
		SubjectDAO subjectdao = new SubjectDAOImpl();

		ArrayList<Subject> subjectsArraylist = new ArrayList<>();
		ArrayList<Class> classesArraylist = new ArrayList<>();
		ArrayList<Teacher> teachersArraylist = new ArrayList<>();

		HashMap<String, ArrayList<?>> map = new HashMap<>();

		Connection connection = DBConnection.getConnection();
		//System.out.println("Connection successful");

		String sql = "select t.teacher_id, t.teacher_name, t.class_id, t.qualification, "
				+ " t.experience_years, t.age, t.gender, t.subject_id, t.class_id, c.class_name, "
				+ " s.subject_name from teachers t"
				+ " left join classes c on t.class_id = c.class_id"
				+ " left join  subjects s on s.subject_id = t.subject_id";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Master Teacher List has no data");
			} else {
				do {
					subjectsArraylist.add(
							new Subject(rs.getInt("subject_id"), rs.getString("subject_name"), rs.getInt("class_id")));
					classesArraylist.add(new Class(rs.getInt("class_id"), rs.getString("class_name")));
					teachersArraylist.add(new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"),
							rs.getInt("class_id"), rs.getInt("subject_id"), rs.getString("qualification"),
							rs.getInt("experience_years"), rs.getInt("age"), rs.getString("gender").charAt(0)));
				} while (rs.next());
				map.put("subjects", subjectsArraylist);
				map.put("classes", classesArraylist);
				map.put("teachers", teachersArraylist);
				map.put("classes_names", classdao.getAllClassesNames());
				map.put("subjects_names", subjectdao.getAllSubjectNames());
				return map;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
			
 		}
	}

	@Override
	public int getTeacherIdFromTeacherName(String teacher_name) throws DAOException {
		Connection connection = DBConnection.getConnection();

		String sqlTeacherSelect = "select teacher_id, teacher_name from teachers where teacher_name=?";
		PreparedStatement stmt1;
		ResultSet rs = null;
		int teacher_id = 0;
		try {
			stmt1 = connection.prepareStatement(sqlTeacherSelect);
			stmt1.setString(1, teacher_name);
			rs = stmt1.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					teacher_id = rs.getInt(1);
				}
				//System.out.println("DB Teacher id: " + teacher_id);
			}

			return teacher_id;
		} catch (SQLException e) {
			
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public boolean checkTeacherIdClassIdExists(int teacher_id, int class_id) throws DAOException {
		Connection connection = DBConnection.getConnection();

		String sqlTeacherSelect = "select teacher_id from teachers " + "where teacher_id=? and class_id=?";
		PreparedStatement stmt1;
		ResultSet rs = null;
		try {
			stmt1 = connection.prepareStatement(sqlTeacherSelect);
			stmt1.setInt(1, teacher_id);
			stmt1.setInt(2, class_id);
			rs = stmt1.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;

			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));

		}

	}

	@Override
	public int getNextTeacherId() throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sqlTeacherProc = "{call next_teacher_id(?)";
		CallableStatement stmt2 = null;
		try {
			stmt2 = connection.prepareCall(sqlTeacherProc);
			stmt2.registerOutParameter(1, Types.INTEGER);
			stmt2.execute();
			return stmt2.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public int insertTeacher(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		TeacherDAO teacherdao = new TeacherDAOImpl();
		ClassDAO classdao = new ClassDAOImpl();
		SubjectDAO subjectdao = new SubjectDAOImpl();
		
		int	class_id = classdao.getClassIdFromClassName(map.get("classname"));
		int subject_id = subjectdao.getSubjectIdFromSubjectName(map.get("subjectname"));
		
		String sql = "INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, "
				+ " qualification, experience_years, age, gender) " + " VALUES (?, ?, ?, ?, ?, ?, ?, ? ) ";
		PreparedStatement stmt3;
		try {
			stmt3 = connection.prepareStatement(sql);
			stmt3.setInt(1, teacherdao.getNextTeacherId());
			stmt3.setString(2, map.get("teachername"));
			stmt3.setInt(3, class_id);
			stmt3.setInt(4, subject_id);
			stmt3.setString(5, map.get("qualification"));
			stmt3.setString(6, map.get("experience_years"));
			stmt3.setString(7, map.get("age"));
			stmt3.setString(8, map.get("gender"));

			if (stmt3.executeUpdate() == 0) {
				throw new DAOException("Teacher details are not saved");
			} else {
				return 1;
			}
		} catch (SQLException | NumberFormatException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	public boolean insertExistingTeacherNewClass(int teacher_id, int class_id, String teachername) throws DAOException {
		if (checkTeacherIdClassIdExists(teacher_id, class_id)) {
			//System.out.println("Teacher : " + teacher_id + " " + class_id + " exists");
			throw new DAOException("Teacher already teaches this class");
		} else {
			//System.out.println("DB Teacherid: " + class_id);
		//	insertTeacher(teacher_id, teachername, class_id);
			//System.out.println("Existing Teacher with new class inserted");
			return true;
		}

	}

	public ArrayList<String> getAllTeachersNames() throws DAOException {
		Connection connection = DBConnection.getConnection();
		ArrayList<String> teachersArraylist = new ArrayList<>();
		String sql = "select distinct teacher_name from teachers order by teacher_name";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Teachers has no data");
			} else {
				do {
					teachersArraylist.add(rs.getString("TEACHER_NAME"));
				} while (rs.next());
				return teachersArraylist;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public boolean checkIfTeacherClassSubjectExists(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		ClassDAO classdao = new ClassDAOImpl();
		SubjectDAO subjectdao = new SubjectDAOImpl();
		
		int class_id = classdao.getClassIdFromClassName(map.get("classname"));
		int subject_id = subjectdao.getSubjectIdFromSubjectName(map.get("subjectname"));
		
		String sql = "select teacher_id from teachers " + " where teacher_name = ? "
				+ "and class_id = ? and subject_id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, map.get("teachername"));
			stmt.setInt(2, class_id);
			stmt.setInt(3, subject_id);
			
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

	@Override
	public int deleteTeacher(int teacher_id) throws DAOException{
		Connection connection = DBConnection.getConnection();
		String sql = "delete from teachers where teacher_id=?";

		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, teacher_id);
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not delete teacher");
			} else {
				return 1;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}
	
	@Override
	public int updateTeacher(Map<String, String> map) throws DAOException {
		
		ClassDAO classdao = new ClassDAOImpl();
		SubjectDAO subjectdao = new SubjectDAOImpl();
		
		int	class_id = classdao.getClassIdFromClassName(map.get("classname"));
		int subject_id = subjectdao.getSubjectIdFromSubjectName(map.get("subjectname"));

		Connection connection = DBConnection.getConnection();

		String sql = "UPDATE teachers SET teacher_name = ?, class_id = ?, subject_id = ?, "
				+ " qualification = ?, experience_years = ?, age = ?, gender = ? WHERE teacher_id = ? ";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, map.get("teachername"));
			stmt.setInt(2, class_id);
			stmt.setInt(3, subject_id);
			stmt.setString(4, map.get("qualification"));
			stmt.setInt(5, Integer.parseInt(map.get("experience_years")));
			stmt.setInt(6, Integer.parseInt(map.get("age")));
			stmt.setString(7, map.get("gender"));
			stmt.setInt(8, Integer.parseInt(map.get("teacherid")));
			
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not update teacher details");
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
