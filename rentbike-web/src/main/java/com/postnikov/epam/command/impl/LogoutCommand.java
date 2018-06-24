package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;

public class LogoutCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setPagePath(PageConstant.LOGIN_PAGE);
		
		request.getSession().invalidate();

		return router;

	}

}
