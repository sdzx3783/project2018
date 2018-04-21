
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.Practiceregist;

@Repository
public class PracticeregistDao extends BaseDao<Practiceregist>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Practiceregist.class;
	}

}