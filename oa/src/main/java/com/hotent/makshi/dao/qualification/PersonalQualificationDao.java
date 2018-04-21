
package com.hotent.makshi.dao.qualification;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.qualification.PersonalQualification;

@Repository
public class PersonalQualificationDao extends BaseDao<PersonalQualification>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalQualification.class;
	}

}