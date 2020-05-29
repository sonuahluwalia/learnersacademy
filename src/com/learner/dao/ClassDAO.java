package com.learner.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;


public interface ClassDAO {

	int getClassIdFromClassName(String class_name) throws DAOException;

	int getNextClassId() throws DAOException;

	void insertClass(int class_id, String class_name) throws DAOException;
	
	public ArrayList<String> getAllClassesNames() throws DAOException ;

	HashMap<String, ArrayList<?>> getAllClasses() throws DAOException, BusinessException;

	int deleteClass(int class_id) throws DAOException;

	int insertClass(Map<String, String> map) throws DAOException;

	int updateClass(Map<String, String> map) throws DAOException;

}