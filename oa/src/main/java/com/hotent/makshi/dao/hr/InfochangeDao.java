
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.hr.Infochange;

@Repository
public class InfochangeDao extends BaseDao<Infochange>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Infochange.class;
	}

}