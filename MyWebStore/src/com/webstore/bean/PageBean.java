package com.webstore.bean;

public class PageBean {
	
	//��ҳ��
	private int totalPage;
	//��ǰ�ǵڼ�ҳ
	private int pageIndex;
	//ÿҳ��ʾ����
	private int pageContent;
	//�ܼ�¼��
	private int totalRecord;
	
	public PageBean() {};
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageContent() {
		return pageContent;
	}
	public void setPageContent(int pageContent) {
		this.pageContent = pageContent;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	
}
