package com.jst.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jst.bean.FoodType;
import com.jst.dao.base.AbstractDao;

@Repository("FoodTypeDao")
public class FoodTypeDao extends AbstractDao{
	
	 private final String MAPPER_NAMESPACE = "FoodType." ;


	/**
	 *  
	 * @return
	 */
	public List<FoodType> selectAll(FoodType foodType) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectAll"), foodType) ;
	}
}
