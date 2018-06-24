package com.postnikov.epam.command;

/**
 * @author Sergey Postnikov
 * 
 * Parameters received and sent to the session.
 */
public enum SessionParameter {

	BIKE_PRODUCT("bikeProduct"), 
	LANGUAGE("lang"), 
	LOCAL("local"),
	MESSAGE("message"),
	USER("user"), 
	PAGE_INFO("pageInfo"), 
	BIKE_CATALOG_WITH_CHOICE("bikeCatalogWithChoise"),
	BIKE_PRODUCT_CATALOG_WITH_CHOICE("bikeProductCatalogWithChoise");

	private String parameter;

	private SessionParameter(String paramName) {
		this.parameter = paramName;
	}

	/**
	 * @return String representation of the parameter.
	 */
	public String parameter() {
		return parameter;
	}

}
