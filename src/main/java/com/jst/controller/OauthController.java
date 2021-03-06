/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jst.common.util.HttpClientUtil;
import com.jst.common.util.MD5Util;
import com.jst.config.ConfigSetting;
import com.jst.prodution.util.XmlUtil;



@RestController
@RequestMapping("/oauth/access")
public class OauthController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(OauthController.class);
//    @Resource
//    private  StringRedisTemplate stringRedisTemplate ;
    
    
    //获取微信code
    @RequestMapping(value = "/weixin")
    public void getCode(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	res.setContentType("text/html");  
    	res.setCharacterEncoding("UTF-8");  
    	res.setCharacterEncoding("UTF-8");  
       //回调地址（改为url:port/oauth/access/getOpenId）
    	String redirect_uri=ConfigSetting.getProperty("backUrl")+"/oauth/access/getOpenId";
    	String pageUrl=req.getParameter("pageUrl");
    	String state =pageUrl;
//    	String state = MD5Util.getMD5Str2(pageUrl+(pageUrl==null?"":pageUrl));
        redirect_uri=URLEncoder.encode(redirect_uri, "UTF-8");
       StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+  
                 "&appid="+ConfigSetting.getProperty("weixin.appid")+"&response_type=code&scope=snsapi_base&state="+state+"&connect_redirect=1#wechat_redirect");  
       log.info("===========请求微信code地址=============="+url);
//       req.getSession().getId();
       res.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可  
    }
    
    //获取微信openId
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOpenId")
    public void getOpenId(HttpServletRequest req, HttpServletResponse res) throws Exception {    
	    res.setContentType("text/html");  
	    res.setCharacterEncoding("UTF-8");  
	    String code = req.getParameter("code");//获取code  
	    String state = req.getParameter("state");//获取state  
	    log.info("===========微信返回======state========="+state);
	    Map params = new HashMap();  
	    params.put("secret", ConfigSetting.getProperty("weixin.secret"));  
	    params.put("appid", ConfigSetting.getProperty("weixin.appid"));  
	    params.put("grant_type", "authorization_code");  
	    params.put("code", code);  
	    String result = HttpClientUtil.doPost("https://api.weixin.qq.com/sns/oauth2/access_token", params);  
	    log.info("===========请求微信返回=============="+result);
		JSONObject jsonObject = JSON.parseObject(result);
	    String openid = jsonObject.get("openid").toString(); 
	    log.info("===========请求微信返回得到的openid为:=============="+openid);
//	    String pageUrl=ConfigSetting.getProperty("pageUrl.index");
	    //发送给其他页面
	    log.info("===========跳转页面为:=============="+state+"?openId="+openid);
	    res.sendRedirect(state+"?openId="+openid);//这里请不要使用get请求单纯的将页面跳转到该url即可  
	    
    }
    
    
//    @RequestMapping(value = "/weixin")
//    public void weixin(HttpServletRequest req, HttpServletResponse res,
//    		@RequestParam(required = true) String pageUrl,@RequestParam(required =false) String bindUrl,@RequestParam(required =false) String isCheckSub) throws Exception {
//    	log.info("===========oauth=====weixin========="+pageUrl);
//    	log.info("===========oauth=====bindUrl========="+bindUrl);
//    	log.info("===========oauth=====isCheckSub========="+isCheckSub);
//    	String   isCheck = isCheckSub==null?"0":isCheckSub;//0不需要校验关注 
//    	String state = MD5Util.getMD5Str2(pageUrl+(bindUrl==null?"":bindUrl)) ;
//    	JSONObject jsonObject = new JSONObject() ;
//    	jsonObject.put("pageUrl", pageUrl) ;
//    	jsonObject.put("bindUrl", bindUrl) ;
//    	jsonObject.put("isCheckSub", isCheck) ;
//    	stringRedisTemplate.opsForValue().set(state, jsonObject.toString(),2,TimeUnit.MINUTES);
//    	StringBuilder buff = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?");
//    	buff.append("appid=").append(ConfigSetting.getProperty("weixin.appid"));
//		//buff.append("&redirect_uri=").append(getUrl(req, "callback")).append("?pageUrl="+pageUrl);
//    	buff.append("&redirect_uri="+ConfigSetting.getProperty("weixin.jsurl")+ConfigSetting.getProperty("server.context-path")+"/rest/oauth/access/wxcallback");
//		buff.append("&response_type=code");
//		buff.append("&scope=snsapi_base");
//		buff.append("&state="+state);
//		buff.append("#wechat_redirect");
//		log.info("===========oauth=====weixin========="+buff.toString());
//		res.sendRedirect(buff.toString());
//    }
//    
//    
//    @RequestMapping(value = "/wxcallback")
//    public void wxcallback(HttpServletRequest req, HttpServletResponse res,@RequestParam(required = false) String state,@RequestParam String code) throws Exception {
//    	log.info("===========wxcallback=============="+code);
//    	log.info("===========wxcallback=====state========="+state);
////    	 String page = stringRedisTemplate.opsForValue().get(state);
//    	JSONObject pagejson = JSON.parseObject("") ;
//    	String pageUrl = pagejson.getString("pageUrl") ;
//    	String isCheckSub = pagejson.getString("isCheckSub");
//    	Map<String, String> param = new HashMap<String, String>();
//		param.put("appid", ConfigSetting.getProperty("weixin.appid"));
//		param.put("secret", ConfigSetting.getProperty("weixin.secret"));
//		param.put("code", code);
//		param.put("grant_type", "authorization_code");
//		String json = HttpClientUtil.doPost("https://api.weixin.qq.com/sns/oauth2/access_token");
//		log.info("===========请求微信返回=============="+json);
//		JSONObject jsonObject = JSON.parseObject(json) ;
//		String openid = "" ;
//		String tradeKey = "" ;
//		String redirectUrl = "" ;
//		boolean bind = false ;
//		if (jsonObject.get("openid")!=null) {
//			openid = jsonObject.get("openid").toString() ;
//			tradeKey = "GATEWAY_WX_OAUTH" + code ;
//			log.info("===========tradeKey=============="+tradeKey);
////			stringRedisTemplate.opsForValue().set(tradeKey, openid,5,TimeUnit.MINUTES);
//			if (pageUrl.contains("?")) {
//				redirectUrl = pageUrl+"&openId="+openid+"&tradeKey="+tradeKey ;
//			}else{
//				redirectUrl = pageUrl+"?openId="+openid+"&tradeKey="+tradeKey ;
//			}
//			if (bind) {
//				redirectUrl +="&pageUrl="+pagejson.getString("pageUrl") ;
//			}
//			String subKey ="wx_subscribe_"+openid;
//			//校验需要是否关注 
//				res.sendRedirect(redirectUrl);
//		}
//    }
    
    
     /**
      * 获取关注、取消事件  
      * @param req
      * @param res
      * @throws Exception
      */
/*    @RequestMapping(value = "/subEvent")
    public void subEvent(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	Map result = req.getParameterMap();
    	log.info("subEvent|关注事件获取验证信息:{}",JSON.toJSONString(result));
    	String[] signList = (String[]) result.get("echostr");
    	String echostr =null;
    	if(null!=signList){
    	  echostr =signList[0];
      	 res.getOutputStream().write(echostr.getBytes());
    	}
    	String token="jstpay";
    	String  msgReslut = getRequestBody(req.getInputStream());
    	if(StringUtils.isNoneEmpty(msgReslut)){
    		JSONObject json =XmlUtil.xml2JSON(msgReslut.getBytes());
        	
        	log.info("subEvent|关注事件获取报文:{}",JSON.toJSONString(json));
        	Map jsonMap =(Map) json.get("xml");
        	List fromUserName =(List)jsonMap.get("FromUserName");
        	List createTime = (List)jsonMap.get("CreateTime");
        	List event = (List)jsonMap.get("Event");
        	String ent =null;
        	if(CollectionUtils.isNotEmpty(event)){
        		ent= (String)event.get(0);
        	}
        	String redisKey =fromUserName.get(0)+"_"+createTime.get(0);
        	String subKey ="wx_subscribe_"+fromUserName.get(0);
        	String scanValue =stringRedisTemplate.opsForValue().get(subKey);//扫码关注 
        	log.info("扫码key：{},value:{}",subKey,scanValue);
        	String resetVaue =stringRedisTemplate.opsForValue().get(redisKey);// 防重处理
        	if(StringUtils.isBlank(resetVaue)&&"subscribe".equals(ent)&&StringUtils.isNotEmpty(scanValue)){	
        		stringRedisTemplate.opsForValue().set(redisKey, "sub");
        		//发消息 
        		
        		StringBuffer message =new StringBuffer();; 
        		message.append("终于等到您 ~~ 欢迎关注捷好用公众号！\r\n");
        		message.append("您停车，我优惠。\r\n");
        		message.append("首次使用一网通支付停车费享优惠！\r\n").append("");
        		
        		log.info("开始请求微信接口，"+url+"，入参：{}",JSON.toJSONString(msg));
                HttpUtil.postAsJson(url, JSON.toJSONString(msg), 5000, null);
        	}
        	if(StringUtils.isBlank(resetVaue)&&"unsubscribe".equals(event)){	
        		stringRedisTemplate.opsForValue().set(redisKey, "unsub");
        		//发消息 
        		
        	}
    	}
    }*/
    
    private String getRequestBody(InputStream stream) {
        String line = "";
        StringBuilder body = new StringBuilder();
        int counter = 0;

        // 读取POST提交的数据内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            while ((line = reader.readLine()) != null) {
                if (counter > 0) {
                    body.append("rn");
                }
                body.append(line);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        log.info("getRequestBody|微信发送事件报文:{}",JSON.toJSONString(body));
        return body.toString();
    }
    

	
	
	
	    
}
