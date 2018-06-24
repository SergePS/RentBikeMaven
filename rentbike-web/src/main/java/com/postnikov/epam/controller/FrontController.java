package com.postnikov.epam.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.command.ActionFactory;
import com.postnikov.epam.command.Command;
import com.postnikov.epam.command.EmptyCommand;
import com.postnikov.epam.rentbike.connection.ConnectionPool;

@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024
		* 1024 * 5 * 5)
@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(FrontController.class);
	
	private static final long serialVersionUID = -3752564644584125999L;

	private final static String COMMAND = "command";

	public FrontController() {
		super();
	}

	@Override
	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Optional<Command> optionalCommand = ActionFactory.defineCommand(request.getParameter(COMMAND));
		Command command = optionalCommand.orElse(new EmptyCommand());

		logger.log(Level.DEBUG, "Command - " + request.getParameter(COMMAND));
		
		Router router = command.execute(request);
		
		if(RouteType.FORWARD.equals(router.getRoute())) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
			dispatcher.forward(request, response);
		}else {
			String path = request.getContextPath() + router.getPagePath();
			response.sendRedirect(path);
		}
	}
	
	
	@Override
	public void destroy() {
		ConnectionPool.getInstance().closeAllWrapperConnection();
		logger.log(Level.DEBUG, "Servlet is closing");
		super.destroy();
	}

}
