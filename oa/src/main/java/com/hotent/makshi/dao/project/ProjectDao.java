package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.Project;

@Repository
public class ProjectDao extends BaseDao<Project> {

	@Override
	public Class<?> getEntityClass() {
		return Project.class;
	}

}
