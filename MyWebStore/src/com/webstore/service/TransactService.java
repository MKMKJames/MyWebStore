package com.webstore.service;

import java.sql.SQLException;
import java.util.List;

import com.webstore.bean.Order;
import com.webstore.bean.User;
import com.webstore.dao.TransactDao;
import com.webstore.utils.DataSourceUtils;

public class TransactService {
	public boolean subOrder(Order order) {
		
		boolean flag = false;
		
		TransactDao dao = new TransactDao();
		try {
			DataSourceUtils.startTransaction();
			dao.addOrder(order);
			dao.addOrderItem(order);
			flag = true;
			
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	

	public List<Order> findOrderList(User user) {
		
		TransactDao dao = new TransactDao();
		List<Order> orderList = null;
		try {
			orderList =  dao.findOrderList(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return orderList;
	}

}
