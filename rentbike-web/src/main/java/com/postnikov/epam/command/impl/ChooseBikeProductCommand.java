package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class ChooseBikeProductCommand implements Command {

	private static Logger logger = LogManager.getLogger(ChooseBikeProductCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();
		router.setPagePath(PageConstant.ORDER_PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();

		try {
			String bikeProductIdString = request.getParameter(RequestParameter.BIKE_PRODUCT_ID.parameter());
			BikeProduct bikeProduct = bikeService.takeBikeProductById(bikeProductIdString);

			HttpSession session = request.getSession(false);
			session.setAttribute(SessionParameter.BIKE_PRODUCT.parameter(), bikeProduct);
	
		} catch (NumberFormatException | ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Get bike product by id error, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
			}
		}

		return router;

	}

}
