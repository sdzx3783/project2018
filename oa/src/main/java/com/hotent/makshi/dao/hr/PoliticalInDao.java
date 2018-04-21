
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.hr.PoliticalIn;

@Repository
public class PoliticalInDao extends BaseDao<PoliticalIn>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PoliticalIn.class;
	}

}