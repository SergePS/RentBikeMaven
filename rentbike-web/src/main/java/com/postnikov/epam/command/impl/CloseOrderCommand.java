package com.postnikov.epam.command.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.RouteType;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class CloseOrderCommand implements Command{
	
	private static Logger logger = LogManager.getLogger(CloseOrderCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setRoute(RouteType.REDIRECT);
		router.setPagePath(PageConstant.REDIRECT_TO_HOME_PAGE);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		HttpSession session = request.getSession(false);
		
		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);
		
		try {	
			BigDecimal payment = userService.closeOrder(requestParameters);
			session.setAttribute(RequestParameter.PAYMENT.parameter(), payment.toString());
		} catch (ServiceException e) {
			if(CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Exception occurred while closing order, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			}else {
				if(ExceptionMessage.ORDER_NOT_EXIST.toString().equals(CommandExceptionHandler.takeLogicExceptionMessage(e))) {
					session.setAttribute(RequestParameter.ERROR.parameter(),  ExceptionMessage.ORDER_NOT_EXIST.message());
				}else {
					session.setAttribute(RequestParameter.ERROR.parameter(),
							CommandExceptionHandler.takeLogicExceptionMessage(e));
				}
			}

		}

		return router;
	}

}