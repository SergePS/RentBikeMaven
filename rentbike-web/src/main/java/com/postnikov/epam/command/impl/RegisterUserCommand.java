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
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.RouteType;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class RegisterUserCommand implements Command {

	private static Logger logger = LogManager.getLogger(RegisterUserCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		char[] password = request.getParameter(RequestParameter.PASSWORD.parameter()).toCharArray();

		try {
			userService.register(requestParameters, password);
			
			for (int i = 0; i < password.length; i++) {
				password[i] = 0;
			}

			router.setRoute(RouteType.REDIRECT);
			router.setPagePath(PageConstant.REDIRECT_TO_LOGIN_PAGE);
			
			HttpSession session = request.getSession(true);
			session.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ADDED.message());
			
		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR,
						"An error occured while the user was creating, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(), CommandExceptionHandler.takeLogicExceptionMessage(e));
				RequestParameterHandler.addParamToRequest(request);
				request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
				router.setRoute(RouteType.FORWARD);
				router.setPagePath(PageConstant.LOGIN_PAGE);
			}

		}

		return router;
	}

}
