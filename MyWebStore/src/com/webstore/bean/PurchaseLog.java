package com.webstore.bean;

public class PurchaseLog {
	private String ordertime;
	private String uid;
	private int count;
	private String pid;

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrdertime() {
		return this.ordertime;
	}

	public String getUid() {
		return this.uid;
	}

	public String getPid() {
		return this.pid;
	}
}
