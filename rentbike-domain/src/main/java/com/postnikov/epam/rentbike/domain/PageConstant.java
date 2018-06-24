package com.postnikov.epam.rentbike.domain;

public class PageConstant {
	
	//user folder
	public static final String ADMIN_PAGE = "/jsp/user/admin.jsp";
	public static final String LOGIN_PAGE = "/jsp/user/login.jsp";
	public static final String USER_PAGE = "/jsp/user/user.jsp";
	public static final String ORDER_PAGE = "/jsp/user/order.jsp";
	public static final String ORDER_REPORT_PAGE = "/jsp/user/orderReport.jsp";
	
	//bike folder
	public static final String ADD_BIKE_PAGE = "/jsp/bike/addBike.jsp";
	public static final String BIKE_CATALOG_PAGE = "/jsp/bike/bikeCatalog.jsp";
	public static final String BIKE_PRODUCT_CATALOG_PAGE = "/jsp/bike/bikeProductCatalog.jsp";
	public static final String BIKE_PURCHASE_PAGE = "/jsp/bike/bikePurchase.jsp";
	public static final String BIKE_TYPE_PAGE = "/jsp/bike/bikeType.jsp";
	public static final String BRAND_PAGE = "/jsp/bike/brand.jsp";

	//parking
	public static final String ADD_PARKING_PAGE = "/jsp/parking/addParking.jsp";
	public static final String PARKING_PAGE = "/jsp/parking/parking.jsp";
	
	//error
	public static final String ERROR_PAGE = "/jsp/error/error.jsp";
	
	//redirect
	public static final String REDIRECT_TO_HOME_PAGE = "/FrontController?command=home";
	public static final String REDIRECT_TO_BIKE_PURCHASE_PAGE = "/FrontController?command=go_to_bike_purchase";
	public static final String REDIRECT_TO_PARKING_PAGE = "/FrontController?command=go_to_parking_page";
	public static final String REDIRECT_TO_LOGIN_PAGE = "/index.jsp";
	

}
