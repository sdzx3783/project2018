package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.task.TaskDelay;

@Repository
public class TaskDelayDao extends BaseDao<TaskDelay> {

	@Override
	public Class<?> getEntityClass() {
		return TaskDelay.class;
	}

}
