package com.hotent.makshi.dao.project;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.project.StageTaskLibrary;

@Repository
public class StageTaskLibraryDao extends BaseDao<StageTaskLibrary> {

	@Override
	public Class<?> getEntityClass() {
		return StageTaskLibrary.class;
	}

}
