package com.learner.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learner.bo.ClassBO;
import com.learner.bo.TeacherBO;
import com.learner.bo.impl.ClassBOImpl;
import com.learner.bo.impl.TeacherBOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

/**
 * Servlet implementation class ClassController
 */
@WebServlet("/classes")
public class ClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = ((String) request.getSession(false).getAttribute("login"));
		if (request.getParameter("logout") != null) {
			request.getSession(false).invalidate();
			response.sendRedirect("login.jsp");
		} else if (login != null && login.equals("true")) {
			if (request.getParameter("action") == null) {
				response.sendRedirect("login.jsp");
			} else if (request.getParameter("action").equals("display")) {
				doDisplay(request, response);
			} else if (request.getParameter("action").equals("insert")) {
				doInsert(request, response);
			} else if (request.getParameter("action").equals("delete")) {
				doDelete(request, response);
			} else if (request.getParameter("action").equals("update")) {
				doUpdate(request, response);
			}
		} else {
			response.sendRedirect("login.jsp");
		}

	}

	public void doDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ClassBO classbo = new ClassBOImpl();
			HashMap<String, ArrayList<?>> map = new HashMap<>();
			map = classbo.getAllClasses();

			if (map.size() == 0 || map == null) {
				throw new BusinessException("Classes data not available");
			} else {
				// request.setAttribute("exception_logic", map.get("teachers").get(0));

				request.setAttribute("classes", map.get("classes"));
				request.setAttribute("next_class_id", classbo.getNextClassId());
			}
			request.getRequestDispatcher("classes.jsp").forward(request, response);

		} catch (BusinessException | DAOException e) {
			request.setAttribute("exception_all_data_classes", e.getMessage());
			request.getRequestDispatcher("classes.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception_all_data_classes", e.getMessage());
			request.getRequestDispatcher("classes.jsp").forward(request, response);
		}

	}

	public void doInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClassBO classbo = new ClassBOImpl();
		Map<String, String> map = new HashMap<String, String>();

		try {
			map.put("classname", request.getParameter("classname"));
			int result = classbo.insertClass(map);
			// request.setAttribute("exception_logic", request.getParameter("classid"));

			if (result == 1) {
				request.setAttribute("success_insert", "Class Name: " + request.getParameter("classname") + " Added");
				request.setAttribute("next_class_id", classbo.getNextClassId());
			} else {
				request.setAttribute("fail_insert", "Class Name: " + request.getParameter("classname") + " Not Added");
				request.setAttribute("next_class_id", classbo.getNextClassId());
			}
			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_insert", e.getMessage());
			doDisplay(request, response);

			try {
				request.setAttribute("next_class_id", classbo.getNextClassId());
			} catch (BusinessException | DAOException e1) {

				request.setAttribute("exception_next_class_id", e.getMessage());
				doDisplay(request, response);

			}
		}
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClassBO classbo = new ClassBOImpl();
		String class_id = request.getParameter("classid");
		try {
			int result = classbo.deleteClass(Integer.parseInt(class_id));

			if (result == 1) {
				request.setAttribute("success_delete", "Deleted class with id: " + class_id);
			} else {
				request.setAttribute("fail_delete", "Not Deleted class with id: " + class_id);
			}
			doDisplay(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("class_id_error", "Class Id should be a number");
			doDisplay(request, response);

		} catch (DAOException e) {
			request.setAttribute("exception_delete", e.getMessage());
			doDisplay(request, response);

		}

	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClassBO classbo = new ClassBOImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("classid", request.getParameter("classid"));
		map.put("classname", request.getParameter("classname"));

		try {
			int result = classbo.updateClass(map);
			// request.setAttribute("exception_logic", "Result: "+result);

			if (result == 1) {
				request.setAttribute("success_update", "Class Name: " + request.getParameter("classname") + " Updated");
				request.setAttribute("next_class_id", classbo.getNextClassId());
			} else {
				request.setAttribute("fail_update",
						"Class Name: " + request.getParameter("classname") + " Not Updated");
				request.setAttribute("next_subject_id", classbo.getNextClassId());
			}

			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_update", e.getMessage());
			doDisplay(request, response);
		}

	}

}
