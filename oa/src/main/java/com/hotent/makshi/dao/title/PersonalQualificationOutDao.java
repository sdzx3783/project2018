
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.PersonalQualificationOut;

@Repository
public class PersonalQualificationOutDao extends BaseDao<PersonalQualificationOut>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalQualificationOut.class;
	}

}