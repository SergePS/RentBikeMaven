package com.postnikov.epam.rentbike.domain;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Sergey Postnikov Pagination parameters
 */
public class PageInfo {

	private final static long ITEM_LAST_ID_FOR_FIRST_PAGE = 0;
	private final static String DEFAULT_ELEMENT_ON_PAGE_KEY = "default_element_on_page";
	private final static int DEFAULT_ELEMENTS_COUNT_ON_PAGE = Integer
			.parseInt(ApplicationProperty.takeProperty().getProperty(DEFAULT_ELEMENT_ON_PAGE_KEY));

	/** Default element count on the page. */
	private int defaultElementOnPage;

	/**
	 * storage for id of the last element on the page. Used to set the first id of
	 * the range of the SQL SELECT query
	 */
	private Deque<Long> pagePoint;

	/** Last page flag. */
	private boolean lastPage;

	/** If emptyList flag true, then pagination menu on JSP is not available. */
	private boolean emptyList;

	/** request to previous page with searching parameters */
	private String previousUrlWithParam;

	/**
	 * if changePageFlag if true, pageInfo is not initialized when redirect by a
	 * previous request
	 */
	private boolean changePageFlag;

	/** -1 - previously page, +1 - next page */
	private int pageAction;

	private int currentPage;

	public PageInfo() {
		this.pagePoint = new ArrayDeque<>();
		this.defaultElementOnPage = DEFAULT_ELEMENTS_COUNT_ON_PAGE;
		this.pagePoint.addLast(ITEM_LAST_ID_FOR_FIRST_PAGE);
		this.changePageFlag = false;
		this.lastPage = false;
		this.emptyList = true;
		this.currentPage = 1;
	}

	public int getDefaultElementOnPage() {
		return defaultElementOnPage;
	}

	public void setDefaultElementOnPage(int defaultElementOnPage) {
		this.defaultElementOnPage = defaultElementOnPage;
	}

	public String getPreviousUrlWithParam() {
		return previousUrlWithParam;
	}

	public void setPreviousUrlWithParam(String previousUrlWithParam) {
		this.previousUrlWithParam = previousUrlWithParam;
	}

	public boolean isChangePageFlag() {
		return changePageFlag;
	}

	public void setChangePageFlag(boolean changePageFlag) {
		this.changePageFlag = changePageFlag;
	}

	public void addPagePoint(Long itemId) {
		this.pagePoint.addLast(itemId);
	}

	public long removeLastPagePoint() {
		return this.pagePoint.removeLast();
	}

	public long getLastPagePoint() {
		return this.pagePoint.getLast();
	}

	public Deque<Long> getPagePoint() {
		return pagePoint;
	}

	public void setPagePoint(Deque<Long> pagePoint) {
		this.pagePoint = pagePoint;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isEmptyList() {
		return emptyList;
	}

	public void setEmptyList(boolean emptyList) {
		this.emptyList = emptyList;
	}

	public int getPageAction() {
		return pageAction;
	}

	public void setPageAction(int pageAction) {
		this.pageAction = pageAction;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changePageFlag ? 1231 : 1237);
		result = prime * result + currentPage;
		result = prime * result + defaultElementOnPage;
		result = prime * result + (emptyList ? 1231 : 1237);
		result = prime * result + (lastPage ? 1231 : 1237);
		result = prime * result + pageAction;
		result = prime * result + ((pagePoint == null) ? 0 : pagePoint.hashCode());
		result = prime * result + ((previousUrlWithParam == null) ? 0 : previousUrlWithParam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageInfo other = (PageInfo) obj;
		if (changePageFlag != other.changePageFlag)
			return false;
		if (currentPage != other.currentPage)
			return false;
		if (defaultElementOnPage != other.defaultElementOnPage)
			return false;
		if (emptyList != other.emptyList)
			return false;
		if (lastPage != other.lastPage)
			return false;
		if (pageAction != other.pageAction)
			return false;
		if (pagePoint == null) {
			if (other.pagePoint != null)
				return false;
		} else if (!pagePoint.equals(other.pagePoint))
			return false;
		if (previousUrlWithParam == null) {
			if (other.previousUrlWithParam != null)
				return false;
		} else if (!previousUrlWithParam.equals(other.previousUrlWithParam))
			return false;
		return true;
	}

}
