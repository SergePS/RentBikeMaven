package com.postnikov.epam.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.postnikov.epam.command.CommandType;
import com.postnikov.epam.rentbike.domain.PageConstant;
import com.postnikov.epam.rentbike.domain.entity.AccessLevel;
import com.postnikov.epam.rentbike.domain.entity.User;

@WebFilter(urlPatterns = { "/FrontController" }, servletNames = { "FrontController" })
public class ServletSecurityFilter implements Filter{
	
	private final static String COMMAND = "command";

	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String login = CommandType.LOGIN.toString().toLowerCase();
		String register = CommandType.REGISTER.toString().toLowerCase();
		String change_localization = CommandType.CHANGE_LOCALIZATION.toString().toLowerCase();
		String commandString = request.getParameter(COMMAND);
		
		User user = (User) session.getAttribute("user");
		
		
		if(!(login.equals(commandString) || register.equals(commandString) || change_localization.equals(commandString))) {
			if(user==null) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageConstant.LOGIN_PAGE);
				requestDispatcher.forward(req, resp);
				return;
			}else {
				AccessLevel commandAccessLevel = CommandType.valueOf(commandString.toUpperCase()).getAccessLevel();
				AccessLevel userAccessLevel = user.getRole().getAccessLevel();
				if(commandAccessLevel.compareTo(userAccessLevel)>0) {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(user.getRole().getHomePage());
					requestDispatcher.forward(req, resp);
					return;
				}
			}
		}
		

		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	}



}
