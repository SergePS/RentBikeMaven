package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class TakeAllUserCommand implements Command {
	private static Logger logger = LogManager.getLogger(TakeAllUserCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			request.setAttribute(RequestParameter.ORDER_LIST.parameter(), userService.takeAllUsers());
			router.setPagePath(PageConstant.ADMIN_PAGE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "take all userd error, " + e);
			router.setPagePath(PageConstant.ERROR_PAGE);
		}
		
		return router;
	}

}
