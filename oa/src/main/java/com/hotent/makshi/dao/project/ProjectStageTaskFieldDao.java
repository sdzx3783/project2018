package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ProjectStageTaskField;

@Repository
public class ProjectStageTaskFieldDao extends BaseDao<ProjectStageTaskField> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectStageTaskField.class;
	}

}
