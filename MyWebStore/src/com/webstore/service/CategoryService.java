package com.webstore.service;

import java.sql.SQLException;
import java.util.List;

import com.webstore.bean.Category;
import com.webstore.dao.CategoryDao;

public class CategoryService {
	
	CategoryDao dao = new CategoryDao();

	public List<Category> fingAllCategory() {
		List<Category> list = null;
		try {
			list = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
