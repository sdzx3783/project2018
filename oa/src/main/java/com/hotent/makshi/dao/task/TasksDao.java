package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.plan.Plan;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.task.Task;

@Repository
public class TasksDao extends BaseDao<Task> {

	@Override
	public Class<?> getEntityClass() {
		return Task.class;
	}

}
