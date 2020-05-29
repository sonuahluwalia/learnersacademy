package com.learner.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public interface ClassBO {
	public boolean checkClassname(String value);

	public HashMap<String, ArrayList<?>> getAllClasses() throws BusinessException, DAOException;

	public int getNextClassId() throws DAOException, BusinessException;
	
	public int deleteClass(int class_id) throws DAOException;

	int insertClass(Map<String, String> map) throws BusinessException, DAOException;

	int updateClass(Map<String, String> map) throws BusinessException, DAOException;

}