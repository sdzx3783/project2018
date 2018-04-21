
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.PersonalQualificationRegist;

@Repository
public class PersonalQualificationRegistDao extends BaseDao<PersonalQualificationRegist>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalQualificationRegist.class;
	}

}