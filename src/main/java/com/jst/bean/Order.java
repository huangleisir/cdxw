package com.jst.bean;

import java.util.Date;
import java.util.List;

import com.jst.prodution.base.bean.BaseBean;

public class Order extends BaseBean{
	
	private String id;
	
	private String openId;
	
	private String createTime;
	
	private String payType;
	
	/**
	 * 0已下单  1已支付  2已过期
	 */
	private int payStatus;
	
	private String mark;
	
	private int sendStatus;
	
	private long foodAmount;
	
	private long sendAmount;
	
	private long orderAmount;
	
	private  String time ;//插入数据库的当前时间
	
	private String addrId;
	private String addr;
	private String name;
	private int sex;
	private String mobile;
	
	private int pageNo = 1 ;      //当前页
	private int pageSize = 20 ;   //每页显示条数
	
	private List<OrderDetail> foodList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public long getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(long foodAmount) {
		this.foodAmount = foodAmount;
	}

	public long getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(long sendAmount) {
		this.sendAmount = sendAmount;
	}

	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	public List<OrderDetail> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<OrderDetail> foodList) {
		this.foodList = foodList;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
