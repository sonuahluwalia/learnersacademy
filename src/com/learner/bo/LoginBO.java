package com.learner.bo;

import java.util.Map;

import com.learner.exception.DAOException;

public interface LoginBO {

	boolean checkLogin(Map<String, String> map) throws DAOException;

}
