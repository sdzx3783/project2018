package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ProjectCollection;

@Repository
public class ProjectCollectionDao extends BaseDao<ProjectCollection> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectCollection.class;
	}

}
