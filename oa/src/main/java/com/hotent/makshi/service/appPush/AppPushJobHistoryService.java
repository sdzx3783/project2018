package com.hotent.makshi.service.appPush;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.appPush.AppPushJobHistoryDao;
import com.hotent.makshi.model.appPush.AppPushJobHistory;


@Service
public class AppPushJobHistoryService extends BaseService<AppPushJobHistory>
{
	@Resource
	private AppPushJobHistoryDao dao;
	
	public AppPushJobHistoryService()
	{
	}
	
	@Override
	protected IEntityDao<AppPushJobHistory,Long> getEntityDao() 
	{
		return dao;
	}
}
