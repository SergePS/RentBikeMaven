package com.postnikov.epam.rentbike.service.impl;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.dao.BikeDAO;
import com.postnikov.epam.rentbike.dao.DAOFactory;
import com.postnikov.epam.rentbike.dao.ParkingDAO;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.BikeProductState;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.CloneRentBikeObject;
import com.postnikov.epam.rentbike.service.DateFormatter;
import com.postnikov.epam.rentbike.validator.BikeParameterValidator;
import com.postnikov.epam.rentbike.validator.UserParameterValidator;

public class BikeServiceImpl implements BikeService {

	private static Logger logger = LogManager.getLogger(BikeServiceImpl.class);

	private final static int MAX_SPEED_COUNT = 99;
	private final static String ZERO_PARAM = "0";
	private final static String COMMA = ",";
	private final static String DOT = ".";

	public BikeServiceImpl() {
	}

	@Override
	public Bike addBike(Map<String, String> requestParameters) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		Bike bike = new Bike();
		Brand brand = new Brand();
		String brandIdString = requestParameters.get(RequestParameter.BRAND_ID.parameter());
		if (!UserParameterValidator.idValidate(brandIdString)) {
			logger.log(Level.DEBUG, "brandId - " + brandIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		brand.setId(Long.parseLong(brandIdString));
		bike.setBrand(brand);

		String model = requestParameters.get(RequestParameter.MODEL.parameter());
		if (!BikeParameterValidator.modelValidate(model)) {
			logger.log(Level.DEBUG, "model - " + model + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setModel(requestParameters.get(RequestParameter.MODEL.parameter()));

		String wheelSizeString = (requestParameters.get(RequestParameter.WHEEL_SIZE.parameter()));
		if (!BikeParameterValidator.wheelSizeValidate(wheelSizeString)) {
			logger.log(Level.DEBUG, "wheelSize - " + wheelSizeString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setWheelSize(Integer.parseInt(wheelSizeString));

		String speedCountString = requestParameters.get(RequestParameter.SPEED_COUNT.parameter());
		if (!BikeParameterValidator.speedCountValidate(speedCountString)) {
			logger.log(Level.DEBUG, "speedCount - " + speedCountString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setSpeedCount(Integer.parseInt(speedCountString));

		bike.setPicturePath(requestParameters.get(RequestParameter.PICTURE.parameter()));

		BikeType bikeType = new BikeType();
		String bikeTypeIdString = requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter());
		if (!UserParameterValidator.idValidate(bikeTypeIdString)) {
			logger.log(Level.DEBUG, "bikeTypeId - " + bikeTypeIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bikeType.setId(Long.parseLong(bikeTypeIdString));
		bike.setBikeType(bikeType);

		try {
			return bikeDao.addBike(bike);
		} catch (DAOException e) {
			throw new ServiceException("Add bike exception", e);
		}

	}

	@Override
	public void addBrand(String brandName) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		if (!BikeParameterValidator.brandValidate(brandName)) {
			logger.log(Level.DEBUG, "brandName - " + brandName + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		Brand brand = new Brand();
		brand.setBrand(brandName);

		try {
			bikeDao.addBrand(brand);
		} catch (DAOException e) {
			throw new ServiceException("Add bike brand exception", e);
		}
	}

	@Override
	public void addBikeType(String bikeTypeString) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		if (!BikeParameterValidator.bikeTypeValidate(bikeTypeString)) {
			logger.log(Level.DEBUG, "bikeType - " + bikeTypeString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		BikeType bikeType = new BikeType();
		bikeType.setBikeType(bikeTypeString);

		try {
			bikeDao.addBikeType(bikeType);
		} catch (DAOException e) {
			throw new ServiceException("Add bike type exception", e);
		}
	}

	@Override
	public List<BikeType> takeAllBikeType() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		try {
			return bikeDao.takeAllBikeType();
		} catch (DAOException e) {
			throw new ServiceException("Get bike type exception", e);
		}

	}

	@Override
	public List<Brand> takeAllBrand() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		try {
			return bikeDao.takeAllBrand();
		} catch (DAOException e) {
			throw new ServiceException("Get bike type exception", e);
		}

	}

	@Override
	public List<Bike> findBike(Map<String, String> requestParameters, PageInfo pageInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDAO = daoFactory.getBikeDAO();

		String brandIdString = requestParameters.get(RequestParameter.BRAND_ID.parameter());
		if (!ZERO_PARAM.equals(brandIdString) && !UserParameterValidator.idValidate(brandIdString)) {
			logger.log(Level.DEBUG, "brandId - " + brandIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long brandId = Long.parseLong(brandIdString);

		String bikeTypeIdString = requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter());
		if (!ZERO_PARAM.equals(bikeTypeIdString) && !UserParameterValidator.idValidate(bikeTypeIdString)) {
			logger.log(Level.DEBUG, "bikeTypeId - " + bikeTypeIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long bikeTypeId = Long.parseLong(bikeTypeIdString);

		String model = requestParameters.get(RequestParameter.MODEL.parameter());
		if (!model.isEmpty() && !BikeParameterValidator.modelValidate(model)) {
			logger.log(Level.DEBUG, "model - " + model + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		int minSpeedCount = 0;
		String minSpeedCountString = requestParameters.get(RequestParameter.MIN_SPEED_COUNT.parameter());
		if (!minSpeedCountString.isEmpty() && !ZERO_PARAM.equals(minSpeedCountString)) {
			if (!BikeParameterValidator.speedCountValidate(minSpeedCountString)) {
				logger.log(Level.DEBUG, "minSpeedCountString - " + minSpeedCountString + " is wrong");
				throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
			}
			minSpeedCount = Integer.parseInt(minSpeedCountString);
		}

		int maxSpeedCount = 0;
		String maxSpeedCountString = requestParameters.get(RequestParameter.MAX_SPEED_COUNT.parameter());
		if (!maxSpeedCountString.isEmpty() && !ZERO_PARAM.equals(maxSpeedCountString)) {
			if (!BikeParameterValidator.speedCountValidate(maxSpeedCountString)) {
				logger.log(Level.DEBUG, "maxSpeedCountString - " + maxSpeedCountString + " is wrong");
				throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
			}
			maxSpeedCount = Integer.parseInt(maxSpeedCountString);
		}

		if (maxSpeedCount < minSpeedCount) {
			maxSpeedCount = MAX_SPEED_COUNT;
		}

		try {
			return bikeDAO.findBike(brandId, bikeTypeId, model, minSpeedCount, maxSpeedCount, pageInfo);
		} catch (DAOException e) {
			throw new ServiceException("Find bike exception", e);
		}
	}

	@Override
	public Bike takeBikeByID(String bikeIdString) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDAO = daoFactory.getBikeDAO();

		if (!UserParameterValidator.idValidate(bikeIdString)) {
			logger.log(Level.DEBUG, "bikeIdString - " + bikeIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long bikeId = Long.parseLong(bikeIdString);

		try {
			return bikeDAO.takeBikeById(bikeId);
		} catch (DAOException e) {
			throw new ServiceException("Exception occured while taking bike by id", e);
		}
	}

	@Override
	public List<BikeProduct> addBikeProduct(Map<String, String> requestParameters) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDAO = daoFactory.getBikeDAO();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO();

		CloneRentBikeObject<BikeProduct> cloneRentBikeObject = new CloneRentBikeObject<>();

		Parking parking = null;

		String countString = requestParameters.get(RequestParameter.BIKE_COUNT.parameter());
		if (!BikeParameterValidator.countValidate(countString)) {
			logger.log(Level.DEBUG, "count - " + countString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		int bikeCount = Integer.parseInt(countString);

		String valueString = requestParameters.get(RequestParameter.BIKE_VALUE.parameter());
		valueString = valueString.replace(COMMA, DOT);
		if (!BikeParameterValidator.amountValidate(valueString)) {
			logger.log(Level.DEBUG, "value - " + valueString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		BigDecimal bikeValue = new BigDecimal(valueString);

		String rentPriceString = requestParameters.get(RequestParameter.BIKE_RENT_PRICE.parameter());
		rentPriceString = rentPriceString.replace(COMMA, DOT);
		if (!BikeParameterValidator.amountValidate(rentPriceString)) {
			logger.log(Level.DEBUG, "Rent ptice has not been verified");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		BigDecimal rentPrice = new BigDecimal(rentPriceString);

		String bikeIdString = requestParameters.get(RequestParameter.BIKE_ID.parameter());
		if (!UserParameterValidator.idValidate(bikeIdString)) {
			logger.log(Level.DEBUG, "bikeIdString - " + bikeIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long bikeId = Long.parseLong(bikeIdString);

		String pakingIdString = requestParameters.get(RequestParameter.PARKING_ID.parameter());
		if (!UserParameterValidator.idValidate(pakingIdString)) {
			logger.log(Level.DEBUG, "pakingIdString - " + pakingIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long parkingId = Long.parseLong(pakingIdString);

		List<BikeProduct> bikeProductList = new ArrayList<>();

		try {
			Bike bike = bikeDAO.takeBikeById(bikeId);
			parking = parkingDAO.findParkingById(parkingId);

			BikeProduct bikeProduct = new BikeProduct();

			bikeProduct.setBike(bike);
			bikeProduct.setParking(parking);
			bikeProduct.setValue(bikeValue);
			bikeProduct.setRentPrice(rentPrice);
			bikeProduct.setRegistrationDate(DateFormatter.takeCurrentDateTimeToDB());

			for (int i = 0; i < bikeCount; i++) {
				bikeProductList.add(cloneRentBikeObject.clone(bikeProduct));
			}

			return bikeDAO.addBikeProduct(bikeProductList);

		} catch (NumberFormatException | DAOException | ServerException e) {
			throw new ServiceException("Get bike by id error", e);
		}
	}

	@Override
	public List<BikeProduct> findBikeProduct(Map<String, String> requestParameters, PageInfo pageInfo)
			throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDAO = daoFactory.getBikeDAO();

		String brandIdString = requestParameters.get(RequestParameter.BRAND_ID.parameter());
		if (!ZERO_PARAM.equals(brandIdString) && !UserParameterValidator.idValidate(brandIdString)) {
			logger.log(Level.DEBUG, "brandId - " + brandIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long brandId = Long.parseLong(brandIdString);

		String bikeTypeIdString = requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter());
		if (!ZERO_PARAM.equals(bikeTypeIdString) && !UserParameterValidator.idValidate(bikeTypeIdString)) {
			logger.log(Level.DEBUG, "bikeTypeId - " + bikeTypeIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long bikeTypeId = Long.parseLong(bikeTypeIdString);

		String model = requestParameters.get(RequestParameter.MODEL.parameter());
		if (!model.isEmpty() && !BikeParameterValidator.modelValidate(model)) {
			logger.log(Level.DEBUG, "model - " + model + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		String pakingIdString = requestParameters.get(RequestParameter.PARKING_ID.parameter());
		if (!ZERO_PARAM.equals(pakingIdString) && !UserParameterValidator.idValidate(pakingIdString)) {
			logger.log(Level.DEBUG, "pakingIdString - " + pakingIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long parkingId = Long.parseLong(pakingIdString);

		try {
			return bikeDAO.findBikeProduct(parkingId, brandId, bikeTypeId, model, BikeProductState.AVAILABLE, pageInfo);
		} catch (DAOException e) {
			throw new ServiceException("Find bike product exception", e);
		}
	}

	@Override
	public BikeProduct takeBikeProductById(String bikeProductIdString) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDAO = daoFactory.getBikeDAO();

		try {
			if (!UserParameterValidator.idValidate(bikeProductIdString)) {
				logger.log(Level.DEBUG, "bikeProductId - " + bikeProductIdString + " is wrong");
				throw new DAOException(ExceptionMessage.VALIDATION_ERROR.toString());
			}
			long bikeProductId = Long.parseLong(bikeProductIdString);

			return bikeDAO.takeBikeProductById(bikeProductId);
		} catch (DAOException e) {
			throw new ServiceException("Take bike product by id exception", e);
		}
	}

	@Override
	public Bike updateBike(Map<String, String> requestParameters) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BikeDAO bikeDao = daoFactory.getBikeDAO();

		Bike bike = new Bike();
		bike.setId(Long.parseLong(requestParameters.get(RequestParameter.BIKE_ID.parameter())));

		// validation
		Brand brand = new Brand();
		brand.setId(Long.parseLong(requestParameters.get(RequestParameter.BRAND_ID.parameter())));
		bike.setBrand(brand);

		String model = requestParameters.get(RequestParameter.MODEL.parameter());
		if (!BikeParameterValidator.modelValidate(model)) {
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setModel(requestParameters.get(RequestParameter.MODEL.parameter()));

		String wheelSizeString = (requestParameters.get(RequestParameter.WHEEL_SIZE.parameter()));
		if (!BikeParameterValidator.wheelSizeValidate(wheelSizeString)) {
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setWheelSize(Integer.parseInt(wheelSizeString));

		String speedCountString = requestParameters.get(RequestParameter.SPEED_COUNT.parameter());
		if (!BikeParameterValidator.speedCountValidate(speedCountString)) {
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		bike.setSpeedCount(Integer.parseInt(speedCountString));

		bike.setPicturePath(requestParameters.get(RequestParameter.PICTURE.parameter()));

		BikeType bikeType = new BikeType();
		bikeType.setId(Long.parseLong(requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter())));
		bike.setBikeType(bikeType);

		try {
			return bikeDao.updateBike(bike);
		} catch (DAOException e) {
			throw new ServiceException("Add bike exception", e);
		}
	}

}
