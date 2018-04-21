package com.hotent.platform.service.system.impl.curuser;

import java.util.ArrayList;
import java.util.List;

import com.hotent.core.model.CurrentUser;
import com.hotent.platform.service.system.ICurUserService;

/**
 * 用户授权
 *
 */
public class CurUserService implements ICurUserService {

	@Override
	public List<Long> getByCurUser(CurrentUser currentUser) {
		List<Long> list=new ArrayList<Long>();
		list.add(currentUser.getUserId());
		return list;
	}

	@Override
	public String getKey() {
		return "user";
	}

	@Override
	public String getTitle() {
		return "用户授权";
	}

	
	
}
