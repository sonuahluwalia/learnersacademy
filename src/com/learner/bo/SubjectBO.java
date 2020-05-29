package com.learner.bo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.BusinessException;
import com.learner.exception.DAOException;

public interface SubjectBO {
	
	public HashMap<String, ArrayList<?>> getAllSubjects() throws BusinessException, DAOException;
	public int getNextSubjectId() throws BusinessException, DAOException ;
	public int insertSubject(Map<String, String[]> map) throws BusinessException, DAOException;
	public int deleteSubject(int subject_id) throws DAOException, BusinessException;
	int updateSubject(Map<String, String[]> map) throws DAOException, BusinessException;
	
}
