package com.postnikov.epam.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class GoToBikePurchasePageCommand implements Command {

	private static Logger logger = LogManager.getLogger(GoToBikePurchasePageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ParkingService parkingService = serviceFactory.getParkingService();

		try {
			List<Parking> parkingList = parkingService.takeAllParking();
			request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
			router.setPagePath(PageConstant.BIKE_PURCHASE_PAGE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Get all parking error, " + e.getMessage());
			router.setPagePath(PageConstant.ERROR_PAGE);
		}

		return router;
	}

}
