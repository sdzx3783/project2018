
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.PersonalQualificationTransfer;

@Repository
public class PersonalQualificationTransferDao extends BaseDao<PersonalQualificationTransfer>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalQualificationTransfer.class;
	}

}