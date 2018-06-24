package com.postnikov.epam.rentbike.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;

public class DBProperty {
	
	private static Logger logger = LogManager.getLogger(DBProperty.class);

	private static final String PROPERTIES = "dbconnect_properties";
	
	DBProperty() {
	}

	public static Properties takeProperty() {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		Properties properties = new Properties();

		logger.log(Level.DEBUG, "try to read property");	//TODO delete	
		
		try (InputStream fileInput = classLoader.getResourceAsStream(PROPERTIES)) {

			properties.load(fileInput);
			logger.log(Level.DEBUG, "JDBCDriver - " + properties.getProperty("JDBCDriver"));	//TODO delete
			

		} catch (IOException e) {
			logger.log(Level.FATAL, "Error reading properties" + ConvertPrintStackTraceToString.convert(e));
			throw new RuntimeException();
		}  
		return properties;
	}
	
}
