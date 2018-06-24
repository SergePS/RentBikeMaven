package com.postnikov.epam.rentbike.domain.exception;

public enum ExceptionMessage {
	
	BIKE_DUBLICATE_ERROR("bikeDublicateError"),
	BIKE_TYPE_DUBLICATE_ERROR("bikeTypeDublicateError"),
	BRAND_DUBLICATE_ERROR("brandDublicateError"),
	
	CURRENT_PASSW_WRONG("currentPasswordWrong"),
		
	LOGIN_PASSW("loginOrPasswWrong"),
	
	ORDER_NOT_EXIST("orderNotExist"),
	
	PARKING_DUBLICATE_ERROR("parkingDublicateError"),
	
	USER_DUBLICATE_ERROR("userDublicateError"),
	USER_IS_TOO_YOUNG("userTooYoung"),
	
	VALIDATION_ERROR("validationError");
	
	private String message;

	private ExceptionMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
	
}
