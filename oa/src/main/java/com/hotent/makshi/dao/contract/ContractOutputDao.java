package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractOutput;

@Repository
public class ContractOutputDao extends BaseDao<ContractOutput>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractOutput.class;
	}

}