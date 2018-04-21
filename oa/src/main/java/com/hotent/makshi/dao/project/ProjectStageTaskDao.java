package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ProjectStageTask;

@Repository
public class ProjectStageTaskDao extends BaseDao<ProjectStageTask> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectStageTask.class;
	}

}
