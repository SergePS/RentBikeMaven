package com.postnikov.epam.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class AddBrandCommand implements Command {

	private static Logger logger = LogManager.getLogger(AddBrandCommand.class);

	private final static String IS_ADD_FORM_PARAM = "isAddForm";

	@Override
	public Router execute(HttpServletRequest request) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();

		Router router = new Router();
		router.setPagePath(PageConstant.ADD_BIKE_PAGE);

		Boolean isAddForm = Boolean.valueOf(request.getParameter(IS_ADD_FORM_PARAM));

		if (isAddForm) {
			router.setPagePath(PageConstant.BRAND_PAGE);
			return router;
		}

		String brandName = request.getParameter(RequestParameter.BRAND.parameter());

		try {
			bikeService.addBrand(brandName);

			List<Brand> brandList = bikeService.takeAllBrand();
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);

			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);

		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Add brand exception, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			}else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				router.setPagePath(PageConstant.BRAND_PAGE);
				RequestParameterHandler.addParamToRequest(request);
			}
		}

		return router;
	}

}
