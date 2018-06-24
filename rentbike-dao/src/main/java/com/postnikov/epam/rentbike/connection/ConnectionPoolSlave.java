package com.postnikov.epam.rentbike.connection;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;

/**
 * @author Sergey Postnikov
 * Changes the number of connections depending on the load on the pool.
 */
public class ConnectionPoolSlave extends Thread {

	private static Logger logger = LogManager.getLogger(ConnectionPoolSlave.class);

	private static boolean workThread = true;

	private final static String THREAD_NAME = "GhostThread";
	
	private final static int TIME_STEP_MILLISECONDS = 100;

	private static Properties properties = DBProperty.takeProperty();
	private final static String DEFAULT_CONNECTION_COUNT_KEY = "connectionCount";
	private final static int DEFAULT_CONNECTION_COUNT = Integer
			.parseInt(properties.getProperty(DEFAULT_CONNECTION_COUNT_KEY));
	
	private final static String WAIT_TIME_CONNECTTION_KEY = "waitTimeConnection";
	private final static int WAIT_TIME_CONNECTTION = Integer
			.parseInt(properties.getProperty(WAIT_TIME_CONNECTTION_KEY));

	ConnectionPoolSlave() {
		this.setDaemon(true);
		this.setName(THREAD_NAME);
		logger.log(Level.DEBUG, "GhostThread has started");
	}

	@Override
	public void run() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		 
		int workingConnectionCount = DEFAULT_CONNECTION_COUNT;
		int timeGap = WAIT_TIME_CONNECTTION / (TIME_STEP_MILLISECONDS * 2);
		int lackConnectionTimer = 0;
		int excessConnectionTimer = 0;

		while (workThread) {
			
			int minConectionCount = DEFAULT_CONNECTION_COUNT / 2;
			if(minConectionCount < 1) {
				minConectionCount = 1;
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(TIME_STEP_MILLISECONDS);
			} catch (InterruptedException e) {
				logger.log(Level.DEBUG, "ConnectionPoolSlave exception, " + ConvertPrintStackTraceToString.convert(e));
			}

			int connectionCount = connectionPool.getAvailableConnectionCount();
			
			if (connectionCount < minConectionCount) {
				lackConnectionTimer++;
				excessConnectionTimer = 0;
			}
		
			if (connectionCount > DEFAULT_CONNECTION_COUNT) {
				excessConnectionTimer++;
				lackConnectionTimer = 0;
			}

			if (connectionCount == DEFAULT_CONNECTION_COUNT) {
				excessConnectionTimer = 0;
				lackConnectionTimer = 0;
			}

			if (lackConnectionTimer > timeGap && connectionPool.isConnectionPoolWork()) {
				connectionPool.addAdditionalWripperConnection();
				workingConnectionCount++;
				lackConnectionTimer = 0;
				logger.log(Level.DEBUG, "Lack of connections, added 1 connection, number of working connections = "
						+ workingConnectionCount);
			}

			if (excessConnectionTimer > timeGap && connectionPool.isConnectionPoolWork()) {
				connectionPool.closeAdditionalWripperConnection();
				workingConnectionCount--;
				excessConnectionTimer = 0;
				logger.log(Level.DEBUG, "Excess of connections, closed 1 connection, number of working connections = "
						+ workingConnectionCount);
			}	
		}
		logger.log(Level.DEBUG, "GhostThread stopped");
	}


	/**
	 * Stops thread execution
	 */
	public void stopThread() {
		workThread = false;
	}

}
