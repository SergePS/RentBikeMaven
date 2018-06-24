package com.postnikov.epam.rentbike.dao;

import java.util.List;

import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.exception.DAOException;

public interface ParkingDAO {
	
	/**
	 * Adds parking to DB.
	 * 
	 * @param parking.
	 * @throws DAOException if occurred SQL exception.
	 */
	void addParking(Parking parking) throws DAOException;
	
	
	/**
	 * Updates parking in DB.
	 * 
	 * @param parking.
	 * @throws DAOException if occurred SQL exception.
	 */
	public void updateParking(Parking parking) throws DAOException;
	
	/**
	 * Searches parking by id.
	 * 
	 * @param parkingId.
	 * @return Parking if it was found or null if isn't.
	 * @throws DAOException if occurred SQL exception.
	 */
	Parking findParkingById(long parkingId) throws DAOException;
	
	/**
	 * Returns all parking lots.
	 * 
	 * @return List<Parking> parkingList with objects of the parking if they was found or empty list if not. 
	 * @throws DAOException if occurred SQL exception.
	 */
	List<Parking> takeAllParking() throws DAOException;

}
