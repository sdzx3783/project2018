package com.hotent.platform.service.system.impl.curuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.core.model.CurrentUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.ICurUserService;
import com.hotent.platform.service.system.SysOrgService;

public class OrgUserService implements ICurUserService {
	
	@Resource
	private SysOrgService sysOrgService;

	@Override
	public List<Long> getByCurUser(CurrentUser currentUser) {
		List<SysOrg> orgs= sysOrgService.getOrgsByUserId(currentUser.getUserId());
		List<Long> list=new ArrayList<Long>();
		for(SysOrg org:orgs){
			list.add(org.getOrgId());
		}
		return list;
	}

	@Override
	public String getKey() {
		return "org";
	}

	@Override
	public String getTitle() {
		return "组织授权（本层级）";
		
	}

	

}
