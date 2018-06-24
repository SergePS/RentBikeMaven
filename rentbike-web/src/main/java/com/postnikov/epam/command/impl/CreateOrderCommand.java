package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.AddTimeParameterToRequest;
import com.postnikov.epam.controller.RouteType;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class CreateOrderCommand implements Command {

	private static Logger logger = LogManager.getLogger(CreateOrderCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setRoute(RouteType.REDIRECT);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		HttpSession session = request.getSession();
		BikeProduct bikeProduct = (BikeProduct) session.getAttribute(SessionParameter.BIKE_PRODUCT.parameter());
		session.removeAttribute(SessionParameter.BIKE_PRODUCT.parameter());

		User user = (User) session.getAttribute(SessionParameter.USER.parameter());

		try {
			BikeOrder bikeOrder = userService.findOpenOrder(user);
			if (bikeOrder != null) {
				request.setAttribute(RequestParameter.BIKE_ORDER.parameter(), bikeOrder);
				router.setPagePath(PageConstant.USER_PAGE);
				return router;
			}

			bikeOrder = userService.createOrder(user, bikeProduct);
			request.setAttribute(RequestParameter.BIKE_ORDER.parameter(), bikeOrder);
			AddTimeParameterToRequest.addParam(request, bikeOrder.getStartTime());
			router.setPagePath(PageConstant.REDIRECT_TO_HOME_PAGE);

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Create order error, " + ConvertPrintStackTraceToString.convert(e));
			router.setPagePath(PageConstant.ERROR_PAGE);
		}
			
		return router;
	}

}
