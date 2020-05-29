package com.learner.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.learner.bo.TeacherBO;
import com.learner.dao.SubjectDAO;
import com.learner.dao.TeacherDAO;
import com.learner.dao.impl.SubjectDAOImpl;
import com.learner.dao.impl.TeacherDAOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public class TeacherBOImpl implements TeacherBO {

	@Override
	public HashMap<String, ArrayList<?>> getAllTeachers() throws BusinessException, DAOException {
		TeacherDAO teacherdao = new TeacherDAOImpl();
		HashMap<String, ArrayList<?>> map = teacherdao.getAllTeachers();
		// check if the data is not empty
		if (!map.isEmpty()) {
			return map;
		} else {
			throw new BusinessException("No data available for teachers");
		}
	}

	
	@Override
	public int insertTeacher(Map<String, String> map) throws BusinessException, DAOException {

		TeacherDAO teacherdao = new TeacherDAOImpl();
		if (checkIfTeacherClassSubjectExists(map)) {
			throw new BusinessException("Teacher is already added to the class and subject");
		} else {
			if (checkGender(map.get("gender"))) {
				return teacherdao.insertTeacher(map);
			} else {
				throw new BusinessException("Gender should be M or F");
			}

		}

	}

	public boolean checkGender(String gender) {
		return gender.matches("[M|F]");
	}

	public boolean checkIfTeacherClassSubjectExists(Map<String, String> map) throws BusinessException, DAOException {
		TeacherDAO teacherdao = new TeacherDAOImpl();
		return teacherdao.checkIfTeacherClassSubjectExists(map);
	}

	@Override
	public int getNextTeacherId() throws DAOException, BusinessException {
	
		TeacherDAO teacherdao = new TeacherDAOImpl();
		int nextTeacherId = 0;
		nextTeacherId = teacherdao.getNextTeacherId();
		if (nextTeacherId != 0) {
			return nextTeacherId;
		} else {
			throw new BusinessException("Could not get teacher id");
		}
	}

	@Override
	public int deleteTeacher(int teacher_id) throws DAOException {
		TeacherDAO teacherdao = new TeacherDAOImpl();
		return teacherdao.deleteTeacher(teacher_id);
	}

	@Override
	public int updateTeacher(Map<String, String> map) throws BusinessException, DAOException {

		TeacherDAO teacherdao = new TeacherDAOImpl();
		String classname = map.get("classname");
		if (new ClassBOImpl().checkClassname(classname)) {
			if (checkGender(map.get("gender"))) {
				return teacherdao.updateTeacher(map);
			} else {
				throw new BusinessException("Gender should be M or F");
			}

		} else {
			throw new BusinessException("Class name should be: Class 1 - Class 12");
		}
	}

}
