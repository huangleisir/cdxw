package com.jst.service;

import java.util.List;

import com.jst.bean.Address;
import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.bean.Order;
import com.jst.bean.SysParams;

public interface BookService {

	public List<FoodType> foodTypeList(FoodType foodType);
	
	public List<Food> getFoodListByTypeId(Food food);
	
	public List<Address> getAddrListByOpenId(Address address);
	
	public String saveAddr(Address address);
	
	public int updateAddr(Address address);
	
	public String saveOrder(Order order);
	
	
	public int updateOrder(Order order);

	public List<Order> getOrderListByOpenId(Order order);

	public SysParams findSysParams();

	public int deleteAddrById(Address address);

	public Food findByFoodId(String foodId);

	public List<Order> getOrderDetailByOpenId(Order order);
	
	
}
