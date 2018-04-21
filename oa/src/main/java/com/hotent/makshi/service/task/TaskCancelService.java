package com.hotent.makshi.service.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.task.TaskCancelDao;
import com.hotent.makshi.model.task.TaskCancel;

@Service
public class TaskCancelService extends BaseService<TaskCancel> {
	@Resource
	private TaskCancelDao dao;	
	public TaskCancelService(){	
		
	}
	
	@Override
	protected IEntityDao<TaskCancel, Long> getEntityDao() {
		return dao;
	}
	
}
