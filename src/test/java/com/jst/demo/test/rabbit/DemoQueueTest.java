package com.jst.demo.test.rabbit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jst.demo.test.AbstractTest;
import com.jst.rabbit.DemoQueue;
import com.jst.rabbit.PayDelay5QueueConfig;
import com.taobao.stresstester.StressTestUtils;
import com.taobao.stresstester.core.StressTask;

public class DemoQueueTest extends AbstractTest{

    @Autowired
    WebApplicationContext wac ;
    
    
    
    
	@Test
	public void send() throws Exception {
	
      DemoQueue.send("hello");
      PayDelay5QueueConfig.send("30秒后的消息");
      Thread.sleep(35000);
      
      
      
      
      mvc = MockMvcBuilders.webAppContextSetup(wac).build();
      
      
//    StressTestUtils.testAndPrint(100, 1000000, new StressTask() {
          
          
          StressTestUtils.testAndPrint(100, 100, new StressTask() {

          public Object doTask() throws Exception {
              
              
//              lstringRedisTemplate.opsForValue().set("aaa", "bbbbb");
              
//              System.out.println(lstringRedisTemplate.opsForValue().get("aaa"));
              
//              result.setData(lstringRedisTemplate.opsForValue().get("aaa"));
              
              
              return null;
          }
      });
      
      System.exit(0);

	}

}
