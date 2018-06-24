package com.postnikov.epam.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.SessionParameter;
import com.postnikov.epam.controller.Router;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.RequestParameter;

public class PagingCommand implements Command {
	private static Logger logger = LogManager.getLogger(PagingCommand.class);

	private final static int NEXT_PAGE = 1;
	private final static int PREV_PAGE = -1;

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router();
		// router.setRoute(RouteType.REDIRECT);

		HttpSession session = request.getSession(false);
		PageInfo pageInfo = (PageInfo) session.getAttribute(SessionParameter.PAGE_INFO.parameter());
		if (pageInfo == null) {
			logger.log(Level.ERROR, "pageInfo object not found");
			router.setPagePath(PageConstant.ERROR_PAGE);
		} else {
			pageInfo.setChangePageFlag(true);

			String pageAction = request.getParameter(RequestParameter.PAGE_ACTION.parameter());
			router.setPagePath(pageInfo.getPreviousUrlWithParam());

			if (RequestParameter.PREVIOUS_PAGE.parameter().equals(pageAction)) {
				if (pageInfo.getCurrentPage() > 1) {
					pageInfo.removeLastPagePoint();		//removing 2 line because user change direction of paging
					pageInfo.removeLastPagePoint();
					pageInfo.setPageAction(PREV_PAGE);
				} else {		// protection against F5
					pageInfo.removeLastPagePoint();
					pageInfo.setPageAction(PREV_PAGE);
				}
			}

			if (RequestParameter.NEXT_PAGE.parameter().equals(pageAction)) {
				if (!pageInfo.isLastPage()) {
					pageInfo.setPageAction(NEXT_PAGE);
				}
			}
		}

		return router;
	}

}
