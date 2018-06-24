package com.postnikov.epam.command.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.PageMessage;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.RouteType;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class AddParkingCommand implements Command {

	private static Logger logger = LogManager.getLogger(AddParkingCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ParkingService parkingService = serviceFactory.getParkingService();

		Router router = new Router();
		router.setRoute(RouteType.REDIRECT);

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		try {
			parkingService.addParking(requestParameters);

			router.setPagePath(PageConstant.REDIRECT_TO_PARKING_PAGE);
			HttpSession session = request.getSession(true);
			session.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.PARKING_ADDED.message());
		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR,
						"An error occurred while adding the parking, " + ConvertPrintStackTraceToString.convert(e));
				router.setRoute(RouteType.FORWARD);
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				router.setRoute(RouteType.FORWARD);
				router.setPagePath(PageConstant.ADD_PARKING_PAGE);
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
			}
		}

		return router;
	}

}
