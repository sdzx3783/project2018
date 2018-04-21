
package com.hotent.makshi.dao.seal;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.seal.DepartmentalSeal;

@Repository
public class DepartmentalSealDao extends BaseDao<DepartmentalSeal>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DepartmentalSeal.class;
	}

}