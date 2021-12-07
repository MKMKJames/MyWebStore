package com.webstore.service;

import java.sql.SQLException;
import java.util.List;

import com.webstore.bean.PurchaseLog;
import com.webstore.dao.PurchaseLogDao;

public class PurchaseService {
	private PurchaseLogDao dao = new PurchaseLogDao();

	public List<PurchaseLog> getPurchaseList() {
		List<PurchaseLog> list = null;
		try {
			list = dao.getPurchaseList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
