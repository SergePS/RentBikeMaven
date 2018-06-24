package com.postnikov.epam.rentbike.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;

/**
 * @author Sergey Postnikov
 *
 */
public class ApplicationProperty {

	private static Logger logger = LogManager.getLogger(ApplicationProperty.class);

	private static final String PROPERTIES = "application_properties";

	/**
	 * Returns Properties object, that contains application properties.
	 * 
	 * @return Properties
	 */
	public static Properties takeProperty() {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		Properties properties = new Properties();

		try (InputStream fileInput = classLoader.getResourceAsStream(PROPERTIES)) {

			properties.load(fileInput);

		} catch (IOException e) {
			logger.log(Level.FATAL, "Error reading properties" + ConvertPrintStackTraceToString.convert(e));
			throw new RuntimeException();
		}  
		return properties;
	}

}
