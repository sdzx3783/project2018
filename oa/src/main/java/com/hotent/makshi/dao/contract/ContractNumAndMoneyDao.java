package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractNumAndMoney;

@Repository
public class ContractNumAndMoneyDao extends BaseDao<ContractNumAndMoney>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractNumAndMoney.class;
	}

}