package com.learner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.learner.exception.DAOException;


public interface TeacherDAO {

	int getNextTeacherId() throws DAOException;

	int getTeacherIdFromTeacherName(String teacher_name) throws DAOException;

	int insertTeacher(Map<String, String> map) throws DAOException;

	boolean checkTeacherIdClassIdExists(int teacher_id, int class_id) throws DAOException;

	public ArrayList<String> getAllTeachersNames() throws DAOException;

	public boolean insertExistingTeacherNewClass(int teacher_id, int class_id, String teachername) throws DAOException;

	HashMap<String, ArrayList<?>> getAllTeachers() throws DAOException;

	boolean checkIfTeacherClassSubjectExists(Map<String, String> map) throws DAOException;

	int deleteTeacher(int teacher_id) throws DAOException;

	int updateTeacher(Map<String, String> map) throws DAOException;
}