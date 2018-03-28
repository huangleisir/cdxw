package com.jst.bean;

public class Food {
	
	private String foodId;
	
	private String typeId;
	
	private String foodName;
	
	private String picPath;
	
	private long salePrice;
	
	private long oldPrice;
	//0删除1正常2售罄
	private int status;
	
	private int spiceLevel;
	
	private String mark;
	
	private String commendMark;

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
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

	public String getCommendMark() {
		return commendMark;
	}

	public void setCommendMark(String commendMark) {
		this.commendMark = commendMark;
	}

	
	

}
