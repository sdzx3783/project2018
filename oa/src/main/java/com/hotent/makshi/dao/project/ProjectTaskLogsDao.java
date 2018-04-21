package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ProjectTaskLogs;

@Repository
public class ProjectTaskLogsDao extends BaseDao<ProjectTaskLogs> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectTaskLogs.class;
	}

}
