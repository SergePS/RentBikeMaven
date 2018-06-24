package com.postnikov.epam.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.postnikov.epam.command.util.RequestParameterHandler;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.AbstractEntity;

/**
 * @author Sergey Postnikov
 *
 */
public class PageInfoHandler {
	
	private final static int NEXT_PAGE = 1;
	private final static int NO_ACTION = 0;
	private final static int PREV_PAGE = -1;

	/**
	 * Creates and initializes or reinitializes PageInfo object.
	 * 
	 * @param request
	 * @return new or changed pageInfo object
	 */
	public static PageInfo pageInfoInit(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		PageInfo pageInfo = (PageInfo) session.getAttribute(SessionParameter.PAGE_INFO.parameter());

		if (pageInfo == null || !pageInfo.isChangePageFlag()) {
			pageInfo = new PageInfo();
		} else {
			pageInfo.setChangePageFlag(false);
		}
		pageInfo.setPreviousUrlWithParam(RequestParameterHandler.paramToString(request));

		return pageInfo;
	}

	/**
	 * Modifies pageInfo object: set lastPage flag, if received itemList not empty
	 * adds the ID of the last item in the itemList. Finally adds pageInfo object to
	 * session.
	 * 
	 * @param pageInfo
	 * @param request
	 * @param itemList
	 */
	public static void handleAndAddToSession(PageInfo pageInfo, HttpServletRequest request,
			List<? extends AbstractEntity> itemList) {

		HttpSession session = request.getSession(false);
		
		

		if (itemList.isEmpty()) {

			if (!pageInfo.isLastPage()) {
				pageInfo.addPagePoint(pageInfo.getLastPagePoint());
			}
			
			if(pageInfo.getPageAction() == NEXT_PAGE) {
				if(!pageInfo.isLastPage()) {
					pageInfo.setCurrentPage(pageInfo.getCurrentPage() + 1);
				}
			}
			
			pageInfo.setLastPage(true);

			// If current page number = 1 and incoming itemList is empty, then result of
			// searching no contains items. So set emptyList flag - true.
			// If emptyList flag true, then pagination menu on JSP is not available.
			if (pageInfo.getCurrentPage() == 1) {
				pageInfo.setEmptyList(true);
			}

		} else {
			long lastBikeId = itemList.get(itemList.size() - 1).getId();

			if(pageInfo.getPageAction() == NO_ACTION) {
				pageInfo.addPagePoint(lastBikeId);
				
				if (pageInfo.getDefaultElementOnPage() > itemList.size()) {
					pageInfo.setLastPage(true);
				}
			}
			
			if(pageInfo.getPageAction() == NEXT_PAGE) {
				if(!pageInfo.isLastPage()) {
					pageInfo.addPagePoint(lastBikeId);
				}
				
				if (pageInfo.getDefaultElementOnPage() > itemList.size()) {
					pageInfo.setLastPage(true);
				}
				
				pageInfo.setCurrentPage(pageInfo.getCurrentPage() + 1);
			}
			
			if(pageInfo.getPageAction() == PREV_PAGE) {
				if(pageInfo.getCurrentPage() > 1) {
					pageInfo.setCurrentPage(pageInfo.getCurrentPage() - 1);
				}
				pageInfo.addPagePoint(lastBikeId);
				
				pageInfo.setLastPage(false);
			}
			
			pageInfo.setEmptyList(false);
		}
		
		session.setAttribute(SessionParameter.PAGE_INFO.parameter(), pageInfo);

	}

}
