package com.webstore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.webstore.bean.Product;
import com.webstore.utils.DataSourceUtils;

public class ProductDao {

	public long totalRecord(String cid) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?;";
		return (long) runner.query(sql, new ScalarHandler(), cid);
	}

	public long totalRecordByName(String name) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where pname like '%" + name + "%';";
		return (long) runner.query(sql, new ScalarHandler());
	}

	public List<Product> findCategoryProduct(int start, int end, String cid) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?;";
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), cid, start, end);
		return productList;
	}

	public List<Product> findCategoryProductByName(int start, int end, String name) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like '%" + name + "%' limit ?,?;";
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), start, end);
		return productList;
	}

	public List<Product> findHotProduct() throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = 1 limit ?,?;";
		List<Product> hotProductList = runner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		return hotProductList;
	}

	public List<Product> findNewProduct() throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> newProductList = runner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		return newProductList;
	}

	public Product getProduct_info(String pid) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?;";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		return product;
	}

	public int addNewProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values (?,?,?,?,?,?,?)";
		int row = runner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(),
				product.getShop_price(), product.getPimage(), product.getPdesc(), product.getCid());
		return row;
	}
	
	public int delProduct(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid = ?";
		int row = runner.update(sql, pid);
		return row;
	}
}
