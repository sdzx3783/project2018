
package com.hotent.makshi.dao.appPush;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.appPush.AppPushUser;

@Repository
public class AppPushUserDao extends BaseDao<AppPushUser>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AppPushUser.class;
	}

}