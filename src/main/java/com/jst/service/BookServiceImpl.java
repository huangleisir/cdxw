package com.jst.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jst.bean.Address;
import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.bean.Order;
import com.jst.bean.OrderDetail;
import com.jst.bean.SysParams;
import com.jst.common.util.SnowflakeIdUtil;
import com.jst.dao.AddressDao;
import com.jst.dao.FoodDao;
import com.jst.dao.FoodTypeDao;
import com.jst.dao.OrderDao;
import com.jst.dao.OrderDetailDao;
import com.jst.dao.SysParamsDao;
import com.jst.prodution.util.EmptyUtil;

@Service
public class BookServiceImpl implements BookService {
	protected Logger  log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	FoodTypeDao foodTypeDao ;
	
	@Autowired
	FoodDao foodDao ;
	
	@Autowired
    AddressDao AdrressDao ;
	
	@Autowired
	OrderDao orderDao ;
	
	@Autowired
	OrderDetailDao orderDetailDao ;
	
	
	@Autowired
	SysParamsDao sysParamsDao ;
	
	@Override
	public List<FoodType> foodTypeList(FoodType foodType) {
		log.info("================foodTypeList:",foodTypeDao.selectAll(foodType));
		return foodTypeDao.selectAll(foodType);
		
	}




	@Override
	public List<Food> getFoodListByTypeId(Food food) {
		// TODO Auto-generated method stub
		return foodDao.selectAll(food);
	}




	@Override
	public String saveAddr(Address address) {
		// TODO Auto-generated method stub
		String addrId = SnowflakeIdUtil.generate()+"";
		address.setId(addrId);
		 AdrressDao.insert(address);
		 return addrId;
	}
	
	@Override
	public int updateAddr(Address address) {
		 return AdrressDao.update(address);
	}
	
	@Override
	public String saveOrder(Order order) {
		int i = 0;
		String orderId = null;
		//先保存OrderDetail数据
		List<OrderDetail> foodList = order.getFoodList();
		//计算food_amount
		long foodAmount = 0; 
		if(EmptyUtil.isEmpty(foodList)) {
			
			return "error";
		}
			orderId = SnowflakeIdUtil.generate()+"";
			for(OrderDetail od : foodList) {
				od.setId(SnowflakeIdUtil.generate()+"");
				od.setOrderId(orderId);
				//查询菜价
				Food food = new Food();
				food.setFoodId(od.getFoodId());
				food = foodDao.findByFoodId(od.getFoodId());
				/*List<Food> list = foodDao.selectAll(food);
				if(EmptyUtil.isEmpty(list)) {
					return 0;
				}*/
				if(food == null) {
					log.info("-------查询不到对应的food-------");
					return null;
				}
				od.setPrice(food.getSalePrice());

				i = orderDetailDao.insert(od);
				log.info("-------保存orderDetail-------"+i);
				foodAmount = foodAmount + food.getSalePrice()*od.getNum();
			}
			//再保存order数据
			order.setFoodAmount(foodAmount);
			log.info("订单金额："+foodAmount);
			//查询配送费
			SysParams params = sysParamsDao.findSysParams();
			//计算订单餐费
			if(foodAmount < params.getOrderCillAmount()) {
				return null;
	        }else if (foodAmount < params.getOrderFreeAmount() && foodAmount>=params.getOrderCillAmount()) {
	        	order.setSendAmount(params.getSendAmount()); //达到配送金额没达到免配送费，重新设置配送费
	        }else {
	        	order.setSendAmount(0); //免配送费，重新设置配送费
	        }
			
			log.info("配送费："+order.getSendAmount());
			order.setId(orderId);
			i = orderDao.insert(order);
			log.info("-------保存地址-------"+i);
		
		return orderId;
	}

	@Override
	public List<Address> getAddrListByOpenId(Address address) {
		// TODO Auto-generated method stub
		return AdrressDao.selectAll(address);
	}
	@Override
	public List<Order> getOrderListByOpenId(Order order) {
		 List<Order> resultList = new ArrayList<Order>();
		 PageInfo<Order> pageInfo = orderDao.getOrderListByOpenId(order);

		 if(EmptyUtil.isNotEmpty(pageInfo.getList())) {
			 for(Order od : pageInfo.getList()) {
				 //根据orderId查询订单详情(包括详情表字段和菜字段)
				 List<OrderDetail> detailList = orderDetailDao.select(od);
				 if(EmptyUtil.isNotEmpty(detailList)) {
					 od.setFoodList(detailList);
					 resultList.add(od);
				 }
				 
			 }
		 }
		 
		 return resultList;
	}




	@Override
	public int updateOrder(Order order) {

		return orderDao.updateOrder(order);
	}




	@Override
	public SysParams findSysParams() {
		
		return sysParamsDao.findSysParams();
	}




	@Override
	public int deleteAddrById(Address address) {
		
		return AdrressDao.deleteAddrById(address);
	}



	//根据foodId查询food
	@Override
	public Food findByFoodId(String foodId) {
		
		return foodDao.findByFoodId(foodId);
	}



	public List<Order> getOrderDetailByOpenId(Order order) {
		 List<Order> resultList = new ArrayList<Order>();
		 PageInfo<Order> pageInfo = orderDao.selectOrder(order);

		 if(EmptyUtil.isNotEmpty(pageInfo.getList())) {
			 for(Order od : pageInfo.getList()) {
				 //根据orderId查询订单详情(包括详情表字段和菜字段)
				 List<OrderDetail> detailList = orderDetailDao.select(od);
				 if(EmptyUtil.isNotEmpty(detailList)) {
					 od.setFoodList(detailList);
					 resultList.add(od);
				 }
				 
			 }
		 }
		 
		 return resultList;
	}

	
}
