package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.MonthlyCollection;

@Repository
public class MonthlyCollectionDao extends BaseDao<MonthlyCollection>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MonthlyCollection.class;
	}

}