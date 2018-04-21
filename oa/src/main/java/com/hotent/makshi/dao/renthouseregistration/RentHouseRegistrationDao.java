
package com.hotent.makshi.dao.renthouseregistration;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.renthouseregistration.RentHouseRegistration;

@Repository
public class RentHouseRegistrationDao extends BaseDao<RentHouseRegistration>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RentHouseRegistration.class;
	}

}