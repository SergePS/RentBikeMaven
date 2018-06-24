package com.postnikov.epam.rentbike.connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.exception.DAOException;

public class WrapperConnection {

	private static Logger logger = LogManager.getLogger(WrapperConnection.class);

	private Connection connection;

	private final static String DB_PATH = "path";
	private final static String DB_LOGIN = "login";
	private final static String DB_PASSWORD = "password";

	WrapperConnection(Properties dbProperties) {
		String path = dbProperties.getProperty(DB_PATH);
		String login = dbProperties.getProperty(DB_LOGIN);
		String password = dbProperties.getProperty(DB_PASSWORD);

		try {
			connection = DriverManager.getConnection(path, login, password);
		} catch (SQLException e) {
			logger.log(Level.ERROR,
					"WrapperConnection was not created because connection was not created, " + e.getMessage());
		}
	}

	public Statement getStatement() throws DAOException {
		if (connection == null) {
			throw new DAOException("Error: connection is null");
		} else {
			try {
				Statement statement = connection.createStatement();
				return statement;
			} catch (SQLException e) {
				throw new DAOException("Error getting Statement", e);
			}
		}
	}

	public PreparedStatement getPreparedStatement(String sqlQuery) throws DAOException{
		if (connection == null) {
			throw new DAOException("Error: connection is null");
		} else {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				return preparedStatement;
			} catch (SQLException e) {
				logger.log(Level.FATAL, "Error geting preparedStatement, " + e.getMessage());
				throw new RuntimeException();
			}
		}
	}

	public CallableStatement getCallableStatement(String prepareCall) throws DAOException {
		if (connection == null) {
			throw new DAOException("Error: connection is null");
		} else {
			try {
				CallableStatement callableStatement = connection.prepareCall(prepareCall);
				return callableStatement;
			} catch (SQLException e) {
				throw new DAOException("Get CallableStatement error", e);
			}
		}
	}

	public void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Connection statemetn close error, " + e.getMessage());
			}
		}
	}

	void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Close connection error, " + e.getMessage());
			}
		}

	}

	boolean isValidConnection() {
		try {
			return connection.isValid(0);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Check conntction error, " + e.getMessage());
		}
		return false;
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			logger.log(Level.FATAL, "Cannot rollback transaction");
			throw new RuntimeException();
		}
	}

	public void setAutoCommit(boolean flag) {
		try {
			connection.setAutoCommit(flag);
		} catch (SQLException e) {
			logger.log(Level.FATAL, "Cannot change AutoCommit parameter");
			throw new RuntimeException();
		}
	}

}
