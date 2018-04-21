package com.hotent.platform.service.bpm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.service.bpm.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {
	
	@Resource
	TaskDao taskDao;
	

	/**
	 * 获取手机端任务。
	 */
	@Override
	public List getMyMobileTasks(QueryFilter queryFilter) {
		Long userId=ContextUtil.getCurrentUserId();
		List  taskList= taskDao.getMyMobileTasks(userId, queryFilter);
		return taskList;
	}

}
