package com.learner.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learner.bo.StudentBO;
import com.learner.bo.TeacherBO;
import com.learner.bo.impl.StudentBOImpl;
import com.learner.bo.impl.TeacherBOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

/**
 * Servlet implementation class StudentController
 */
@WebServlet("/students")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("action") == null) {
			response.sendRedirect("main.jsp");
		} else if (request.getParameter("action").equals("display")) {
			doDisplay(request, response);
		} else if (request.getParameter("action").equals("insert")) {
//			doInsert(request, response);
		} else if (request.getParameter("action").equals("delete")) {
			doDelete(request, response);
		} else if (request.getParameter("action").equals("update")) {
//			doUpdate(request, response);
		}
	}
	// TODO DODISPLAY OF STUDENT CONTROLLER
	public void doDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StudentBO studentbo = new StudentBOImpl();
			HashMap<String, ArrayList<?>> map = new HashMap<>();
			map = studentbo.getAllStudents();

			if (map.size() == 0 || map == null) {
				throw new BusinessException("Students data not available");
			} else {
				// request.setAttribute("exception_logic", map.get("teachers").get(0));

				request.setAttribute("students", map.get("students"));
				request.setAttribute("classes", map.get("classes"));
				request.setAttribute("classes_names", map.get("classes_names"));
				request.setAttribute("next_teacher_id", studentbo.getNextStudentId());
			}
			request.getRequestDispatcher("students.jsp").forward(request, response);

		} catch (BusinessException | DAOException e) {
			request.setAttribute("exception_all_data_students", e.getMessage());
			request.getRequestDispatcher("students.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception_all_data_students", e.getMessage());
			request.getRequestDispatcher("students.jsp").forward(request, response);
		}

	}

}
