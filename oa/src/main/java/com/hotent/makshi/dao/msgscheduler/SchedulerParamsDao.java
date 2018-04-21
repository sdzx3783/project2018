
package com.hotent.makshi.dao.msgscheduler;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.msgscheduler.SchedulerParams;

@Repository
public class SchedulerParamsDao extends WfBaseDao<SchedulerParams>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SchedulerParams.class;
	}

}