package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.RequestParameter;

public class ChangeLocalizationCommand implements Command{

	@Override
	public Router execute(HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		
		Router router = new Router();
		
		boolean loginMenu;
		if(request.getParameter(RequestParameter.LOGIN_MENU.parameter()).isEmpty()) {
			loginMenu = true;
		}else {
			loginMenu = Boolean.valueOf(request.getParameter(RequestParameter.LOGIN_MENU.parameter()));
		}	
		request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), loginMenu);
		
		session.setAttribute(SessionParameter.LOCAL.parameter(), request.getParameter(SessionParameter.LANGUAGE.parameter()));
		
		router.setPagePath(PageConstant.LOGIN_PAGE);
		return router;
	}

}
