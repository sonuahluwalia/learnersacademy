package com.learner.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.bo.StudentBO;
import com.learner.bo.StudentDAO;
import com.learner.dao.impl.StudentDAOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public class StudentBOImpl implements StudentBO {

	@Override
	public int getNextStudentId() throws DAOException, BusinessException {
	
		StudentDAO studentdao = new StudentDAOImpl();
		int nextStudentId = 0;
		nextStudentId = studentdao.getNextStudentId();
		if (nextStudentId != 0) {
			return nextStudentId;
		} else {
			throw new BusinessException("Could not get student id");
		}
	}
	
	@Override
	public HashMap<String, ArrayList<?>> getAllStudents() throws BusinessException, DAOException {
		StudentDAO studentdao = new StudentDAOImpl();
		HashMap<String, ArrayList<?>> map = studentdao.getAllStudents();
		// check if the data is not empty
		if (!map.isEmpty()) {
			return map;
		} else {
			throw new BusinessException("No data available for students");
		}
	}


	@Override
	public int deleteStudent(int student_id) throws DAOException {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.deleteStudent(student_id);
	}

	@Override
	public int insertStudent(Map<String, String> map) throws BusinessException, DAOException {

		StudentDAO studentdao = new StudentDAOImpl();
		if (checkIfStudentClassExists(map)) {
			throw new BusinessException("Student is already added to the class");
		} else {
			if (checkGender(map.get("gender"))) {
				return studentdao.insertStudent(map);
			} else {
				throw new BusinessException("Gender should be M or F");
			}

		}

	}
	
	public boolean checkGender(String gender) {
		return gender.matches("[M|F]");
	}

	public boolean checkIfStudentClassExists(Map<String, String> map) throws BusinessException, DAOException {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.checkIfStudentClassExists(map);
	}

	@Override
	public int updateStudent(Map<String, String> map) throws BusinessException, DAOException {

		StudentDAO studentdao = new StudentDAOImpl();
		String classname = map.get("classname");
		if (new ClassBOImpl().checkClassname(classname)) {
			if (checkGender(map.get("gender"))) {
				return studentdao.updateStudent(map);
			} else {
				throw new BusinessException("Gender should be M or F");
			}

		} else {
			throw new BusinessException("Class name should be: Class 1 - Class 12");
		}
	}


}
