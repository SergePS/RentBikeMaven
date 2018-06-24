package com.postnikov.epam.command;

import javax.servlet.http.HttpServletRequest;

import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;

public class EmptyCommand implements Command{

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setPagePath(PageConstant.ERROR_PAGE);
		
		return router;
	}

}
