package com.jst.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class MD5Util {
	private static Logger log = Logger.getLogger(MD5Util.class);
	
	   public static String getMD5Str2(String str) {  
	        MessageDigest messageDigest = null;  
	        try {  
	            messageDigest = MessageDigest.getInstance("MD5");  
	            messageDigest.reset();  
	            messageDigest.update(str.getBytes("UTF-8"));  
	        } catch (NoSuchAlgorithmException e) {  
	        	e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	  
	        byte[] byteArray = messageDigest.digest();  
	  
	        StringBuffer md5StrBuff = new StringBuffer();  
	  
	        for (int i = 0; i < byteArray.length; i++) {              
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
	            else  
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
	        }  
	  
	        return md5StrBuff.toString();  
	    }
       public static String getMD5Str(String str,String key) {  
           String sign=""; 
           try {  
               String[] aa = StringUtils.split(str, "&");
               Arrays.sort(aa);   
               
               List<String> paramPairs = new ArrayList<String>();
               for (int i = 0; i < aa.length; i++) {
                   //method参数不参与签名的生成

                   paramPairs.add(aa[i]);
                   if (aa[i].contains("token=")) {
                       log.info("----"+aa[i]);
                       String reUrl2 =aa[i];
                       String token = reUrl2.substring(reUrl2.indexOf("token=")+6,reUrl2.length());
                       log.info("前端传过来的sign:"+token);
                   }
                   
               }
               //每个键值对用&连接：key1=value1&key2=value2&key3=value3
               String temp = StringUtils.join(paramPairs, "");
               temp = temp + "key=" +key;
               log.info("--加密前------------"+temp);
               
                
                 sign=getMD5Str2(temp);
               
               log.info("++++++"+temp); 
           } catch (Exception e) {  
               e.printStackTrace();
           }
          
           return sign;  
       }
       public static String getToken(String str) {  
           String token = "";
           try {  
               String[] aa = StringUtils.split(str, "&");
               Arrays.sort(aa);   
               
               List<String> paramPairs = new ArrayList<String>();
               for (int i = 0; i < aa.length; i++) {
                   //method参数不参与签名的生成

                   paramPairs.add(aa[i]);
                   if (aa[i].contains("token=")) {
                       log.info("----"+aa[i]);
                       String reUrl2 =aa[i];
                        token = reUrl2.substring(reUrl2.indexOf("token=")+6,reUrl2.length());
                       log.info("前端传过来的sign:"+token);
                   }
                   
               }

           } catch (Exception e) {  
               e.printStackTrace();
           }
          
           return token;  
       }
       

	public static void main(String[] args) {
		System.out.println("--------"+getMD5Str2("appVersion=100deviceType=2payChannelId=JYFpayType=H5rechargeAmount=2017102420501rechargeMobile=1510819328774terminalType=Mtoken=6459a0fa9bf5f801039c8dac4f1a5dbfkey=7be1f142de741d0ce70fb978bf85cd3b"));
		// TODO Auto-generated method stub
		//获取查询指定城市影院列表接口的sign示例
//terminalType=Mtoken=83f58e9269f217314f1a39e9cfbbf727a8a32644c3358861d792dff3d9d1a5a6userId=9657061068362784768usrLgName=9657061068362784768key=123456
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", "83f58e9269f217314f1a39e9cfbbf727a8a32644c3358861d792dff3d9d1a5a6");
		params.put("terminalType", "M");
		//params.put("source", 1);
		//params.put("mobile", "15170046120");  
		
		params.put("usrLgName", "9657061068362784768");
		params.put("userId", "9657061068362784768");
		//params.put("seatCodes", "4010");
		//params.put("channelOrderCode", "15123110121359441223");
		//渠道号
		//params.put("channelCode", "0004");		
		//通信密钥
		getSign(params, "123456");
		
		String[] aa = StringUtils.split("token=bbecf2291a3979db79f416e950408446de528ca885766168991162b5f17891ce&terminalType=M&usrLgName=9657061068362784768&sign=22b046009fbf81cfce26693dc2ca71bf", "&");
        for (int i = 0; i < aa.length; i++) {
            //method参数不参与签名的生成
            log.info(aa[i]);
            if (aa[i].contains("token=")) {
                log.info("----"+aa[i]);
                String reUrl2 =aa[i];
                String reUrl5 = reUrl2.substring(reUrl2.indexOf("token=")+6,reUrl2.length());
                log.info("前端传过来的sign:"+reUrl5);
            }
            //连接key和value：key=value
         //   paramPairs.add(keys[i] + "=" + params.get(keys[i]));
        }
		log.info(aa.toString());
	}
	//获取sign方法
	public static String getSign(Map<String, Object> params, String key){
				Object[] keys = params.keySet().toArray();
				//字段排序
				Arrays.sort(keys);			
				List<String> paramPairs = new ArrayList<String>();
				for (int i = 0; i < keys.length; i++) {
					//method参数不参与签名的生成
					//log.info(params.get(keys[i])+"---"+keys[i]);
					if (keys[i].equals("method")) {
						continue;
					}
					//连接key和value：key=value
					paramPairs.add(keys[i] + "=" + params.get(keys[i]));
				}
				//每个键值对用&连接：key1=value1&key2=value2&key3=value3
				String temp = StringUtils.join(paramPairs, "");
				//String temp = paramPairs.toString();
				
				//对字符串进行URLl编码
/*				try {
					temp = URLEncoder.encode(temp, "UTF-8");
				}
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}*/
				//参数串首、尾部加上通讯密钥
		
				temp = temp + "key=" +key;
				log.info("--加密前------------"+temp);
				String sign=getMD5Str2(temp);
				
				//String sign = DigestUtils.md5Hex(temp);
				//URLDecoder.decode(sign);
				sign=change(sign);
				log.info("-加密后sign-------------"+sign);
				//log.info(sign+"---sign:");
				return sign;
			}
			
			
			public static String change(String s){
				
				StringBuffer dd=new StringBuffer(s);
				 char c;
			    for(int i=0;i<s.length();i++)
			    {
			     c=s.charAt(i);
			     if(c>='A'&&c<='Z')
			      dd.replace(i, i+1,(char)((int)s.charAt(i)+32)+"");
			    }
			    s=dd.toString();	
				return s;			
			}
			
}
