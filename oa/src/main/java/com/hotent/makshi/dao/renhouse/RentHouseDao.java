
package com.hotent.makshi.dao.renhouse;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.renhouse.RentHouse;

@Repository
public class RentHouseDao extends WfBaseDao<RentHouse>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RentHouse.class;
	}

}