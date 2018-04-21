package com.hotent.makshi.dao.task;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.task.TaskTransfer;

@Repository
public class TaskTransferDao extends BaseDao<TaskTransfer> {

	@Override
	public Class<?> getEntityClass() {
		return TaskTransfer.class;
	}

}
