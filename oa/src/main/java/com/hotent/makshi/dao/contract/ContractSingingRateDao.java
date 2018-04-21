package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractSingingRate;

@Repository
public class ContractSingingRateDao extends BaseDao<ContractSingingRate>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractSingingRate.class;
	}

}