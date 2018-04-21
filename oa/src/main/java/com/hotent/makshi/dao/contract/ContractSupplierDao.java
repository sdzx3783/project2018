package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractSupplier;

@Repository
public class ContractSupplierDao extends BaseDao<ContractSupplier>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractSupplier.class;
	}

}