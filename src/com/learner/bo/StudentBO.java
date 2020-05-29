package com.learner.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public interface StudentBO {

	int getNextStudentId() throws DAOException, BusinessException;

	HashMap<String, ArrayList<?>> getAllStudents() throws BusinessException, DAOException;

	int deleteStudent(int student_id) throws DAOException;

	int insertStudent(Map<String, String> map) throws BusinessException, DAOException;

	int updateStudent(Map<String, String> map) throws BusinessException, DAOException;

}
