
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.GoodsPurchase;

@Repository
public class GoodsPurchaseDao extends BaseDao<GoodsPurchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return GoodsPurchase.class;
	}

}