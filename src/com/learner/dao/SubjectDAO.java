package com.learner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.DAOException;

public interface SubjectDAO {
	// public static ArrayList<Subject> getAllSubjects();
	public HashMap<String, ArrayList<?>> getAllSubjects() throws DAOException;
	public int getNextSubjectId() throws DAOException  ;
	public int deleteSubject(int subject_id) throws DAOException;
	int insertSubject(String[] parameters) throws DAOException;
	boolean checkIfSubjectClassExists(String[] parameters) throws DAOException;
	int updateSubject(String[] parameters) throws DAOException;
	public ArrayList<String> getAllSubjectNames() throws DAOException;
	public int getSubjectIdFromSubjectName(String subjectname) throws DAOException;
	

}
