package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.task.TaskSubmit;

@Repository
public class TaskSubmitDao extends BaseDao<TaskSubmit> {

	@Override
	public Class<?> getEntityClass() {
		return TaskSubmit.class;
	}

}
