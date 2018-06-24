package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;

public class GoToOrderReportPage implements Command{

	@Override
	public Router execute(HttpServletRequest request) {
		
		Router router = new Router();
		router.setPagePath(PageConstant.ORDER_REPORT_PAGE);
		HttpSession session = request.getSession(true);
		session.removeAttribute(SessionParameter.PAGE_INFO.parameter());

		return router;
	}

}
