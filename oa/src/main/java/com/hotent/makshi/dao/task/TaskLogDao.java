package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.task.TaskLog;

@Repository
public class TaskLogDao extends BaseDao<TaskLog> {

	@Override
	public Class<?> getEntityClass() {
		return TaskLog.class;
	}

}
