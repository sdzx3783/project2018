package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractStatistics;

@Repository
public class ContractStatisticsDao extends BaseDao<ContractStatistics>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractStatistics.class;
	}

}