
package com.hotent.makshi.dao.userinfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.userinfo.UserInfomation;

@Repository
public class UserInfomationDao extends BaseDao<UserInfomation>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserInfomation.class;
	}

	public void updateLeaveVacation(Long userId, Double leaveVacation) {
		Map param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("leaveVacation", leaveVacation);
	    this.getBySqlKey("updateLeaveVacation",param);
	}

}