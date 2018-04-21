package com.hotent.platform.service.system.impl.handler;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.IOrgHandler;

public class OrgHandlerSelf  implements IOrgHandler {

	@Override
	public SysOrg getByType(String type) {
		SysOrg sysOrg=(SysOrg) ContextUtil.getCurrentOrg();
		return sysOrg;
	}

}
