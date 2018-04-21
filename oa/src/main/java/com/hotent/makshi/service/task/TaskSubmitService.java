package com.hotent.makshi.service.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.task.TaskSubmitDao;
import com.hotent.makshi.model.task.TaskSubmit;

@Service
public class TaskSubmitService extends BaseService<TaskSubmit> {
	@Resource
	private TaskSubmitDao dao;	
	public TaskSubmitService(){	
		
	}
	
	@Override
	protected IEntityDao<TaskSubmit, Long> getEntityDao() {
		return dao;
	}
	
	public List<TaskSubmit> getSubmitByTaskId(Integer taskId){
		List<TaskSubmit> list = dao.getBySqlKey("getSubmitByTaskId", taskId);
		return list;
	}
	
}
