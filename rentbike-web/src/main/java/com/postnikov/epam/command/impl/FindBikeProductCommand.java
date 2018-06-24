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
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ParkingService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class FindBikeProductCommand implements Command {

	private static Logger logger = LogManager.getLogger(FindBikeProductCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();
		router.setPagePath(PageConstant.BIKE_PRODUCT_CATALOG_PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();
		ParkingService parkingService = serviceFactory.getParkingService();

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		PageInfo pageInfo = PageInfoHandler.pageInfoInit(request);

		try {
			List<Parking> parkingList = parkingService.takeAllParking();
			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			List<Brand> brandList = bikeService.takeAllBrand();
			request.setAttribute(RequestParameter.PARKING_LIST.parameter(), parkingList);
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);
			RequestParameterHandler.addParamToRequest(request);
			
			List<BikeProduct> bikeProductList = bikeService.findBikeProduct(requestParameters, pageInfo);
			request.setAttribute(RequestParameter.BIKE_PRODUCT_LIST.parameter(), bikeProductList);
					
			PageInfoHandler.handleAndAddToSession(pageInfo, request, bikeProductList);

		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Get data error, " + e.getMessage());
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				
				//remove the parameter to make paging menu unavailable.
				HttpSession session = request.getSession(true);
				session.removeAttribute(SessionParameter.PAGE_INFO.parameter());
			}
		}

		return router;
	}

}
