
package com.hotent.makshi.dao.common;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.common.WChangeHistory;

@Repository
public class ChangeHistoryDao extends WfBaseDao<WChangeHistory>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WChangeHistory.class;
	}

}