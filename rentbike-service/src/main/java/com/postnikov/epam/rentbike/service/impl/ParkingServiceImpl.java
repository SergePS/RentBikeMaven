package com.postnikov.epam.rentbike.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.dao.DAOFactory;
import com.postnikov.epam.rentbike.dao.ParkingDAO;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.validator.ParkingParameterValidator;
import com.postnikov.epam.rentbike.validator.UserParameterValidator;

public class ParkingServiceImpl implements ParkingService {
	
	private static Logger logger = LogManager.getLogger(ParkingServiceImpl.class);

	@Override
	public List<Parking> takeAllParking() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO(); 

		try {
			return parkingDAO.takeAllParking();
		} catch (DAOException e) {
			throw new ServiceException("An error occurred while take all parkings", e);
		}
	}

	@Override
	public void addParking(Map<String, String> requestParameters) throws ServiceException {

		Parking parking = new Parking();
		
		String address = requestParameters.get(RequestParameter.ADDRESS.parameter());
		if(!ParkingParameterValidator.addressValidate(address)) {
			throw new  ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		parking.setAddress(address);
		
		String capacityString = requestParameters.get(RequestParameter.CAPACITY.parameter());
		if(!ParkingParameterValidator.capacityValidate(capacityString)) {
			throw new  ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}	
		parking.setCapacity(Integer.parseInt(capacityString));

		DAOFactory daoFactory = DAOFactory.getInstance();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO();

		try {
			parkingDAO.addParking(parking);
		} catch (DAOException e) {
			throw new ServiceException("An error occurred while adding the parking", e);
		}

	}
	
	@Override
	public void updateParking(Map<String, String> requestParameters) throws ServiceException {
		
		Parking parking = new Parking();
		
		String parkingIdString = requestParameters.get(RequestParameter.PARKING_ID.parameter());
		if(!UserParameterValidator.idValidate(parkingIdString)) {
			logger.log(Level.ERROR, "pakingIdString - " + parkingIdString + " is wrong");
			throw new  ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long parkingId = Long.parseLong(parkingIdString);
		parking.setId(parkingId);
		
		
		String address = requestParameters.get(RequestParameter.ADDRESS.parameter());
		if(!ParkingParameterValidator.addressValidate(address)) {
			throw new  ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		parking.setAddress(address);
		
		String capacityString = requestParameters.get(RequestParameter.CAPACITY.parameter());
		if(!ParkingParameterValidator.capacityValidate(capacityString)) {
			throw new  ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}	
		parking.setCapacity(Integer.parseInt(capacityString));

		DAOFactory daoFactory = DAOFactory.getInstance();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO();

		try {
			parkingDAO.updateParking(parking);
		} catch (DAOException e) {
			throw new ServiceException("An error occurred while updating the parking", e);
		}
	}

	@Override
	public Parking findParkingById(String pakingIdString) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO();
		
		if(!UserParameterValidator.idValidate(pakingIdString)) {
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long parkingId = Long.parseLong(pakingIdString);
		
		try {
			return parkingDAO.findParkingById(parkingId);
		} catch (DAOException e) {
			throw new ServiceException("An error occurred while find the parking by id", e);
		}
	}

}
