package com.hotent.makshi.dao.director;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.director.ProjectNoticebook;

@Repository
public class ProjectNoticebookDao extends BaseDao<ProjectNoticebook> {

	@Override
	public Class<?> getEntityClass() {
		return ProjectNoticebook.class;
	}

}
