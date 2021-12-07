package com.webstore.bean;

public class OrderItem {
	
	private String itemid;//�������id
	private int count;//����������Ʒ�Ĺ�������
	private double subtotal;//������С��
	private Product product;//�������ڲ�����Ʒ
	private Order order;//�ö����������ĸ�����
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
