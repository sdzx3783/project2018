
package com.hotent.makshi.dao.talente;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.talente.PersonIn;

@Repository
public class PersonInDao extends WfBaseDao<PersonIn>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonIn.class;
	}

}