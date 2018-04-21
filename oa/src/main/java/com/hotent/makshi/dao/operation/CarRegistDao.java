
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.CarRegist;

@Repository
public class CarRegistDao extends BaseDao<CarRegist>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CarRegist.class;
	}

}