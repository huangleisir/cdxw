package com.jst.bean;

public class OrderDetail {
	
	private String id;
	
	private String orderId;
	
	private String foodId;
	
	private long price;
	//份数
	private int num;
	
	
	private String typeId;
	
	private String foodName;
	
	private String picPath;
	
	private long salePrice;
	
	private long oldPrice;
	//0删除1正常2售罄
	private int status;
	
	private int spiceLevel;
	
	private String mark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}
	public long getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(long oldPrice) {
		this.oldPrice = oldPrice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSpiceLevel() {
		return spiceLevel;
	}
	public void setSpiceLevel(int spiceLevel) {
		this.spiceLevel = spiceLevel;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	

}
