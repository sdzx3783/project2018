package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.ClassifyStage;

@Repository
public class ClassifyStageDao extends BaseDao<ClassifyStage> {

	@Override
	public Class<?> getEntityClass() {
		return ClassifyStage.class;
	}

}
