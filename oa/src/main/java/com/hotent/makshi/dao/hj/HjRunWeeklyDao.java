
package com.hotent.makshi.dao.hj;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hj.HjRunWeekly;

@Repository
public class HjRunWeeklyDao extends WfBaseDao<HjRunWeekly>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjRunWeekly.class;
	}

}