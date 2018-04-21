package com.hotent.makshi.service.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.task.TaskLogDao;
import com.hotent.makshi.model.task.TaskLog;

@Service
public class TaskLogService extends BaseService<TaskLog> {
	@Resource
	private TaskLogDao dao;	
	public TaskLogService(){	
		
	}
	
	@Override
	protected IEntityDao<TaskLog, Long> getEntityDao() {
		return dao;
	}
	
}
