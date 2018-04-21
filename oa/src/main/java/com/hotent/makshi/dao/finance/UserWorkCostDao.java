
package com.hotent.makshi.dao.finance;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.finance.UserWorkCost;

@Repository
public class UserWorkCostDao extends BaseDao<UserWorkCost>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserWorkCost.class;
	}

}