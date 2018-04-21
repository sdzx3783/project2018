package com.hotent.makshi.dao.autonum;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.autonum.ExpenseOptions;

@Repository
public class ExpenseOptionsDao extends BaseDao<ExpenseOptions> {
	@Override
	public Class<?> getEntityClass() {
		return ExpenseOptions.class;
	}
}
