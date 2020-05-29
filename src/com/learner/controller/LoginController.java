package com.learner.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learner.bo.LoginBO;
import com.learner.bo.impl.LoginBOImpl;
import com.learner.exception.DAOException;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("logout") != null) {
			request.getSession(false).invalidate();
			response.sendRedirect("login.jsp");
		} else if (request.getParameter("login") != null) {
			LoginBO loginbo = new LoginBOImpl();
			Map<String, String> map = new HashMap<>();
			map.put("username", request.getParameter("username"));
			map.put("password", request.getParameter("password"));
			try {
				boolean result = loginbo.checkLogin(map);
				if (result) {
					request.setAttribute("success_login", "Login Successful");
					request.getSession(true).setAttribute("login", "true");
					request.getRequestDispatcher("home").forward(request, response);
				} else {
					request.setAttribute("error_login", "Login Unsuccessful");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			} catch (DAOException e) {
				request.setAttribute("error_login", e.getMessage());
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		}
	}

}
