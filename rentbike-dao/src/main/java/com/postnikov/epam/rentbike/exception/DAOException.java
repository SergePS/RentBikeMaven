package com.postnikov.epam.rentbike.exception;

public class DAOException extends Exception{
	
	private static final long serialVersionUID = 8683045669240629842L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(String message, Exception e) {
		super(message, e);
	}	

}
