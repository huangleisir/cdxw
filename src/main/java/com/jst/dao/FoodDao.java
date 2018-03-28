package com.jst.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.dao.base.AbstractDao;

@Repository("FoodDao")
public class FoodDao extends AbstractDao{
	
	 private final String MAPPER_NAMESPACE = "Food." ;


	/**
	 *  
	 * @return
	 */
	public List<Food> selectAll(Food food) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectAll"), food) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public List<FoodType> select(FoodType foodType) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectOne"), foodType) ;
	}

	public Food findByFoodId(String foodId) {
		return baseDao.getOne(MAPPER_NAMESPACE.concat("selectOne"), foodId) ;
	}
}
