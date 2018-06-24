package com.postnikov.epam.rentbike.connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.DAOException;

/**
 * @author Sergey Postnikov
 *
 */
public class ConnectionPool {

	private static Logger logger = LogManager.getLogger(ConnectionPool.class);

	private static ConnectionPoolSlave connectionPoolSlave = new ConnectionPoolSlave();

	private static AtomicBoolean isPoolCreated = new AtomicBoolean(false);
	private static Lock lock = new ReentrantLock();

	private static ConnectionPool pool;
	private static Properties properties = DBProperty.takeProperty();

	private final static String DEFAULT_CONNECTION_COUNT_KEY = "connectionCount";
	private final static int DEFAULT_WRAPPER_CONNECTION_COUNT = Integer
			.parseInt(properties.getProperty(DEFAULT_CONNECTION_COUNT_KEY));

	private final static String MAX_WRAPPER_CONNECTION_COUNT_KEY = "maxConnectionCount";
	private final static int MAX_WRAPPER_CONNECTION_COUNT = Integer
			.parseInt(properties.getProperty(MAX_WRAPPER_CONNECTION_COUNT_KEY));

	private static int currentWrapperConnectionCount = DEFAULT_WRAPPER_CONNECTION_COUNT;

	private final static String WAIT_TIME_CONNECTTION_KEY = "waitTimeConnection";
	private final static int WAIT_TIME_CONNECTTION = Integer
			.parseInt(properties.getProperty(WAIT_TIME_CONNECTTION_KEY));

	private static boolean isConnectionPoolWork;

	private static BlockingQueue<WrapperConnection> wrapperConnectionQueue;

	private ConnectionPool() {
		connectionPoolSlave.start();
	}

	/**
	 * Method that create connection pool
	 * 
	 * @return ConnectionPool pool
	 */
	public static ConnectionPool getInstance() {
		if (!isPoolCreated.get()) {
			lock.lock();
			if (!isPoolCreated.get()) {
				pool = new ConnectionPool();
				wrapperConnectionCreate();
				isPoolCreated.set(true);
			}
			lock.unlock();
		}
		return pool;
	}

	/**
	 * Gives out wrapperConnection
	 * 
	 * @return WrapperConnection wrapperConnection
	 * @throws DAOException
	 */
	public WrapperConnection getWrapperConnection() throws DAOException {
		WrapperConnection wrapperConnection;

		if (!isConnectionPoolWork) {
			throw new DAOException("Connection pool was stopped");
		}

		try {
			wrapperConnection = wrapperConnectionQueue.poll(WAIT_TIME_CONNECTTION, TimeUnit.MILLISECONDS);

			if (!wrapperConnection.isValidConnection()) {
				logger.log(Level.ERROR,
						"When the connection was issued, was found that it was bad, so old connection terminated and new connection created");
				wrapperConnection.closeConnection();
				wrapperConnection = createConnection();
			}

			return wrapperConnection;

		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "Get connection error, " + ConvertPrintStackTraceToString.convert(e));
			throw new DAOException("Failed to get WrapperConnection", e);
		}
	}

	/**
	 * Takes back wrapperConnection.
	 * 
	 * @param wrapperConnection - returned WrapperConnection
	 */
	public void returnWrapperConnection(WrapperConnection wrapperConnection) {
		if (wrapperConnection != null) {
			try {
				if (!wrapperConnection.isValidConnection()) {
					logger.log(Level.ERROR,
							"When the connection was obtained, was found that it was bad, so old connection terminated and new connection created");
					wrapperConnection.closeConnection();
					wrapperConnection = createConnection();
				}
				wrapperConnection.setAutoCommit(true);
				wrapperConnectionQueue.put(wrapperConnection);
			} catch (InterruptedException e) {
				logger.log(Level.ERROR,
						"Cannot add wraperConnection in Pool, " + ConvertPrintStackTraceToString.convert(e));
			}
		}
	}

	/**
	 * Closes all connection from connection pool.
	 */
	public void closeAllWrapperConnection() {

		isConnectionPoolWork = false;

		connectionPoolSlave.stopThread();

		try {
			int availableConnection = wrapperConnectionQueue.size();

			closeAllConnections();

			if (availableConnection == currentWrapperConnectionCount) {
				logger.log(Level.DEBUG, "All connection - " + currentWrapperConnectionCount + " were closed");
			} else {

				TimeUnit.MILLISECONDS.sleep(WAIT_TIME_CONNECTTION);

				if (wrapperConnectionQueue.size() > 0) {
					closeAllConnections();
					logger.log(Level.DEBUG, "All connection - " + currentWrapperConnectionCount + " were closed");
				} else {
					logger.log(Level.ERROR, "Not all connection were closed");
				}
			}

		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "Close connection error, " + ConvertPrintStackTraceToString.convert(e));
		}

		// unloading drivers
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.log(Level.DEBUG, "All drivers deregistred");
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Drivers deregister error");
			}
		}
	}

	boolean isConnectionPoolWork() {
		return isConnectionPoolWork;
	}

	/**
	 * Returns the number of available WrapperConnections.
	 * 
	 * @return available connection count
	 */
	int getAvailableConnectionCount() {
		return wrapperConnectionQueue.size();
	}

	/**
	 * Returns the number of all WrapperConnections.
	 * 
	 * @return all wrapperConnection count
	 */
	int getCurrentWrapperConnectionCount() {
		return currentWrapperConnectionCount;
	}

	void addAdditionalWripperConnection() {
		if (currentWrapperConnectionCount < MAX_WRAPPER_CONNECTION_COUNT) {
			try {
				WrapperConnection wrapperConnection = new WrapperConnection(properties);
				wrapperConnectionQueue.put(wrapperConnection);
				currentWrapperConnectionCount++;
				logger.log(Level.DEBUG, "One wrapperConnection added");
			} catch (InterruptedException e) {
				logger.log(Level.ERROR,
						"WrapperConnection didn't create, " + ConvertPrintStackTraceToString.convert(e));
			}
		}
	}

	void closeAdditionalWripperConnection() {
		if (currentWrapperConnectionCount > DEFAULT_WRAPPER_CONNECTION_COUNT) {
			try {
				wrapperConnectionQueue.poll(WAIT_TIME_CONNECTTION, TimeUnit.MILLISECONDS).closeConnection();
				currentWrapperConnectionCount--;
				logger.log(Level.DEBUG, "WrapperConnection is closed");
			} catch (InterruptedException e) {
				logger.log(Level.ERROR, "WrapperConnection is not closed" + ConvertPrintStackTraceToString.convert(e));
			}
		}
	}

	private static void wrapperConnectionCreate() {

		wrapperConnectionQueue = new LinkedBlockingQueue<WrapperConnection>();
		
		try {
			
			logger.log(Level.DEBUG, "JDBCDriver in pool - " + properties.getProperty("JDBCDriver"));
			
			Class.forName(properties.getProperty("JDBCDriver"));
			WrapperConnection wrapperConnection;

			for (int i = 0; i < DEFAULT_WRAPPER_CONNECTION_COUNT; i++) {
				wrapperConnection = new WrapperConnection(properties);
				wrapperConnectionQueue.put(wrapperConnection);
			}
			logger.log(Level.DEBUG, DEFAULT_WRAPPER_CONNECTION_COUNT + " connection were create");
		} catch (ClassNotFoundException e) {
			logger.log(Level.FATAL, "JDBC Driver not found, " + ConvertPrintStackTraceToString.convert(e));
			throw new RuntimeException();
		} catch (InterruptedException e) {
			logger.log(Level.ERROR,
					"Connection are not added to the Pool, " + ConvertPrintStackTraceToString.convert(e));
		}

		isConnectionPoolWork = true;
	}

	private WrapperConnection createConnection() {
		return new WrapperConnection(properties);
	}

	private void closeAllConnections() throws InterruptedException {
		for (int i = 0; i < wrapperConnectionQueue.size(); i++) {
			wrapperConnectionQueue.poll(WAIT_TIME_CONNECTTION, TimeUnit.MILLISECONDS).closeConnection();
		}
	}

}
