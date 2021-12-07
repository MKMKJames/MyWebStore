package com.webstore.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.webstore.bean.PurchaseLog;
import com.webstore.utils.DataSourceUtils;

public class PurchaseLogDao {
	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
	
	public List<PurchaseLog> getPurchaseList() throws SQLException{
		String sql = "select o.ordertime, o.uid, oi.count, oi.pid from orders o join orderitem oi on o.oid = oi.oid;";
		List<PurchaseLog> purchaseList = runner.query(sql, new BeanListHandler<PurchaseLog>(PurchaseLog.class));
		return purchaseList;
	}
}
