package com.webstore.bean;

public class VisitLog {
	private String time;
	private String uid;
	private String pid;

	public void setTime(String time) {
		this.time = time;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTime() {
		return this.time;
	}

	public String getUid() {
		return this.uid;
	}

	public String getPid() {
		return this.pid;
	}
}
