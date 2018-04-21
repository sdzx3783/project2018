package com.hotent.platform.service.bpm;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.hotent.core.web.query.QueryFilter;

public interface ITaskService {
	
	/**
	 * 获取我的手机端任务。
	 * @param queryFilter
	 * @return
	 */
	List getMyMobileTasks(QueryFilter queryFilter);

}
