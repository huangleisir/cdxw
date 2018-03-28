package com.jst.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.bean.Order;
import com.jst.bean.OrderDetail;
import com.jst.dao.base.AbstractDao;

@Repository("OrderDetailDao")
public class OrderDetailDao extends AbstractDao{
	
	 private final String MAPPER_NAMESPACE = "OrderDetail." ;


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
	public List<OrderDetail> select(Order order) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("select"), order) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public int insert(OrderDetail orderDetail) {
		return baseDao.insert(MAPPER_NAMESPACE.concat("insert"), orderDetail) ;
	}

}
