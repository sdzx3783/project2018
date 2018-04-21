package com.hotent.makshi.dao.gates;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.gates.Executive;

@Repository
public class ExecutiveDao extends BaseDao<Executive> {

	@Override
	public Class<?> getEntityClass() {
		return Executive.class;
	}

}
