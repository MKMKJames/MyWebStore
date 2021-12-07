package com.webstore.service;

import java.sql.SQLException;

import com.webstore.bean.AdminUser;
import com.webstore.bean.User;
import com.webstore.dao.UserDao;

public class UserService {
	
	private UserDao dao = new UserDao();
	
	public boolean register(User user) {
		
		int row;
		try {
			row = dao.register(user);
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checkUsername(String username) {
		
		long result;
		try {
			result = dao.checkUsername(username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result>0;
	}
	
	public boolean checkEmail(String email) {
		
		long result;
		try {
			result = dao.checkEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result>0;
	}

	public User login(String username, String password) {
		
		try {
			return dao.login(username,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public AdminUser adminLogin(String username, String password) {
		try {
			return dao.adminLogin(username,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
