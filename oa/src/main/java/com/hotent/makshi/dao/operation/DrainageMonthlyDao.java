
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.operation.DrainageMonthly;

@Repository
public class DrainageMonthlyDao extends WfBaseDao<DrainageMonthly>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DrainageMonthly.class;
	}

}