package com.learner.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.learner.bo.SubjectBO;
import com.learner.bo.impl.SubjectBOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;
import com.learner.model.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Servlet implementation class SubjectsClasses
 */
@WebServlet(name = "Subjects", urlPatterns = { "/subjects" })
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubjectController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
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

		}

		else {
			response.sendRedirect("login.jsp");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SubjectBO subjectbo = new SubjectBOImpl();
			HashMap<String, ArrayList<?>> map = new HashMap<>();
			map = subjectbo.getAllSubjects();
			request.setAttribute("subjects", map.get("subjects"));
			request.setAttribute("classes", map.get("classes"));
			request.setAttribute("teachers", map.get("teachers"));
			request.setAttribute("classes_names", map.get("classes_names"));
			request.setAttribute("teachers_names", map.get("teachers_names"));
			request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			request.getRequestDispatcher("subjects.jsp").forward(request, response);
		} catch (BusinessException | DAOException e) {
			request.setAttribute("exception_subjects", e.getMessage());
			request.getRequestDispatcher("subjects.jsp").forward(request, response);
		}

	}

	public void doInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SubjectBO subjectbo = new SubjectBOImpl();
		Map map = request.getParameterMap();
		try {
			int result = subjectbo.insertSubject(map);

			if (result == 1) {
				request.setAttribute("success_insert",
						"Subject Name: " + request.getParameter("subjectname") + " Added");
				request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			} else {
				request.setAttribute("fail_insert",
						"Subject Name: " + request.getParameter("subjectname") + " Not Added");
				request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			}
			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {
			request.setAttribute("exception_insert", e.getMessage());
			doDisplay(request, response);

			try {
				request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			} catch (BusinessException | DAOException e1) {

				request.setAttribute("exception_next_subject_id", e.getMessage());
				doDisplay(request, response);

			}
		}
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SubjectBO subjectbo = new SubjectBOImpl();
		String subject_id = request.getParameter("subject_id");
		try {
			int result = subjectbo.deleteSubject(Integer.parseInt(subject_id));

			if (result == 1) {
				request.setAttribute("success_delete", "Deleted Subject with id: " + subject_id);
			} else {
				request.setAttribute("fail_delete", "Not Deleted Subject with id: " + subject_id);
			}
			doDisplay(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("subject_id_error", "Subject Id should be a number");
			doDisplay(request, response);

		} catch (DAOException | BusinessException e) {
			request.setAttribute("exception_delete", e.getMessage());
			doDisplay(request, response);

		}
	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SubjectBO subjectbo = new SubjectBOImpl();
		Map<String, String[]> map = request.getParameterMap();
		try {
			int result = subjectbo.updateSubject(map);
			if (result == 1) {
				request.setAttribute("success_update",
						"Subject Name: " + request.getParameter("subjectname") + " Updated");
				request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			} else {
				request.setAttribute("fail_update",
						"Subject Name: " + request.getParameter("subjectname") + " Not Updated");
				request.setAttribute("next_subject_id", subjectbo.getNextSubjectId());
			}

			doDisplay(request, response);
		} catch (BusinessException | DAOException e) {

			request.setAttribute("exception_update", e.getMessage());
			doDisplay(request, response);
		}

	}

}
