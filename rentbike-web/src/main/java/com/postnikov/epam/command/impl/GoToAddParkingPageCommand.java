package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class GoToAddParkingPageCommand implements Command {

	private static Logger logger = LogManager.getLogger(GoToAddParkingPageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ParkingService parkingService = serviceFactory.getParkingService();

		String parkingId = request.getParameter(RequestParameter.PARKING_ID.parameter());
		if (parkingId != null) {
			try {
				Parking parking = parkingService.findParkingById(parkingId);

				request.setAttribute(RequestParameter.PARKING.parameter(), parking);
			} catch (ServiceException e) {
				if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
					logger.log(Level.ERROR, "Take pakring by id error" + ConvertPrintStackTraceToString.convert(e));
					router.setPagePath(PageConstant.ERROR_PAGE);
				} else {
					router.setPagePath(PageConstant.ERROR_PAGE);
				}
			}
		}

		router.setPagePath(PageConstant.ADD_PARKING_PAGE);
		return router;
	}

}
