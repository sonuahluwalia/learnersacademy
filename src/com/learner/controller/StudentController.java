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

import com.learner.bo.StudentBO;
import com.learner.bo.StudentBO;
import com.learner.bo.impl.StudentBOImpl;
import com.learner.bo.impl.StudentBOImpl;
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
		String login = ((String) request.getSession(false).getAttribute("login"));
		if (request.getParameter("logout") != null) {
			request.getSession(false).invalidate();
			response.sendRedirect("login.jsp");
		} else if (login != null && login.equals("true")) {
			if (request.getParameter("action") == null) {
				response.sendRedirect("main.jsp");
			} else if (request.getParameter("action").equals("display")) {
				doDisplay(request, response);
			} else if (request.getParameter("action").equals("insert")) {
				doInsert(request, response);
			} else if (request.getParameter("action").equals("delete")) {
				doDelete(request, response);
			} else if (request.getParameter("action").equals("update")) {
				doUpdate(request, response);
			} else {
				response.sendRedirect("login.jsp");
			}
		}
	}

	public void doDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StudentBO studentbo = new StudentBOImpl();
			HashMap<String, ArrayList<?>> map = new HashMap<>();
			map = studentbo.getAllStudents();

			if (map.size() == 0 || map == null) {
				throw new BusinessException("Students data not available");
			} else {
				// request.setAttribute("exception_logic", map.get("students"));

				request.setAttribute("students", map.get("students"));
				request.setAttribute("classes", map.get("classes"));
				request.setAttribute("classes_names", map.get("classes_names"));
				request.setAttribute("next_student_id", studentbo.getNextStudentId());
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

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentBO studentbo = new StudentBOImpl();
		String student_id = request.getParameter("studentid");
		try {
			int result = studentbo.deleteStudent(Integer.parseInt(student_id));

			if (result == 1) {
				request.setAttribute("success_delete", "Deleted Student with id: " + student_id);
			} else {
				request.setAttribute("fail_delete", "Not Deleted Student with id: " + student_id);
			}
			doDisplay(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("student_id_error", "Student Id should be a number");
			doDisplay(request, response);

		} catch (DAOException e) {
			request.setAttribute("exception_delete", e.getMessage());
			doDisplay(request, response);

		}

	}

	public void doInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentBO studentbo = new StudentBOImpl();

		Map<String, String> map = new HashMap<String, String>();

		try {
			map.put("studentname", request.getParameter("studentname"));
			map.put("classname", request.getParameter("classname"));
			map.put("age", request.getParameter("age"));
			map.put("gender", request.getParameter("gender"));
			map.put("mathsgrade", request.getParameter("mathsgrade"));
			map.put("englishgrade", request.getParameter("englishgrade"));
			map.put("socialsciencegrade", request.getParameter("socialsciencegrade"));
			map.put("artgrade", request.getParameter("artgrade"));
			map.put("sciencegrade", request.getParameter("sciencegrade"));
			map.put("finalgrade", request.getParameter("finalgrade"));

			int result = studentbo.insertStudent(map);
			// request.setAttribute("exception_logic", request.getParameter("studentid"));

			if (result == 1) {
				request.setAttribute("success_insert",
						"Student Name: " + request.getParameter("studentname") + " Added");
				request.setAttribute("next_student_id", studentbo.getNextStudentId());
			} else {
				request.setAttribute("fail_insert",
						"Student Name: " + request.getParameter("studentname") + " Not Added");
				request.setAttribute("next_student_id", studentbo.getNextStudentId());
			}
			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_insert", e.getMessage());
			doDisplay(request, response);

			try {
				request.setAttribute("next_student_id", studentbo.getNextStudentId());
			} catch (BusinessException | DAOException e1) {

				request.setAttribute("exception_next_student_id", e.getMessage());
				doDisplay(request, response);

			}
		}

	}

	// TODO doUpdate in student controller
	public void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentBO studentbo = new StudentBOImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentid", request.getParameter("studentid"));
		map.put("studentname", request.getParameter("studentname"));
		map.put("classname", request.getParameter("classname"));
		map.put("age", request.getParameter("age"));
		map.put("gender", request.getParameter("gender"));
		map.put("mathsgrade", request.getParameter("mathsgrade"));
		map.put("englishgrade", request.getParameter("englishgrade"));
		map.put("socialsciencegrade", request.getParameter("socialsciencegrade"));
		map.put("artgrade", request.getParameter("artgrade"));
		map.put("sciencegrade", request.getParameter("sciencegrade"));
		map.put("finalgrade", request.getParameter("finalgrade"));

		try {
			int result = studentbo.updateStudent(map);
			// request.setAttribute("exception_logic", "Result: "+result);

			if (result == 1) {
				request.setAttribute("success_update",
						"Student Name: " + request.getParameter("studentname") + " Updated");
				request.setAttribute("next_student_id", studentbo.getNextStudentId());
			} else {
				request.setAttribute("fail_update",
						"Student Name: " + request.getParameter("studentname") + " Not Updated");
				request.setAttribute("next_subject_id", studentbo.getNextStudentId());
			}

			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_update", e.getMessage());
			doDisplay(request, response);
		}

	}

}
