package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractCustomers;

@Repository
public class ContractCustomersDao extends BaseDao<ContractCustomers>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractCustomers.class;
	}

}