
package com.hotent.makshi.dao.inhouseregistration;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.inhouseregistration.InHouseRegistration;

@Repository
public class InHouseRegistrationDao extends BaseDao<InHouseRegistration>
{
	@Override
	public Class<?> getEntityClass()
	{
		return InHouseRegistration.class;
	}

}