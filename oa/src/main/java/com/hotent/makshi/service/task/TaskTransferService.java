package com.hotent.makshi.service.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.task.TaskTransferDao;
import com.hotent.makshi.model.task.TaskTransfer;

@Service
public class TaskTransferService extends BaseService<TaskTransfer> {
	@Resource
	private TaskTransferDao dao;	
	public TaskTransferService(){	
		
	}
	
	@Override
	protected IEntityDao<TaskTransfer, Long> getEntityDao() {
		return dao;
	}
	
}
