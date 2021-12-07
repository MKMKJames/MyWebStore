package com.webstore.service;

import java.sql.SQLException;
import java.util.List;

import com.webstore.bean.PageBean;
import com.webstore.bean.Product;
import com.webstore.dao.ProductDao;

public class ProductService {

	private ProductDao dao = new ProductDao();

	public int totalRecord(String cid) {

		long totalRecord;
		try {
			totalRecord = dao.totalRecord(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return new Long(totalRecord).intValue();
	}

	public int totalRecordByName(String name) {

		long totalRecord;
		try {
			totalRecord = dao.totalRecordByName(name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return new Long(totalRecord).intValue();
	}

	public boolean addNewProduct(Product product) throws SQLException {
		int row;
		row = dao.addNewProduct(product);
		return row > 0;
	}

	public List<Product> findNewProduct() {

		try {
			return dao.findNewProduct();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findCategoryProduct(PageBean pageBean, String cid) {
		
		//对分页业务进行处理
		int start = (pageBean.getPageIndex()-1)*pageBean.getPageContent();
		int end = pageBean.getPageContent();
		try {
			return dao.findCategoryProduct(start,end,cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Product> findCategoryProductByName(PageBean pageBean, String name) {
		
		//对分页业务进行处理
		int start = (pageBean.getPageIndex()-1)*pageBean.getPageContent();
		int end = pageBean.getPageContent();
		try {
			return dao.findCategoryProductByName(start, end, name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public boolean delProduct(String pid) {
		try {
			return dao.delProduct(pid) > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public Product getProduct_info(String pid) {

		try {
			return dao.getProduct_info(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
