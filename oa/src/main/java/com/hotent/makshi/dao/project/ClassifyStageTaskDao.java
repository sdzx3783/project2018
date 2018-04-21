package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ClassifyStageTask;

@Repository
public class ClassifyStageTaskDao extends BaseDao<ClassifyStageTask> {

	@Override
	public Class<?> getEntityClass() {
		return ClassifyStageTask.class;
	}

}
