package com.postnikov.epam.rentbike.domain;

/**
 * @author Sergey Postnikov
 * 
 * Parameters that are transmitted through the request.
 */
public enum RequestParameter {
	
	ADDRESS("address"),

	BIKE("bike"),
	BIKE_ID("bikeId"),
	BIKE_COUNT("bikeCount"),
	BIKE_ORDER("bikeOrder"),
	BIKE_RENT_PRICE("rentPrice"),
	BIKE_TYPE_LIST("bikeTypeList"),
	BIKE_TYPE_ID("bikeTypeId"),
	BIKE_TYPE("bikeType"),
	BIKE_VALUE("value"),
	BIKE_LIST("bikeList"),
	
	BIKE_CATALOG_WITH_CHOICE("bikeCatalogWithChoise"),
	BIKE_PRODUCT_CATALOG_WITH_CHOICE("bikeProductCatalogWithChoise"),
	
	BIKE_PRODUCT_ID("bikeProductId"),
	BIKE_PRODUCT_LIST("bikeProductList"),
	
	BIKE_ORDER_LIST("bikeOrderList"),
	
	BIRTHDAY("birthday"),
	BRAND("brand"),
	BRAND_ID("brandId"),
	BRAND_LIST("brandList"),
	
	CAPACITY("capacity"),
	CRADIT_CARD("creditCard"),
	
	CURRENT_PASSWORD("currentPassword"),
	
	FROM_DATE("fromDate"),
	TO_DATE("toDate"),
	
	EMAIL("email"),
	ERROR("error"),
	
	FINISH_PARKING_ID("finishParkingId"),
	
	LOGIN_MENU("loginMenu"),
	
	ISLOGIN("isLogin"),
	IS_ADD_FORM_PARAM("isAddForm"),
	
	LOGIN("login"),

	MAX_SPEED_COUNT("maxSpeedCount"),
	MIN_SPEED_COUNT("minSpeedCount"),
	MESSAGE("message"),
	MODEL("model"),
	
	NAME("name"),
	NEXT_PAGE("next"),
	
	ORDER_ID("orderId"),
	
	PAYMENT("payment"),
	PARKING("parking"),
	PARKING_LIST("parkingList"),
	PARKING_ID("parkingId"),
	PICTURE("picture"),
	PASSWORD("password"),
	PAGE_ACTION("pageAction"),
	PREVIOUS_PAGE("previous"),
	
	RENT_PRICE("rentPrice"),
	
	SECONDS("seconds"),
	MINUTES("minutes"),
	HOURS("hours"),
	DAYS("days"),
	
	
	SPEED_COUNT("speedCount"),
	START_PARKING_ID("startParkingId"),
	START_TIME("startTime"),
	SURNAME("surname"),
	
	ORDER_LIST("orderList"),
	USER_WRONG("userWrong"),
	
	WHEEL_SIZE("wheelSize");

	private String parameter;

	private RequestParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return String representation of the parameter.
	 */
	public String parameter() {
		return parameter;
	}

}
