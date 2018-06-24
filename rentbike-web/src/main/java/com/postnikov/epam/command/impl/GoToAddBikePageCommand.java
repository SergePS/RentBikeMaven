package com.postnikov.epam.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class GoToAddBikePageCommand implements Command {

	private static Logger logger = LogManager.getLogger(GoToAddBikePageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();

		try {
			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);

			List<Brand> brandList = bikeService.takeAllBrand();
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);

			String bikeIdString = request.getParameter(RequestParameter.BIKE_ID.parameter());
			if (bikeIdString != null) {
				String bikeId = request.getParameter(RequestParameter.BIKE_ID.parameter());
				Bike bike = bikeService.takeBikeByID(bikeId);
				if(bike!=null) {
					request.setAttribute(RequestParameter.BIKE.parameter(), bike);
				}
			}
			router.setPagePath(PageConstant.ADD_BIKE_PAGE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Add bike exception, " + e.getMessage());
			router.setPagePath(PageConstant.ERROR_PAGE);
		}
		
		return router;
	}

}
