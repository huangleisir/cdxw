package com.jst.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jst.bean.Address;
import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.dao.base.AbstractDao;

@Repository("AddressDao")
public class AddressDao extends AbstractDao{
	
	 private final String MAPPER_NAMESPACE = "Address." ;


	/**
	 *  
	 * @return
	 */
	public List<Address> selectAll(Address address) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectAll"), address) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public List<Address> select(Address address) {
		return baseDao.getList(MAPPER_NAMESPACE.concat("selectOne"), address) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public int insert(Address address) {
		return baseDao.insert(MAPPER_NAMESPACE.concat("insert"), address) ;
	}
	
	/**
	 *  
	 * @return
	 */
	public int update(Address address) {
		return baseDao.update(MAPPER_NAMESPACE.concat("update"), address) ;
	}

	public int deleteAddrById(Address address) {
		
		return baseDao.delete(MAPPER_NAMESPACE.concat("delete"), address.getId()) ;
	}
}
