
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.CarUse;

@Repository
public class CarUseDao extends BaseDao<CarUse>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CarUse.class;
	}

}