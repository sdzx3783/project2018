package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.StageLibrary;

@Repository
public class StageLibraryDao extends BaseDao<StageLibrary> {

	@Override
	public Class<?> getEntityClass() {
		return StageLibrary.class;
	}

}
