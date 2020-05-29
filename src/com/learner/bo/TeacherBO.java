package com.learner.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public interface TeacherBO {

	HashMap<String, ArrayList<?>> getAllTeachers() throws BusinessException, DAOException;

	int insertTeacher(Map<String, String> map) throws BusinessException, DAOException;

	int getNextTeacherId() throws DAOException, BusinessException;

	int deleteTeacher(int teacher_id) throws DAOException;

	int updateTeacher(Map<String, String> map) throws BusinessException, DAOException;
	
}
