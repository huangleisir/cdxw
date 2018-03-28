package com.jst.demo.test.controller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jst.demo.test.AbstractTest;
import com.taobao.stresstester.StressTestUtils;
import com.taobao.stresstester.core.StressTask;

public class TestControllerTest extends AbstractTest{
    
    @Autowired
    WebApplicationContext wac ;
    
    @Autowired
    StringRedisTemplate lstringRedisTemplate;
   	
	@Test
    public void testCache() throws Exception {
  	    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	        
	    StressTestUtils.testAndPrint(100, 100, new StressTask() {

            public Object doTask() throws Exception {
                
                /** 需要被压测代码段  */
                lstringRedisTemplate.opsForValue().set("aaa", "bbbbb");

                return null;
            }
        });
	    
	    System.exit(0);
   }
}
