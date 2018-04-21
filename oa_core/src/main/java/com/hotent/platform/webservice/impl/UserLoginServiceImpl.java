package com.hotent.platform.webservice.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.web.listener.UserSessionListener;
import com.hotent.platform.webservice.api.UserLoginService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.util.ReturnCode;
import com.hotent.platform.webservice.util.UserLoginUtil;

import net.sf.json.JSONObject;

public class UserLoginServiceImpl implements UserLoginService {

	@Resource
	SysUserService sysUserService;
	@Override
	public String userLogin(String account) {
		BaseRuelt result = new BaseRuelt();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 验证码校验
		
		//登录校验

		//因为页面jsp也用到validCodeEnabled这个写死的变量
//		String validCodeEnabled = RequestUtil.getString(request, "validCodeEnabled", "false");
		
		UserSessionListener.getOnLineUsers();
		//生成token并保存登录态
		String accessToken = request.getSession().getId();
		if (accessToken.indexOf(".") != -1) {
			accessToken = accessToken.substring(0, accessToken.indexOf("."));
		}
		SysUser user=sysUserService.getByAccount(account);
		if(user==null){
			throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在");
		}
		JSONObject map=new JSONObject();
		map.put("accessToken", accessToken);
		map.put("account", user.getAccount());
		map.put("accessTokenTime", DateUtils.format(DateUtils.addDay(new Date(), UserLoginUtil.getAccessTokenTime()),DateUtils.D_FORMAT));
		UserLoginUtil.getUserLoginMap().put("Log_App_"+account, map);
	//	System.out.println("----------------accessToken:"+map.get("accessToken"));
		result.setResultMap(map, ReturnCode.SUCCESS);
		return result.getResultMap().toString();
	}

}
