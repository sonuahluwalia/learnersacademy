package com.learner.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.learner.bo.SubjectBO;
import com.learner.dao.SubjectDAO;
import com.learner.dao.impl.SubjectDAOImpl;
import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public class SubjectBOImpl implements SubjectBO {

	@Override
	public HashMap<String, ArrayList<?>> getAllSubjects() throws BusinessException, DAOException {
		SubjectDAO subjectdao = new SubjectDAOImpl();
		HashMap<String, ArrayList<?>> map = subjectdao.getAllSubjects();
		// check if the data is not empty
		if (!map.isEmpty()) {
			return map;
		} else {
			throw new BusinessException("No data available for subjects");
		}

	}

	@Override
	public int getNextSubjectId() throws BusinessException, DAOException {
		
		SubjectDAO subjectdao = new SubjectDAOImpl();
		int nextSubjectId = 0;
		nextSubjectId = subjectdao.getNextSubjectId();
		if (nextSubjectId != 0) {
			return nextSubjectId;
		} else {
			throw new BusinessException("Could not get subject id");
		}

	}

	@Override
	public int insertSubject(Map<String, String[]> map) throws BusinessException, DAOException {
		
		SubjectDAO subjectdao = new SubjectDAOImpl();
		String[] parameters = setParamenters(map);
		if (checkIfSubjectClassExists(parameters)) {
			throw new BusinessException("Subject is already added to the class");
		} else {
			return subjectdao.insertSubject(parameters);

		}

	}

	public boolean checkIfSubjectClassExists(String[] parameters) throws BusinessException, DAOException {
		SubjectDAO subjectdao = new SubjectDAOImpl();
		return subjectdao.checkIfSubjectClassExists(parameters);
	}

	public String[] setParamenters(Map<String, String[]> map) throws BusinessException {
		Set s = map.entrySet();

		Iterator it = s.iterator();
		int count = 0;
		String subjectname = "", classname = "", subjectid = "";

		while (it.hasNext()) {
			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
 
			if (key.equals("subjectid")) {
 				subjectid = value[0];
			}
			if (key.equals("subjectname")) {
 				subjectname = value[0];
			}
			if (key.equals("classname")) {
 
				if (value[0] == null || value[0].isEmpty()) {

					throw new BusinessException("class name value cannot be empty");

				} else {
					classname = value[0];
				}
			}
		}
		return new String[] { subjectname, classname, subjectid };
	}


	@Override
	public int deleteSubject(int subject_id) throws BusinessException, DAOException {
		
		SubjectDAO subjectdao = new SubjectDAOImpl();
		return subjectdao.deleteSubject(subject_id);

	}

	@Override
	public int updateSubject(Map<String, String[]> map) throws BusinessException, DAOException {
		
		SubjectDAO subjectdao = new SubjectDAOImpl();
		String[] parameters = setParamenters(map);
		String classname = parameters[1];
		if (new ClassBOImpl().checkClassname(classname)) {
			if (checkIfSubjectClassExists(parameters)) {
				throw new BusinessException("Subject is already added to the class");
			}
			return subjectdao.updateSubject(parameters);
		} else {
			throw new BusinessException("Class name should be: Class 1 - Class 12");
		}

	}

}
