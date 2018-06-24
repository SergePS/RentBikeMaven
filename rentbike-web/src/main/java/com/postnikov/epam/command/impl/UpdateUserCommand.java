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
import com.postnikov.epam.command.PageMessage;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.AddTimeParameterToRequest;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class UpdateUserCommand implements Command {

	private static Logger logger = LogManager.getLogger(UpdateUserCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		HttpSession session = request.getSession();

		User currentUser = (User) session.getAttribute(SessionParameter.USER.parameter());
		router.setPagePath(currentUser.getRole().getHomePage());

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		try {
			BikeOrder bikeOrder = userService.findOpenOrder(currentUser);
			request.setAttribute(RequestParameter.BIKE_ORDER.parameter(), bikeOrder);
			if (bikeOrder != null) {
				AddTimeParameterToRequest.addParam(request, bikeOrder.getStartTime());

				ParkingService parkingService = serviceFactory.getParkingService();
				List<Parking> parkingList = parkingService.takeAllParking();
				request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
			}
			
			User user = userService.userUpdate(requestParameters, currentUser);

			request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.PROFILE_CHANGED.message());
			session.setAttribute(SessionParameter.USER.parameter(), user);
		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Update user error, " + e);
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				RequestParameterHandler.addParamToRequest(request);
				request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
			}

		}

		return router;
	}

}
