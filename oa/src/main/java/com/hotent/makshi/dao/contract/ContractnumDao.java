
package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.Contractnum;

@Repository
public class ContractnumDao extends BaseDao<Contractnum>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Contractnum.class;
	}

}