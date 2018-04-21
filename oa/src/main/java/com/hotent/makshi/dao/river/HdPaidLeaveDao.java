
package com.hotent.makshi.dao.river;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.river.HdPaidLeave;

@Repository
public class HdPaidLeaveDao extends BaseDao<HdPaidLeave>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HdPaidLeave.class;
	}

}