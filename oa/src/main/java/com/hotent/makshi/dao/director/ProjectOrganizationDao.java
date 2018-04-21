package com.hotent.makshi.dao.director;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.director.ProjectOrganization;

@Repository
public class ProjectOrganizationDao extends BaseDao<ProjectOrganization> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectOrganization.class;
	}

}
