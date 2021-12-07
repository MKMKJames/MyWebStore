package com.webstore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.webstore.bean.VisitLog;
import com.webstore.utils.DataSourceUtils;

public class VisitLogDao {
	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public int logVisit(VisitLog visit) throws SQLException {
		String sql = "insert into visit values (?,?,?)";
		return runner.update(sql, visit.getTime(), visit.getUid(), visit.getPid());
	}
	
	public List<VisitLog> getVisitList() throws SQLException{
		String sql = "select * from visit;";
		List<VisitLog> visitList = runner.query(sql, new BeanListHandler<VisitLog>(VisitLog.class));
		return visitList;
	}
}
