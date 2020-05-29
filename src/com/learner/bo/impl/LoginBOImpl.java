package com.learner.bo.impl;

import java.util.Map;

import com.learner.bo.LoginBO;
import com.learner.dao.LoginDAO;
import com.learner.dao.impl.LoginDAOImpl;
import com.learner.exception.DAOException;

public class LoginBOImpl implements LoginBO {

	@Override
	public boolean checkLogin(Map<String, String> map) throws DAOException {
		LoginDAO logindao = new LoginDAOImpl();
		return logindao.checkLogin(map);
		
	}

}
