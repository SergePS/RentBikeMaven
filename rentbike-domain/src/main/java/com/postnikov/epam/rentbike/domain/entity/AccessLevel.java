package com.postnikov.epam.rentbike.domain.entity;

/**
 * @author Sergey Postnikov
 * Type of user role.
 *
 */
public enum AccessLevel {
	
	/**
	 * Access level - User and higher. 
	 */
	USER(1), 
	
	/**
	 * Access level - Administrator and higher. 
	 */
	ADMIN(2);
	
	private int level;
	
	private AccessLevel(int level) {
		this.level = level;
	}

	/**
	 * @return access level of the role.
	 */
	public int level() {
		return level;
	}

}
