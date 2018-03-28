package com.jst.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.bean.Order;
import com.jst.bean.OrderDetail;
import com.jst.dao.base.AbstractDao;

@Repository("OrderDao")
public class OrderDao extends AbstractDao{
	
	 private final String MAPPER_NAMESPACE = "Order." ;


	/**
	 *  
	 * @return
	 */
	public List<Food> selectAll(Order order) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectAll"), order) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public List<Order> select(Order order) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("select"), order) ;
	}

	public PageInfo<Order> selectOrder(Order order) {
		return baseDao.getPage(MAPPER_NAMESPACE.concat("selectOrder"), order) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public int insert(Order order) {
		return baseDao.insert(MAPPER_NAMESPACE.concat("insert"), order) ;
	}

	public int updateOrder(Order order) {
		return baseDao.insert(MAPPER_NAMESPACE.concat("update"), order) ;
	}

	public PageInfo<Order> getOrderListByOpenId(Order order) {
		return baseDao.getPage(MAPPER_NAMESPACE.concat("getOrderListByOpenId"), order) ;
	}

}
