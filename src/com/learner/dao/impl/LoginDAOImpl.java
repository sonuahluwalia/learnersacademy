package com.learner.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.learner.dao.LoginDAO;
import com.learner.exception.DAOException;
import com.learner.model.Class;
import com.learner.model.Student;
import com.learner.util.DBConnection;
import com.learner.util.ExceptionHandler;

public class LoginDAOImpl implements LoginDAO {

	@Override
	public boolean checkLogin(Map<String, String> map) throws DAOException {
		Connection connection = DBConnection.getConnection();
		//System.out.println("Connection successful");

		String sql = "select username, password from login where username = ? and password = ? ";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, map.get("username"));
			stmt.setString(2, map.get("password"));

			ResultSet rs = stmt.executeQuery();
	
			if (rs.next() == false) {
				throw new DAOException("Wrong Username/Password. <br> Please enter again.");
			} else {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));

		}
	}

}
