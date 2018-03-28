package com.jst.bean;

public class SysParams {
	
	private String id;
	
	private int orderCillAmount;
	
	private int sendAmount;
	
	private int orderFreeAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrderCillAmount() {
		return orderCillAmount;
	}

	public void setOrderCillAmount(int orderCillAmount) {
		this.orderCillAmount = orderCillAmount;
	}

	public int getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(int sendAmount) {
		this.sendAmount = sendAmount;
	}

	public int getOrderFreeAmount() {
		return orderFreeAmount;
	}

	public void setOrderFreeAmount(int orderFreeAmount) {
		this.orderFreeAmount = orderFreeAmount;
	}
	

}
