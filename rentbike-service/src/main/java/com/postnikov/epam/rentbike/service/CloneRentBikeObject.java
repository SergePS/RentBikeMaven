package com.postnikov.epam.rentbike.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.ServerException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CloneRentBikeObject<T> {

	private static Logger logger = LogManager.getLogger(CloneRentBikeObject.class);

	@SuppressWarnings("unchecked")
	public T clone(T originalObject) throws ServerException {

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);) {
			objOutputStream.writeObject(originalObject);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return (T) objInputStream.readObject();

		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.ERROR, "Clone object error, " + e.getMessage());
			throw new ServerException("An exception was occurred while clone object");
		}
	}

}
