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
import com.learner.util.DBConnection;
import com.learner.util.ExceptionHandler;

public class ClassDAOImpl implements ClassDAO {

	@Override
	public int getClassIdFromClassName(String class_name) throws DAOException {
		Connection connection = DBConnection.getConnection();

		String sqlClassSelect = "select class_id, class_name from classes where class_name=?";
		PreparedStatement stmt1;
		ResultSet rs = null;
		int class_id = 0;
		try {
			stmt1 = connection.prepareStatement(sqlClassSelect);
			stmt1.setString(1, class_name);
			rs = stmt1.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					class_id = rs.getInt(1);
				}
				//System.out.println("DB Class id: " + class_id);
			}

			return class_id;
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public int getNextClassId() throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sqlClassProc = "{call next_class_id(?)";
		CallableStatement stmt2;
		try {
			stmt2 = connection.prepareCall(sqlClassProc);
			stmt2.registerOutParameter(1, Types.INTEGER);
			stmt2.execute();
			return stmt2.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public void insertClass(int class_id, String class_name) throws DAOException {
		Connection connection = DBConnection.getConnection();
		String sqlClassInsert = "insert into classes (class_id, class_name) values (?, ?)";
		PreparedStatement stmt3;
		try {
			stmt3 = connection.prepareStatement(sqlClassInsert);
			stmt3.setInt(1, class_id);
			stmt3.setString(2, class_name);
			if (stmt3.executeUpdate() != 1) {
				throw new DAOException("Class details are not saved");
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	public ArrayList<String> getAllClassesNames() throws DAOException {
		Connection connection = DBConnection.getConnection();
		ArrayList<String> classesArraylist = new ArrayList<>();
		String sql = "select distinct class_name from classes order by class_name";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Classes has no data");
			} else {
				do {
					classesArraylist.add(rs.getString("class_name"));
				} while (rs.next());
				return classesArraylist;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public HashMap<String, ArrayList<?>> getAllClasses() throws DAOException, BusinessException{

		ArrayList<Class> classesArraylist = new ArrayList<>();

		HashMap<String, ArrayList<?>> map = new HashMap<>();

		Connection connection = DBConnection.getConnection();
		//System.out.println("Connection successful");

		String sql = "select class_id, class_name from classes ";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new DAOException("Master Classes List has no data");
			} else {
				do {
					classesArraylist.add(new Class(rs.getInt("class_id"), rs.getString("class_name")));
				} while (rs.next());
				map.put("classes", classesArraylist);
				return map;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
			
 		}
	}

	
	public int deleteClass(int class_id) throws DAOException{
		Connection connection = DBConnection.getConnection();
		String sql = "delete from classes where class_id = ? ";
		//System.out.println("In Delete classdaoimpl: "+class_id);
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, class_id);
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not delete Class");
			} else {
				return 1;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}

	@Override
	public int insertClass(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		ClassDAO classdao = new ClassDAOImpl();
		
		String sql = "INSERT INTO classes(class_id, class_name) VALUES (?, ?) ";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, classdao.getNextClassId());
			stmt.setString(2, map.get("classname"));

			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Class details are not saved");
			} else {
				return 1;
			}
		} catch (SQLException | NumberFormatException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}

	}

	@Override
	public int updateClass(Map<String, String> map) throws DAOException {
			
		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE classes SET class_name = ? WHERE class_id = ? ";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, map.get("classname"));
			stmt.setString(2, map.get("classid"));
			
			if (stmt.executeUpdate() == 0) {
				throw new DAOException("Could not update classes details");
			} else {
				return 1;
			}

		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}

}
