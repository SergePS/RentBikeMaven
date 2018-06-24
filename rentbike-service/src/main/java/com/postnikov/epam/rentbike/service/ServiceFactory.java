package com.postnikov.epam.rentbike.service;

import com.postnikov.epam.rentbike.service.impl.BikeServiceImpl;
import com.postnikov.epam.rentbike.service.impl.ParkingServiceImpl;
import com.postnikov.epam.rentbike.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private static final UserService userService = new UserServiceImpl();
	private static final BikeService bikeService = new BikeServiceImpl();
	private static final ParkingService parkingService = new ParkingServiceImpl();

	private ServiceFactory() {
	}

	public UserService getUserService() {
		return userService;
	}

	public BikeService getBikeService() {
		return bikeService;
	}
	
	public ParkingService getParkingService() {
		return parkingService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

}
