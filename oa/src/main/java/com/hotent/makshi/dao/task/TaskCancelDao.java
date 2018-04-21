package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.task.TaskCancel;

@Repository
public class TaskCancelDao extends BaseDao<TaskCancel> {

	@Override
	public Class<?> getEntityClass() {
		return TaskCancel.class;
	}

}
