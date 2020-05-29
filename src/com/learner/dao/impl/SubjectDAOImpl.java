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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.learner.dao.ClassDAO;
import com.learner.dao.SubjectDAO;
import com.learner.exception.DAOException;
import com.learner.model.Subject;
import com.learner.util.DBConnection;
import com.learner.util.ExceptionHandler;
import com.learner.model.Class;

public class SubjectDAOImpl implements SubjectDAO {

	@Override
	public HashMap<String, ArrayList<?>> getAllSubjects() throws DAOException {

		ClassDAO classdao = new ClassDAOImpl();
		ArrayList<Subject> subjectsArraylist = new ArrayList<>();
		ArrayList<Class> classesArraylist = new ArrayList<>();

		HashMap<String, ArrayList<?>> map = new HashMap<>();

		Connection connection = DBConnection.getConnection();
		//System.out.println("Connectioin successful");

		String sql = "select s.subject_id, s.subject_name, c.class_name,  " + "s.class_id \n" + "from subjects s\n"
				+ "left join classes c on s.class_id = c.class_id\n" + "\n" + "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Master Subject List has no data");
			} else {
				do {
					subjectsArraylist.add(
							new Subject(rs.getInt("subject_id"), rs.getString("subject_name"), rs.getInt("class_id")));
					classesArraylist.add(new Class(rs.getInt("class_id"), rs.getString("class_name")));
				} while (rs.next());
				map.put("subjects", subjectsArraylist);
				map.put("classes", classesArraylist);
				map.put("classes_names", classdao.getAllClassesNames());
				return map;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	public int getNextSubjectId() throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sql = "{call next_subject_id(?)";

		try {
			CallableStatement stmt = connection.prepareCall(sql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			return stmt.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}

	@Override
	public int insertSubject(String[] parameters) throws DAOException {
		ClassDAO classdao = new ClassDAOImpl();
		int class_id = 0;
		String subjectname = parameters[0], classname = parameters[1];

		Connection connection = DBConnection.getConnection();
		String sql = "insert into subjects(subject_id, subject_name, class_id)\n" + "values (?, ?, ?)\n" + "";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, getNextSubjectId());
			stmt.setString(2, subjectname);

			class_id = classdao.getClassIdFromClassName(classname);

			stmt.setInt(3, class_id);

			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not insert subject details");
			} else {
				return 1;
			}

		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public int deleteSubject(int subject_id) throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sql = "delete from subjects where subject_id=?";

		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, subject_id);
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not delete subject");
			} else {
				return 1;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public boolean checkIfSubjectClassExists(String[] parameters) throws DAOException {
		Connection connection = DBConnection.getConnection();
		String subjectname = parameters[0], classname = parameters[1];
		ClassDAO classdao = new ClassDAOImpl();
		int class_id = classdao.getClassIdFromClassName(classname);
		String sql = "select subject_id from subjects \n" + " where subject_name = ? and class_id = ? ";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, subjectname);
			stmt.setInt(2, class_id);
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
	public int updateSubject(String[] parameters) throws DAOException {
		
		ClassDAO classdao = new ClassDAOImpl();
		int class_id = 0;
		String subjectname = parameters[0], classname = parameters[1];
		int subjectid = Integer.parseInt(parameters[2]);
		Connection connection = DBConnection.getConnection();
		String sql = "update subjects set subject_name = ?, " + " class_id = ? where subject_id = ? ";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			class_id = classdao.getClassIdFromClassName(classname);
			stmt.setString(1, subjectname);
			stmt.setInt(2, class_id);
			stmt.setInt(3, subjectid);

			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not update subject details");
			} else {
				return 1;
			}

		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		} catch (NumberFormatException e) {
			throw new DAOException("Invalid Input");
		}
	}

	@Override
	public ArrayList<String> getAllSubjectNames() throws DAOException {
		
		Connection connection = DBConnection.getConnection();
		ArrayList<String> subjectsArraylist = new ArrayList<>();
		String sql = "select distinct subject_name from subjects order by subject_name";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Subjects has no data");
			} else {
				do {
					subjectsArraylist.add(rs.getString("subject_name"));
				} while (rs.next());
				return subjectsArraylist;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public int getSubjectIdFromSubjectName(String subjectname) throws DAOException {
		Connection connection = DBConnection.getConnection();

		String sqlClassSelect = "select subject_id from subjects where subject_name=?";
		PreparedStatement stmt1;
		ResultSet rs = null;
		int subject_id = 0;
		try {
			stmt1 = connection.prepareStatement(sqlClassSelect);
			stmt1.setString(1, subjectname);
			rs = stmt1.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					subject_id = rs.getInt(1);
				}
				//System.out.println("DB Subject id: " + subject_id);
			}

			return subject_id;
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}


}
