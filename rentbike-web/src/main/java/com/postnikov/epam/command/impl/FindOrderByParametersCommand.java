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
import com.postnikov.epam.command.PageInfoHandler;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.ServiceFactory;
import com.postnikov.epam.rentbike.service.UserService;

public class FindOrderByParametersCommand implements Command{
	
	private static Logger logger = LogManager.getLogger(FindOrderByParametersCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setPagePath(PageConstant.ORDER_REPORT_PAGE);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);
		
		PageInfo pageInfo = PageInfoHandler.pageInfoInit(request);
		RequestParameterHandler.addParamToRequest(request);
		
		try {
			List<BikeOrder> bikeOrderList = userService.findOrder(requestParameters, pageInfo);
			request.setAttribute(RequestParameter.BIKE_ORDER_LIST.parameter(), bikeOrderList);
			PageInfoHandler.handleAndAddToSession(pageInfo, request, bikeOrderList);
		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Exception occurred while find orders, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			}else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				pageInfo = PageInfoHandler.pageInfoInit(request);
				
				//remove the parameter to make paging menu unavailable.
				HttpSession session = request.getSession(true);
				session.removeAttribute(SessionParameter.PAGE_INFO.parameter());
			}
		}
		
		return router;
	}

}