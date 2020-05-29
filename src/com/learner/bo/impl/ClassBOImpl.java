package com.learner.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.bo.ClassBO;
import com.learner.dao.ClassDAO;
import com.learner.dao.TeacherDAO;
import com.learner.dao.impl.ClassDAOImpl;
import com.learner.dao.impl.TeacherDAOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public class ClassBOImpl implements ClassBO {

	public boolean checkClassname(String value) {
		//return true;
		return value.matches("Class\\s[1-9]|1[012]");
	}

	@Override
	public HashMap<String, ArrayList<?>> getAllClasses() throws BusinessException, DAOException {
		ClassDAO classdao = new ClassDAOImpl();
		HashMap<String, ArrayList<?>> map = classdao.getAllClasses();
		// check if the data is not empty
		if (!map.isEmpty()) {
			return map;
		} else {
			throw new BusinessException("No data available for classes");
		}
	}

	@Override
	public int getNextClassId() throws DAOException, BusinessException {
		ClassDAO classdao = new ClassDAOImpl();
		int nextClassId = 0;
		nextClassId = classdao.getNextClassId();
		if (nextClassId != 0) {
			return nextClassId;
		} else {
			throw new BusinessException("Could not get class id");
		}
	}

	public int deleteClass(int class_id) throws DAOException {
		ClassDAO classdao = new ClassDAOImpl();
		return classdao.deleteClass(class_id);
	}

	@Override
	public int insertClass(Map<String, String> map) throws BusinessException, DAOException {

		ClassDAO classdao = new ClassDAOImpl();
		if (checkClassname(map.get("classname"))) {
			return classdao.insertClass(map);
		} else {
			throw new BusinessException("Class name should be Class 1 to Class 12");
		}

	}
	
	
	@Override
	public int updateClass(Map<String, String> map) throws BusinessException, DAOException {

		ClassDAO classdao = new ClassDAOImpl();
		if (checkClassname(map.get("classname"))) {
			return classdao.updateClass(map);
		} else {
			throw new BusinessException("Class name should be Class 1 to Class 12");
		}
	}

	
}
