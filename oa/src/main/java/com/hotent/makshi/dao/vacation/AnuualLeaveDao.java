package com.hotent.makshi.dao.vacation;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.vacation.AnuualLeave;

@Repository
public class AnuualLeaveDao extends BaseDao<AnuualLeave> {

	@Override
	public Class<?> getEntityClass() {
		return AnuualLeave.class;
	}

}
