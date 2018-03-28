/*package com.jst.demo.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import com.jst.demo.bean.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserCacheImpl userCacheImpl;
	
	
	@Test
	public void testAddUsrInfoPwd() {
		User user = new User();
		user.setId("1");
		user.setName("Test1");
		user.setPwd("111111");
		int flag = userCacheImpl.addUsrInfoPwd(user);
		
		System.out.println("增加用户返回结果:"+ (flag ==0?"失败":"成功"));
	}

	@Test
	public void testUserLogin() {
		
		User user = new User();
		user.setId("1");
		user.setName("Test1");
		System.out.println("查询用户入参:"+ JSON.toJSONString(user));
		User us = userCacheImpl.userLogin(user);
		System.out.println("查询用户返回结果:"+ JSON.toJSONString(us));
		
	}
	
	@Test
	public void testUpdUsrPwdInf() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setPwd("111111");
		int flag = userCacheImpl.addUsrInfoPwd(user);
		
		System.out.println("更新用户返回结果:"+ (flag ==0?"失败":"成功"));
	}

}
*/