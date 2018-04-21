
package com.hotent.makshi.dao.appPush;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.appPush.AppPushJob;

@Repository
public class AppPushJobDao extends BaseDao<AppPushJob>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AppPushJob.class;
	}

}