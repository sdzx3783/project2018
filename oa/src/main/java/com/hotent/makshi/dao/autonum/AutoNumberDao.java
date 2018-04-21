package com.hotent.makshi.dao.autonum;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.autonum.AutoNumber;

@Repository
public class AutoNumberDao extends BaseDao<AutoNumber> {
	@Override
	public Class<?> getEntityClass() {
		return AutoNumber.class;
	}
}
