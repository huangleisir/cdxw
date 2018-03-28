package com.jst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jst.bean.Address;
import com.jst.bean.Food;
import com.jst.bean.FoodType;
import com.jst.bean.Order;
import com.jst.bean.OrderDetail;
import com.jst.bean.PayBean;
import com.jst.bean.SysParams;
import com.jst.bo.BaseBo;
import com.jst.common.util.HttpClientUtil;
import com.jst.common.util.MD5Util;
import com.jst.config.ConfigSetting;
import com.jst.framework.common.bean.Result;
import com.jst.prodution.util.EmptyUtil;
import com.jst.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	protected Logger  log = LoggerFactory.getLogger(getClass());
	@Autowired
	BookService bookService;
	
	 	@RequestMapping(value = "/foodTypeList", method = RequestMethod.POST)
	    @ResponseBody
	    public Result foodTypeList(BaseBo baseBo) {
	        Result result = new Result();
	        FoodType foodType = new FoodType();
	        foodType.setStatus(1);
	        List<FoodType> list = bookService.foodTypeList(foodType);
	        result.setData(list);
	        return result;
	    }
	 	
	 	@RequestMapping(value = "/getFoodListByTypeId", method = RequestMethod.POST)
	    @ResponseBody
	    public Result getFoodListByTypeId(@RequestBody Food food) {
	 		log.info("入参："+JSON.toJSONString(food));
	        Result result = new Result();
	        List<Food> list = bookService.getFoodListByTypeId(food);
	        result.setData(list);
	        return result;
	    }
	 	
	 	//保存地址
	 	@RequestMapping(value = "/saveAddr", method = RequestMethod.POST)
	    @ResponseBody
	    public Result saveAddr(@RequestBody Address address) {
	 		log.info("入参："+JSON.toJSONString(address));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(address.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        if(EmptyUtil.isEmpty(address.getAddr()) ){
	        	result.setCode("201");
	        	result.setMsg("地址不能为空");
	        	return result;
	        }
	        if(address.getAddr().length()>50){
	        	result.setCode("201");
		        result.setMsg("地址超过最大长度限制");
		        return result;
	        }
	        	 
	        if(EmptyUtil.isEmpty(address.getName())){
	        	result.setCode("201");
	        	result.setMsg("联系人不能为空");
	        	return result;
	        }
	        if(address.getName().length()>8){
	        	result.setCode("201");
		        result.setMsg("联系人超过最大长度限制");
		        return result;
	        }
	        String mobile = address.getMobile();
	        if(EmptyUtil.isEmpty(mobile) ){
	        	result.setCode("201");
	        	result.setMsg("手机号码不能为空");	
	        	return result;
	        }
	        if(mobile.length()!=11 || !mobile.startsWith("1")){
	        	result.setCode("201");
		        result.setMsg("手机号码格式不正确");	
		        return result;
	        }

	        String addrId = bookService.saveAddr(address);
	        log.info("保存收货地址返回addrId："+addrId);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("addrId", addrId);
	        result.setData(map);
	        return result;
	    }
	 	
	 	//保存订单
	 	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	    @ResponseBody
	    public Result saveOrder(@RequestBody Order order) {
	 		log.info("入参："+JSON.toJSONString(order));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(order.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        if(order.getMark().length()>50){
	        	result.setCode("201");
	        	result.setMsg("备注超过最大长度限制");
	        	return result;
	        }
	        //校验food的状态是否为1(正常)
	        List<OrderDetail> foodList = order.getFoodList();
	        for (OrderDetail orderDetail : foodList) {
	        	Food food = new Food();
	        	food = bookService.findByFoodId(orderDetail.getFoodId());
	        	//0删除1正常2售罄
	        	if(food ==null || food.getStatus() == 0) {
	        		result.setCode("201");
		        	result.setMsg("“"+food.getFoodName()+"”不存在");
		        	return result;
	        	}
	        	if(food ==null || food.getStatus() == 2) {
	        		result.setCode("201");
		        	result.setMsg("“"+food.getFoodName()+"”卖完了");
		        	return result;
	        	}
			}
	        String orderId = bookService.saveOrder(order);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("orderId", orderId);
	        result.setData(map);
	        log.info("保存订单返回订单编号："+orderId);
	        return result;
	    }
	 	
	 	//去结算时判断菜品是否正确
	 	@RequestMapping(value = "/getFoodState", method = RequestMethod.POST)
	    @ResponseBody
	    public Result getFoodState(@RequestBody List<Food> foodList){
	 		log.info("入参："+JSON.toJSONString(foodList));
	        Result result = new Result();
	        for (Food food : foodList) {
	        	food = bookService.findByFoodId(food.getFoodId());
		        //0删除1正常2售罄
	        	if(food ==null || food.getStatus() == 0) {
	        		result.setCode("201");
		        	result.setMsg("“"+food.getFoodName()+"”不存在");
		        	return result;
	        	}
	        	if(food ==null || food.getStatus() == 2) {
	        		result.setCode("201");
		        	result.setMsg("“"+food.getFoodName()+"”卖完了");
		        	return result;
	        	}
			}	        
	        return result;
	 	}
	 	
	 	//更新地址
	 	@RequestMapping(value = "/updateAddr", method = RequestMethod.POST)
	    @ResponseBody
	    public Result updateAddr(@RequestBody Address address) {
	 		log.info("入参："+JSON.toJSONString(address));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(address.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        if(EmptyUtil.isEmpty(address.getAddr()) ){
	        	result.setCode("201");
	        	result.setMsg("地址不能为空");
	        	return result;
	        }
	        if(address.getAddr().length()>50){
	        	result.setCode("201");
		        result.setMsg("地址超过最大长度限制");
		        return result;
	        }
	        	 
	        if(EmptyUtil.isEmpty(address.getName())){
	        	result.setCode("201");
	        	result.setMsg("联系人不能为空");
	        	return result;
	        }
	        if(address.getName().length()>8){
	        	result.setCode("201");
		        result.setMsg("联系人超过最大长度限制");
		        return result;
	        }
	        String mobile = address.getMobile();
	        if(EmptyUtil.isEmpty(mobile) ){
	        	result.setCode("201");
	        	result.setMsg("手机号码不能为空");	
	        	return result;
	        }
	        if(mobile.length()!=11 || !mobile.startsWith("1")){
	        	result.setCode("201");
		        result.setMsg("手机号码格式不正确");	
		        return result;
	        }
	        int i = bookService.updateAddr(address);
	        log.info("更新地址结果："+i);
	        return result;
	    }
	 	
	 	//查询地址，传入openId查询地址列表，传入openId和id查询一条地址
	 	@RequestMapping(value = "/selectAddrByOpenId", method = RequestMethod.POST)
	    @ResponseBody
	    public Result selectAddrByOpenId(@RequestBody Address address) {
	 		log.info("入参："+JSON.toJSONString(address));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(address.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        List<Address> list = bookService.getAddrListByOpenId(address);
	        result.setData(list);
	        return result;
	    }
	 	
	 	@RequestMapping(value = "/getOrderListByOpenId", method = RequestMethod.POST)
	    @ResponseBody
	    public Result getOrderListByOpenId(@RequestBody Order order) {
	 		log.info("入参："+JSON.toJSONString(order));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(order.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        List<Order> list = bookService.getOrderListByOpenId(order);
	        result.setData(list);
	        return result;
	    }
	 	
	 	@RequestMapping(value = "/getOrderDetailByOrderId", method = RequestMethod.POST)
	    @ResponseBody
	    public Result getOrderDetailByOrderId(@RequestBody Order order) {
	 		log.info("入参："+JSON.toJSONString(order));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(order.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        if(EmptyUtil.isEmpty(order.getId())) {
	        	result.setCode("201");
	        	result.setMsg("id不可为空");
	        	return result;
	        }
	        List<Order> list = bookService.getOrderDetailByOpenId(order);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("orderList", list);
	        map.put("time", System.currentTimeMillis());
	        result.setData(map);
	        return result;
	    }
	 	
	 	
	 	@RequestMapping(value = "/getSysParams", method = RequestMethod.POST)
	    @ResponseBody
	    public Result getSysParams() {
	 		log.info("获取系统配置参数");
	        Result result = new Result();
	        /*if(EmptyUtil.isEmpty()) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }*/
//	        List<Order> list = bookService.getOrderListByOpenId(order);
//	        result.setData(list);
	        return result;
	    }
	 	
	 	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	    @ResponseBody
	    public Result pay(@RequestBody PayBean payBean) {
	 		log.info("入参："+JSON.toJSONString(payBean));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(payBean.getOpenId())) {
	        	result.setCode("201");
	        	result.setMsg("openId不可为空");
	        	return result;
	        }
	        if(EmptyUtil.isEmpty(payBean.getId())) {
	        	result.setCode("201");
	        	result.setMsg("订单ID不可为空");
	        	return result;
	        }
	        String cardNo = payBean.getCardNo();
	        if(EmptyUtil.isEmpty(cardNo)) {
	        	result.setCode("201");
	        	result.setMsg("卡号不能为空");
	        	return result;
	        } 
	        if(cardNo.length()!=19 || !cardNo.matches("^[0-9]*$")){
	        	result.setCode("201");
	        	result.setMsg("卡号格式不正确");
	        	return result;
	        }
	        if(EmptyUtil.isEmpty(payBean.getPwd())) {//510814
	        	result.setCode("201");
	        	result.setMsg("密码不可为空");
	        	return result;
	        }
	        
	        //根据订单ID查询订单支付金额
	        log.info("----根据订单ID查询订单详情-------");
	        List<Order> list = bookService.getOrderListByOpenId(payBean);
	        log.info("----根据订单ID查询订单支付金额-详情"+JSON.toJSONString(list));
	        if(EmptyUtil.isEmpty(list)) {
	        	log.info("---查询不到该订单的详情，拒绝支付----");
	        	result.setCode("201");
	        	result.setMsg("查询不到该订单的详情");
	        	return result;
	        }
	        Order order = list.get(0);
	        //判断该订单是否已过期
	        if(2==order.getPayStatus()){
	        	log.info("---该订单已过期，拒绝支付----");
	        	result.setCode("201");
	        	result.setMsg("该订单已过期");
	        	return result;
	        }
	        //将状态改为3  支付中
/*	        order.setPayStatus(3);
	        bookService.updateOrder(order);*/
	        
			String stringA ="amount="+order.getOrderAmount()+"&cardNo="+payBean.getCardNo()
	        +"&nonceStr=ibuaiVcKdpRxkhJA&outTradeNo="+
					payBean.getId()+"&password="+payBean.getPwd();
			String signTemp=stringA+"&key=B83B3538A4431A4E8950DA741D6BAB1C";
			String sign=MD5Util.getMD5Str2(signTemp).toUpperCase();
			String url=ConfigSetting.getProperty("preCardPayUrl")+"?"
			 		+stringA+"&sign="+sign;
			log.info("~~~~~~~~~~~url~~~~~~~~~~"+url);
			long time1 = System.currentTimeMillis();
	 			String resultStr = HttpClientUtil.doPost(url);
	 		log.info("调用预付卡系统支付耗时："+(System.currentTimeMillis()-time1)+"毫秒");
	 		log.info("调用预付卡系统支付返回结果："+resultStr);
			JSONObject resObj = JSONObject.parseObject(resultStr);
			String dataStr = "";
			if(!"00".equals(resObj.getString("errCode"))){
				log.info(resObj.getString("errMsg"));
				result.setCode("201");
	        	result.setMsg(resObj.getString("errMsg"));
	        	return result;
			}else {
				dataStr = resObj.getString("data");
				JSONObject dataObj = JSONObject.parseObject(dataStr);
				if(!"00".equals(dataObj.getString("resultCode"))) {
					result.setCode("201");
		        	result.setMsg(dataObj.getString("resultDesc"));
		        	return result;
				}else {
					result.setCode("200");
		        	result.setMsg("支付成功");
		        	//支付成功，支付状态改为1
		        	order.setPayStatus(1);
		        	//设置配送状态为1 已确认
		        	order.setSendStatus(1);
		        	bookService.updateOrder(order);
		        	return result;
				}
				
	 		
			
			}
//	        List<Order> list = bookService.getOrderListByOpenId(payBean);
//	        result.setData(list);
	    }
	 	
	 	/**
	 	 * 查询配送费详情
	 	 */
	 	@RequestMapping(value = "/querySysParams", method = RequestMethod.POST)
	    @ResponseBody
	 	public Result querySysParams() {
	 		Result result = new Result();
	 		SysParams sysParams = bookService.findSysParams();
	 		log.info("-------------配送费详情----------"+JSON.toJSONString(sysParams));
	 		result.setData(sysParams);
	 		return result;
	 	}
	 	
	 	/**
	 	 * 根据ID删除地址
	 	 */
	 	@RequestMapping(value = "/deleteAddrById", method = RequestMethod.POST)
	    @ResponseBody
	    public Result deleteAddrById(@RequestBody Address address) {
	 		log.info("入参："+JSON.toJSONString(address));
	        Result result = new Result();
	        if(EmptyUtil.isEmpty(address.getId())) {
	        	result.setCode("201");
	        	result.setMsg("id不可为空");
	        	return result;
	        }
	        int i = bookService.deleteAddrById(address);
	        log.info("--------------删除地址：--------------"+i);
	        return result;
	    }

}
