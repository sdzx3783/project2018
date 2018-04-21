
package com.hotent.makshi.dao.contract;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.ContractNumYear;

@Repository
public class ContractNumYearDao extends BaseDao<ContractNumYear>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractNumYear.class;
	}

}