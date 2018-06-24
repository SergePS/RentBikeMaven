package com.postnikov.epam.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/jsp/*"}, initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class PageRedirectSecurityFilter implements Filter{
	
	private String indexPath;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	indexPath = filterConfig.getInitParameter("INDEX_PATH");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			HttpSession session = httpRequest.getSession(false);
			session.invalidate();
			session = httpRequest.getSession(true);
			
			httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
			chain.doFilter(request, response);
			}

	public void destroy() {
	}
}
