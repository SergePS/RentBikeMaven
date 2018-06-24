package com.postnikov.epam.command;

import javax.servlet.http.HttpServletRequest;

import com.postnikov.epam.controller.Router;

/**
 * @author Sergey Postnikov
 *
 */
public interface Command {
	
	public Router execute(HttpServletRequest request);

}
