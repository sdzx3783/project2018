
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.hr.PoliticalOut;

@Repository
public class PoliticalOutDao extends BaseDao<PoliticalOut>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PoliticalOut.class;
	}

}