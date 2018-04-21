/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.UserFormal;
import com.hotent.makshi.service.hr.UserFormalService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

/**
 * @author cesc
 *转正关联
 */
@Component("userFormalFetcher")
public class FormalFetcher implements IFetcher {
	@Resource
	private UserFormalService userFormalService;
	@Resource
	private SysUserService userService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			UserFormal userFormal = userFormalService.getById(Long.parseLong(businessKey));
			if(userFormal!=null){
				SysUser user = userService.getById(Long.parseLong(userFormal.getUser_id()));
				if(user!=null){
					user.setUserStatus("正式员工");
					user.setFormalDate(userFormal.getFormalDate());
					userService.update(user);
				}
				
			}
			
		}
		
	}

}
