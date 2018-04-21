package com.hotent.platform.webservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.webservice.exception.ApiExcetpion;

/**
 * 用户登录状态校验
 * @author martic
 *
 */
public class UserLoginUtil {
	private static Map<String,Map<String, Object>> userLoginMap=new HashMap<>();
	//登录accessToken的过期时间 
	private static Integer accessTokenTime=3;//3天
	public static Map<String, Map<String, Object>> getUserLoginMap() {
		return userLoginMap;
	}

	public static void setUserLoginMap(Map<String, Map<String, Object>> userLoginMap) {
		UserLoginUtil.userLoginMap = userLoginMap;
	}
	

	public static Integer getAccessTokenTime() {
		return accessTokenTime;
	}


	static{
		Map<String , Object> map=new HashMap<>();
		map.put("accessToken", "1602718161027");
		//accessToken的过期时间
		map.put("accessTokenTime", DateUtils.format(DateUtils.addDay(new Date(), accessTokenTime),DateUtils.D_FORMAT));
//		getNow(DateUtils.D_FORMAT)
	//	System.out.println("token:"+map.get("accessToken"));
		UserLoginUtil.getUserLoginMap().put("Log_App_admin", map);
	}
	public static boolean checkLogin(String account,String accessToken) {
		boolean result=false;
		if(StringUtils.isEmpty(account)||StringUtils.isEmpty(accessToken)){
			throw new ApiExcetpion(ReturnCode.NOT_LOGIN,"userId或登陆凭证为空");
		}
		Map<String, Map<String, Object>> map = UserLoginUtil.getUserLoginMap();
		Map<String, Object> accountMap=null;
		if(null!=map){
			accountMap=map.get("Log_App_"+account);
			if(null!=accountMap&&null!=accountMap.get("accessToken")&&null!=accountMap.get("accessTokenTime")){
				return accountMap.get("accessToken").equals(accessToken)&&Integer.valueOf((String) accountMap.get("accessTokenTime"))>Integer.valueOf(DateUtils.getNow(DateUtils.D_FORMAT));
			}
		}
		return result;
	}
}
