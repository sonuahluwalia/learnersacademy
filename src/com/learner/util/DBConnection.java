package com.learner.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.learner.exception.DAOException;

import oracle.jdbc.pool.OracleDataSource;

public class DBConnection {
	
	public static Connection getConnection() throws DAOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));

		}
		Connection connection = null;
		try {
			String username = "learner";
			String password = "learner";
			String dbServiceName = "";
			String dbDatabaseName = "orcl";
			
			OracleDataSource ods = null;
//			Connection connection = null;
//			boolean exceptionRaised = false;
			ods = new OracleDataSource();
			// set the properties that define the connection
			ods.setDriverType("thin"); // type of driver
			ods.setServerName("localhost"); // database server name
			ods.setNetworkProtocol("tcp"); // tcp is the default anyway
			//ods.setDatabaseName(dbServiceName); // Oracle SID
			if (!dbServiceName.isEmpty()) {
				ods.setServiceName(dbServiceName);				
			}
			if (!dbDatabaseName.isEmpty()) {
				ods.setDatabaseName(dbDatabaseName); // Oracle SID				
			}
			
			if ("ora92".equals(dbServiceName)) {
				ods.setPortNumber(1522);
			} else {
				ods.setPortNumber(1521);
			}
			ods.setUser(username); // user name
			ods.setPassword(password); // password
//			System.out.println("URL:" + ods.getURL());
			System.out.flush();
			// get the connection without JNDI
			connection = ods.getConnection();
//			connection.setAutoCommit(false);
			return connection;

		} catch (SQLException e) {
			throw new DAOException(ExceptionHandler.printExceptionDetails(e));
		}
	}
}
