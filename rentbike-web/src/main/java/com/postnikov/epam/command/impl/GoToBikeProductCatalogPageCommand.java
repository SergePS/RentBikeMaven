package com.postnikov.epam.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class GoToBikeProductCatalogPageCommand implements Command {

	private static Logger logger = LogManager.getLogger(GoToBikeProductCatalogPageCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ParkingService parkingService = serviceFactory.getParkingService();
		BikeService bikeService = serviceFactory.getBikeService();
		
		HttpSession session = request.getSession(true);
		session.removeAttribute(SessionParameter.PAGE_INFO.parameter());
		session.setAttribute(SessionParameter.BIKE_PRODUCT_CATALOG_WITH_CHOICE.parameter(), request.getParameter(RequestParameter.BIKE_PRODUCT_CATALOG_WITH_CHOICE.parameter()));

		try {
			List<Parking> parkingList = parkingService.takeAllParking();
			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			List<Brand> brandList = bikeService.takeAllBrand();

			request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);
			
			router.setPagePath(PageConstant.BIKE_PRODUCT_CATALOG_PAGE);

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Get data error, " + e.getMessage());
			router.setPagePath(PageConstant.ERROR_PAGE);
		}

		return router;
	}

}
