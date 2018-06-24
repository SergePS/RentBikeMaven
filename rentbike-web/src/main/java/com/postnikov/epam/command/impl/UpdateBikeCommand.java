package com.postnikov.epam.command.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.CommandExceptionHandler;
import com.postnikov.epam.command.PageMessage;
import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.ApplicationProperty;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.BikeService;
import com.postnikov.epam.rentbike.service.ServiceFactory;

public class UpdateBikeCommand implements Command {

	private static Logger logger = LogManager.getLogger(UpdateBikeCommand.class);

	private static final String UPLOAD_DIR_PROP_KEY = "upload_dir";
	private static final String UPLOAD_DIR = ApplicationProperty.takeProperty().getProperty(UPLOAD_DIR_PROP_KEY);

	private final static String DOT_SEPARATOR = "\\.";

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();
		router.setPagePath(PageConstant.ADD_BIKE_PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BikeService bikeService = serviceFactory.getBikeService();

		Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

		// create picture's file name
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + File.separator + UPLOAD_DIR;

		Part filePart = null;
		String filePath = "";
		String filePictureName = "";

		try {
			for (Part part : request.getParts()) {
				String fileName = extractFileName(part);
				fileName = new File(fileName).getName();

				if (!fileName.isEmpty()) {
					String fileExtension = fileName.split(DOT_SEPARATOR)[1];
					fileExtension = "." + fileExtension;
					filePictureName = File.createTempFile("bike", fileExtension, null).getName();
					requestParameters.put(RequestParameter.PICTURE.parameter(), filePictureName);
					filePath = savePath + File.separator + filePictureName;
					filePart = part;
				}
			}
		} catch (IOException | ServletException e) {
			logger.log(Level.ERROR, "Upload picture error, " + ConvertPrintStackTraceToString.convert(e));
		}

		// update bike
		try {
			List<BikeType> bikeTypeList = bikeService.takeAllBikeType();
			request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypeList);

			List<Brand> brandList = bikeService.takeAllBrand();
			request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brandList);

			Bike bike = bikeService.updateBike(requestParameters);

			request.setAttribute(RequestParameter.BIKE.parameter(), bike);

			if (filePart != null) {
				filePart.write(filePath);
			}

			request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_CHANGED.message());

		} catch (ServiceException e) {
			if (CommandExceptionHandler.takeLogicExceptionMessage(e).isEmpty()) {
				logger.log(Level.ERROR, "Update bike exception, " + ConvertPrintStackTraceToString.convert(e));
				router.setPagePath(PageConstant.ERROR_PAGE);
			} else {
				request.setAttribute(RequestParameter.ERROR.parameter(),
						CommandExceptionHandler.takeLogicExceptionMessage(e));
				RequestParameterHandler.addParamToRequest(request);
			}

		} catch (IOException e) {
			logger.log(Level.ERROR, "Write picture error, " + ConvertPrintStackTraceToString.convert(e));
		}

		return router;
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";

	}

}
