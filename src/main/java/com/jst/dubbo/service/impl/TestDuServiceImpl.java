/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.jst.prodution.base.bean.BaseBean;
import com.jst.prodution.demo.service.TestDuService;
import com.jst.service.TestService;

@Service
public class TestDuServiceImpl implements TestDuService {

	@Autowired
	TestService testService ;
	
	@Override
	public BaseBean action(BaseBean input) {
		return null;
		//return testService.action(input);
	}



}
