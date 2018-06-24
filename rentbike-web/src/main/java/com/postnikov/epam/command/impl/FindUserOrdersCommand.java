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
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class FindUserOrdersCommand implements Command {

	private static Logger logger = LogManager.getLogger(FindUserOrdersCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(SessionParameter.USER.parameter());

		try {
			List<BikeOrder> bikeOrderList = userService.findAllOrderByUser(user);
			if(!bikeOrderList.isEmpty()) {
				request.setAttribute(RequestParameter.BIKE_ORDER_LIST.parameter(), bikeOrderList);
			}
			router.setPagePath(user.getRole().getHomePage());
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Take all user orders error, " + ConvertPrintStackTraceToString.convert(e));
			router.setPagePath(PageConstant.ERROR_PAGE);
		}

		return router;
	}

}
