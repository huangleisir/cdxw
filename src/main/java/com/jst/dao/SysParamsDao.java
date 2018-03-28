package com.jst.dao;

import org.springframework.stereotype.Repository;

import com.jst.bean.SysParams;
import com.jst.dao.base.AbstractDao;

@Repository("SysParamsDao")
public class SysParamsDao extends AbstractDao{
	
	private final String MAPPER_NAMESPACE = "SysParams." ;

	public SysParams findSysParams() {
		
		return baseDao.getOne(MAPPER_NAMESPACE.concat("selectOne"),"1") ;
	}

}
