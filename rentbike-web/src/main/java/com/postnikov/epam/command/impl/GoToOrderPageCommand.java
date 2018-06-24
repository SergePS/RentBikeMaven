package com.postnikov.epam.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.AddTimeParameterToRequest;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class GoToOrderPageCommand implements Command{
	
	private static Logger logger = LogManager.getLogger(GoToOrderPageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(SessionParameter.USER.parameter());
		
		try {
			BikeOrder bikeOrder = userService.findOpenOrder(user);
			
			if (bikeOrder != null) {
				request.setAttribute(RequestParameter.BIKE_ORDER.parameter(), bikeOrder);
				AddTimeParameterToRequest.addParam(request, bikeOrder.getStartTime());
				
				ParkingService parkingService = serviceFactory.getParkingService();
				List<Parking> parkingList = parkingService.takeAllParking();
				request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
				
				router.setPagePath(PageConstant.USER_PAGE);
				return router;
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "find open order error, " + ConvertPrintStackTraceToString.convert(e));
		}
		
		router.setPagePath(PageConstant.ORDER_PAGE);
		
		return router;
	}

}
