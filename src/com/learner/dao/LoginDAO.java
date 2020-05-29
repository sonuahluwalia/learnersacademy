package com.learner.dao;

import java.util.Map;

import com.learner.exception.DAOException;

public interface LoginDAO {

	boolean checkLogin(Map<String, String> map) throws DAOException;

}
