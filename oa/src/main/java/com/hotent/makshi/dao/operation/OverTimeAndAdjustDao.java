
package com.hotent.makshi.dao.operation;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.operation.OverTime;

@Repository
public class OverTimeAndAdjustDao extends BaseDao<OverTime>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OverTime.class;
	}

}