package com.webstore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.webstore.bean.AdminUser;
import com.webstore.bean.User;
import com.webstore.utils.DataSourceUtils;

public class UserDao {

	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?)";
		int row = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getName()
				,user.getEmail(),user.getTelephone());
		DataSourceUtils.closeConnection();
		return row;
	}

	public long checkUsername(String username) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username = ?;";
		long row = (long) runner.query(sql, new ScalarHandler(), username);
		return row;
	}

	public long checkEmail(String email) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where email = ?;";
		long row = (long) runner.query(sql, new ScalarHandler(), email);
		return row;
	}
	
	public User login(String username, String password) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?;";
		User user = runner.query(sql, new BeanHandler<User>(User.class), username,password);
		return user;	
	}
	
	public AdminUser adminLogin(String username, String password) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from admin where username = ? and password = ?;";
		AdminUser adminUser = runner.query(sql, new BeanHandler<AdminUser>(AdminUser.class), username,password);
		return adminUser;	
	}
}
