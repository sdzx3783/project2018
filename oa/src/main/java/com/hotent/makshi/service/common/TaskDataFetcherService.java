package com.hotent.makshi.service.common;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.common.TaskDataFetcherDao;
import com.hotent.makshi.model.common.TaskDataFetcher;


@Service
public class TaskDataFetcherService extends BaseService<TaskDataFetcher>
{
	@Resource
	private TaskDataFetcherDao dao;
	
	public TaskDataFetcherService()
	{
	}
	
	@Override
	protected IEntityDao<TaskDataFetcher,Long> getEntityDao() 
	{
		return dao;
	}

}
