package com.zensar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zensar.daos.UserDao;
import com.zensar.daos.UserDaoImpl;
import com.zensar.entities.User;
import com.zensar.exceptions.UserServiceException;
import com.zensar.service.UserService;
import com.zensar.service.UserServiceImpl;


/**
 * Servlet implementation class LoginController
 */
@WebServlet("/checkuser")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//servletcontex object represents entire web application and created only once and available to all servlets as a comman
		ServletContext context = config.getServletContext();
		String driverClassName = context.getInitParameter("jdbcDriver");
		String dbUrl = context.getInitParameter("jdbcUrl");
		String dbUserName = context.getInitParameter("dbuser");
		String dbPassword = context.getInitParameter("dbpassword");
		System.out.println(driverClassName);
		System.out.println(dbUrl);
		System.out.println(dbUserName);
		System.out.println(dbPassword);
		//load jdbc driver class
		try {
			Class.forName(driverClassName);
			context.log("deiver is loaded successfully");
			Connection con =  DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			context.log("Mysql connection established");
			//create UserDao object
			UserDao userDao = new UserDaoImpl();
			((UserDaoImpl) userDao).setConnection(con);
			//create userService object
			UserService userService = new UserServiceImpl();
			((UserServiceImpl)userService).setUserDao(userDao);
			//set userService to a servlet
			setUserService(userService);
			context.log("UserServiec is set in LoginServlet");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User clientUser = new User();
		clientUser.setUsername(username);
		clientUser.setPassword(password);
		PrintWriter out = response.getWriter();
		//invoking business logic of business layer in presentation layer
		try {
			if(userService.validateUser(clientUser))
			{
				//out.println("<p>Dear "+username+" Welcome to online shopping</p>");
				RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
				rd.forward(request, response);
			}
			else
			{
				out.println("<p>Sorry! Invalid username or password</p>");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.include(request, response);
			}
		} catch (UserServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
