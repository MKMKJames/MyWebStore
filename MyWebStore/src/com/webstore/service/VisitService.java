package com.webstore.service;

import java.sql.SQLException;
import java.util.List;

import com.webstore.bean.VisitLog;
import com.webstore.dao.VisitLogDao;

public class VisitService {
	private VisitLogDao dao = new VisitLogDao();

	public boolean logVisit(VisitLog visit) {
		int row;
		try {
			row = dao.logVisit(visit);
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<VisitLog> getVisitList() {
		List<VisitLog> list = null;
		try {
			list = dao.getVisitList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
