package com.learner.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.DAOException;

public interface StudentDAO {

	int getNextStudentId() throws DAOException;

	HashMap<String, ArrayList<?>> getAllStudents() throws DAOException;

	int deleteStudent(int student_id) throws DAOException;

	boolean checkIfStudentClassExists(Map<String, String> map) throws DAOException;

	int insertStudent(Map<String, String> map) throws DAOException;

	int updateStudent(Map<String, String> map) throws DAOException;

}
