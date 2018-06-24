package com.postnikov.epam.rentbike.service;

import java.util.List;
import java.util.Map;

import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.exception.ServiceException;

public interface ParkingService {
	
	/**
	 * Validates incoming parameters and adds parking.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @return notice if validation failed or if parking with same address already exists.
	 * @throws ServiceException if any exception occurred.
	 */
	void addParking(Map<String, String> requestParameters) throws ServiceException;
	
	/**
	 * Validates incoming parameters and updates parking.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @throws ServiceException if any exception occurred.
	 */
	void updateParking(Map<String, String> requestParameters) throws ServiceException;
	
	/**
	 * Returns all parking lots.
	 * 
	 * @return list with all parking lots
	 * @throws ServiceException if any exception occurred.
	 */
	List<Parking> takeAllParking() throws ServiceException;
	
	/**
	 * Searches parking by id.
	 * 
	 * @param pakingId as String.
	 * @return {@link Parking}.
	 * @throws ServiceException if any exception occurred.
	 */
	Parking findParkingById(String pakingId) throws ServiceException;

}
