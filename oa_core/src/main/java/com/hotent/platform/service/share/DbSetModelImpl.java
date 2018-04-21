package com.hotent.platform.service.share;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IDbSetModel;
import com.hotent.core.model.BaseModel;

public class DbSetModelImpl implements IDbSetModel {

	@Override
	public void setAdd(BaseModel model) {
		ISysUser user=ContextUtil.getCurrentUser();
		if(user!=null){
			model.setCreateBy(user.getUserId());
		}
		
	}

	@Override
	public void setUpd(BaseModel model) {
		ISysUser user=ContextUtil.getCurrentUser();
		if(user!=null){
			model.setUpdateBy(user.getUserId());
		}
	}

}
