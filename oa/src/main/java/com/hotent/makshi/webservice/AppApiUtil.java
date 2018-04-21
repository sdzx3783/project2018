package com.hotent.makshi.webservice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hotent.core.api.org.ICurrentContext;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.util.ReturnCode;

public class AppApiUtil {
	@Resource
	private SysUserService sysUserService;
	public  SysUser getCurrentUser(HttpServletRequest request)
	{
		String userId = RequestUtil.getString(request, "userId");
		SysUser curUser =null;
		if(StringUtils.isNotEmpty(userId)){
			curUser = sysUserService.getById(Long.valueOf(userId));
		}
		if(curUser==null){
			curUser=sysUserService.getByAccount(RequestUtil.getString(request, "account"));
		}
		return curUser;
	}
}
