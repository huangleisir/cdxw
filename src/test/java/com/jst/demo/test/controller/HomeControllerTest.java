package com.jst.demo.test.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSONObject;
import com.jst.demo.test.AbstractTest;

public class HomeControllerTest extends AbstractTest{

	@Test
	public void testInfo() throws Exception {
		JSONObject param = new JSONObject(baseMap) ;
		 
        String json = param.toString() ;
        System.out.println("=================/home/info===============请求开始,入参："+json);
        RequestBuilder request = MockMvcRequestBuilders.post("/home/info")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("SESSIONNO", "ed148db36de80c544254ee3ce3e829c0")
				.content(json) ;
		
        MvcResult mvcResult = mvc.perform(request).andReturn() ;
	    
        int status = mvcResult.getResponse().getStatus();  
        String content = mvcResult.getResponse().getContentAsString();
        
        Assert.assertTrue("错误，正确的返回值为200", status == 200);  
        Assert.assertFalse("错误，正确的返回值为200", status != 200);  
        
        System.out.println("返回结果："+status);
        System.out.println(content);
		
	}

}
