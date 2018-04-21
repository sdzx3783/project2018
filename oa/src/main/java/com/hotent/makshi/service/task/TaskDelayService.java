package com.hotent.makshi.service.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.task.TaskDelayDao;
import com.hotent.makshi.model.task.TaskDelay;

@Service
public class TaskDelayService extends BaseService<TaskDelay> {
	@Resource
	private TaskDelayDao dao;	
	public TaskDelayService(){	
		
	}
	
	@Override
	protected IEntityDao<TaskDelay, Long> getEntityDao() {
		return dao;
	}
	
}
