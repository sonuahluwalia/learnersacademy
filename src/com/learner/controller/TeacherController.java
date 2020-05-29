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

import com.learner.bo.SubjectBO;
import com.learner.bo.TeacherBO;
import com.learner.bo.impl.SubjectBOImpl;
import com.learner.bo.impl.TeacherBOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

/**
 * Servlet implementation class TeacherController
 */
@WebServlet("/teachers")
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherController() {
		super();

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

	public void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TeacherBO teacherbo = new TeacherBOImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacherid", request.getParameter("teacherid"));
		map.put("teachername", request.getParameter("teachername"));
		map.put("classname", request.getParameter("classname"));
		map.put("subjectname", request.getParameter("subjectname"));
		map.put("qualification", request.getParameter("qualification"));
		map.put("experience_years", request.getParameter("experience_years"));
		map.put("age", request.getParameter("age"));
		map.put("gender", request.getParameter("gender"));

		try {
			int result = teacherbo.updateTeacher(map);
			// request.setAttribute("exception_logic", "Result: "+result);

			if (result == 1) {
				request.setAttribute("success_update",
						"Teacher Name: " + request.getParameter("teachername") + " Updated");
				request.setAttribute("next_teacher_id", teacherbo.getNextTeacherId());
			} else {
				request.setAttribute("fail_update",
						"Teacher Name: " + request.getParameter("teachername") + " Not Updated");
				request.setAttribute("next_subject_id", teacherbo.getNextTeacherId());
			}

			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_update", e.getMessage());
			doDisplay(request, response);
		}

	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TeacherBO teacherbo = new TeacherBOImpl();
		String teacher_id = request.getParameter("teacherid");
		try {
			int result = teacherbo.deleteTeacher(Integer.parseInt(teacher_id));

			if (result == 1) {
				request.setAttribute("success_delete", "Deleted Teacher with id: " + teacher_id);
			} else {
				request.setAttribute("fail_delete", "Not Deleted Teacher with id: " + teacher_id);
			}
			doDisplay(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("teacher_id_error", "Teacher Id should be a number");
			doDisplay(request, response);

		} catch (DAOException e) {
			request.setAttribute("exception_delete", e.getMessage());
			doDisplay(request, response);

		}

	}

	public void doDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TeacherBO teacherbo = new TeacherBOImpl();
			HashMap<String, ArrayList<?>> map = new HashMap<>();
			map = teacherbo.getAllTeachers();

			if (map.size() == 0 || map == null) {
				throw new BusinessException("Teachers data not available");
			} else {
				// request.setAttribute("exception_logic", map.get("teachers").get(0));

				request.setAttribute("subjects", map.get("subjects"));
				request.setAttribute("classes", map.get("classes"));
				request.setAttribute("teachers", map.get("teachers"));
				request.setAttribute("classes_names", map.get("classes_names"));
				request.setAttribute("subjects_names", map.get("subjects_names"));
				request.setAttribute("next_teacher_id", teacherbo.getNextTeacherId());
			}
			request.getRequestDispatcher("teachers.jsp").forward(request, response);

		} catch (BusinessException | DAOException e) {
			request.setAttribute("exception_all_data_teachers", e.getMessage());
			request.getRequestDispatcher("teachers.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception_all_data_teachers", e.getMessage());
			request.getRequestDispatcher("teachers.jsp").forward(request, response);
		}

	}

	public void doInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TeacherBO teacherbo = new TeacherBOImpl();

		Map<String, String> map = new HashMap<String, String>();

		try {
			map.put("teachername", request.getParameter("teachername"));
			map.put("classname", request.getParameter("classname"));
			map.put("subjectname", request.getParameter("subjectname"));
			map.put("qualification", request.getParameter("qualification"));
			map.put("experience_years", request.getParameter("experience_years"));
			map.put("age", request.getParameter("age"));
			map.put("gender", request.getParameter("gender"));
			int result = teacherbo.insertTeacher(map);
			request.setAttribute("exception_logic", request.getParameter("teacherid"));

			if (result == 1) {
				request.setAttribute("success_insert",
						"Teacher Name: " + request.getParameter("teachername") + " Added");
				request.setAttribute("next_teacher_id", teacherbo.getNextTeacherId());
			} else {
				request.setAttribute("fail_insert",
						"Teacher Name: " + request.getParameter("teachername") + " Not Added");
				request.setAttribute("next_teacher_id", teacherbo.getNextTeacherId());
			}
			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_insert", e.getMessage());
			doDisplay(request, response);

			try {
				request.setAttribute("next_teacher_id", teacherbo.getNextTeacherId());
			} catch (BusinessException | DAOException e1) {

				request.setAttribute("exception_next_teacher_id", e.getMessage());
				doDisplay(request, response);

			}
		}
	}

}
