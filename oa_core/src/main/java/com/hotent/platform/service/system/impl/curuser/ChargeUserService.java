package com.hotent.platform.service.system.impl.curuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.core.model.CurrentUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.ICurUserService;
import com.hotent.platform.service.system.UserPositionService;

/**
 * 获取某人负责的组织。
 * @author ray
 *
 */
public class ChargeUserService implements ICurUserService  {
	
	@Resource
	private UserPositionService userPositionService;

	@Override
	public List<Long> getByCurUser(CurrentUser currentUser) {
		List<Long> orgIds=new ArrayList<Long>();
		List<UserPosition> userOrgs= userPositionService.getChargeOrgByUserId(currentUser.getUserId());
		for(UserPosition userOrg:userOrgs){
			orgIds.add(userOrg.getOrgId());
		}
		return orgIds;
	}

	@Override
	public String getKey() {
		return "orgMgr";
	}

	@Override
	public String getTitle() {
		return "组织负责人";
	}

}
