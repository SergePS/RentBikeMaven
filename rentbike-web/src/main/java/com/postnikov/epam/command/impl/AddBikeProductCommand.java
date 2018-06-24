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
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.RouteType;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class AddBikeProductCommand implements Command {

	private static Logger logger = LogManager.getLogger(AddBikeProductCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();

		Router router = new Router();

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		try {
			ParkingService parkingService = serviceFactory.getParkingService();
			List<Parking> parkingList = parkingService.takeAllParking();
			request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);

			List<BikeProduct> bikeProductList = bikeService.addBikeProduct(requestParameters);

			HttpSession session = request.getSession(false);
			session.setAttribute(RequestParameter.BIKE_PRODUCT_LIST.parameter(), bikeProductList);
			router.setRoute(RouteType.REDIRECT);
			router.setPagePath(PageConstant.REDIRECT_TO_BIKE_PURCHASE_PAGE);

		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Add bike product error" + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				RequestParameterHandler.addParamToRequest(request);

				router.setRoute(RouteType.FORWARD);
				router.setPagePath(PageConstant.BIKE_PURCHASE_PAGE);
			}
		}

		return router;
	}
}
