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
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class GoToBikeCatalogPageCommand implements Command {

	private static Logger logger = LogManager.getLogger(GoToBikeCatalogPageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();
		
		HttpSession session = request.getSession(false);
		session.removeAttribute(SessionParameter.PAGE_INFO.parameter());
		session.setAttribute(SessionParameter.BIKE_CATALOG_WITH_CHOICE.parameter(), request.getParameter(RequestParameter.BIKE_CATALOG_WITH_CHOICE.parameter()));

		try {
			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			List<Brand> brandList = bikeService.takeAllBrand();
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);
			
			router.setPagePath(PageConstant.BIKE_CATALOG_PAGE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Get bike tipe or brand exception, " + e.getMessage());
			router.setPagePath(PageConstant.ERROR_PAGE);
		}

		return router;
	}

}
