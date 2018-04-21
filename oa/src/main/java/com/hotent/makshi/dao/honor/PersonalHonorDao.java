
package com.hotent.makshi.dao.honor;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.honor.PersonalHonor;

@Repository
public class PersonalHonorDao extends BaseDao<PersonalHonor>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalHonor.class;
	}

}