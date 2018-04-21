/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.UserResignation;
import com.hotent.makshi.service.hr.UserResignationService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
import com.maishi.component.util.DateUtil;

/**
 * @author cesc
 *离职关联
 */
@Component("resignationFetcher")
public class ResignationFetcher implements IFetcher {
	@Resource
	private UserResignationService userResignationService;
	@Resource
	private SysUserService userService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			UserResignation resignation = userResignationService.getById(Long.parseLong(businessKey));
			if(resignation!=null){
				SysUser user = userService.getById(Long.parseLong(resignation.getUser_id()));
				if(user!=null){
					user.setUserStatus("离职");
					user.setResignationDate(DateUtil.parseDbDate(resignation.getTerminame_time()));
				//	user.setIsLock((short) 1);
					userService.update(user);
				}
				
			}
			
		}
		
	}

}
