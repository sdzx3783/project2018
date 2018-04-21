package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ProjectStage;

@Repository
public class ProjectStageDao extends BaseDao<ProjectStage> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectStage.class;
	}

}
