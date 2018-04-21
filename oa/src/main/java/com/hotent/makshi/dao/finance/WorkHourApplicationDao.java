
package com.hotent.makshi.dao.finance;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.finance.WorkHourApplication;

@Repository
public class WorkHourApplicationDao extends BaseDao<WorkHourApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WorkHourApplication.class;
	}

}