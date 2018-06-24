package com.postnikov.epam.command.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.AddTimeParameterToRequest;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.entity.UserRole;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class LoginCommand implements Command {

	private static Logger logger = LogManager.getLogger(LoginCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		HttpSession session = request.getSession();

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		char[] password = request.getParameter(RequestParameter.PASSWORD.parameter()).toCharArray();
		
		logger.log(Level.DEBUG, "login user");

		try {
			User user = userService.login(requestParameters, password);

			for (int i = 0; i < password.length; i++) {
				password[i] = 0;
			}

			if (user == null) {
				request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSW.message());
				RequestParameterHandler.addParamToRequest(request);
				router.setPagePath(PageConstant.LOGIN_PAGE);
				return router;
			}

			if (UserRole.USER.equals(user.getRole())) {

				BikeOrder bikeOrder = userService.findOpenOrder(user);
				if (bikeOrder != null) {
					request.setAttribute(RequestParameter.BIKE_ORDER.parameter(), bikeOrder);
					AddTimeParameterToRequest.addParam(request, bikeOrder.getStartTime());

					ParkingService parkingService = serviceFactory.getParkingService();
					List<Parking> parkingList = parkingService.takeAllParking();
					request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
				}
			}

			router.setPagePath(user.getRole().getHomePage());
			session.setAttribute(SessionParameter.USER.parameter(), user);

		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR,
						"An exception occurred while get user data, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				router.setPagePath(PageConstant.LOGIN_PAGE);
			}
		}

		return router;
	}

}
