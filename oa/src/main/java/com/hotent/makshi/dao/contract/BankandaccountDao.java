
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.Bankandaccount;

@Repository
public class BankandaccountDao extends BaseDao<Bankandaccount>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Bankandaccount.class;
	}

}