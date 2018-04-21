
package com.hotent.makshi.dao.dispatch;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.dispatch.Dispatch;

@Repository
public class DispatchDao extends WfBaseDao<Dispatch>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Dispatch.class;
	}

}