package com.hotent.makshi.dao.director;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.director.DirectorAppointing;

@Repository
public class DirectorAppointingDao extends BaseDao<DirectorAppointing> {

	@Override
	public Class<?> getEntityClass() {
		return DirectorAppointing.class;
	}

}
