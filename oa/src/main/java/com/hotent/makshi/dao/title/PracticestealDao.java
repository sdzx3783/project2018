
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.Practicesteal;

@Repository
public class PracticestealDao extends BaseDao<Practicesteal>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Practicesteal.class;
	}

}