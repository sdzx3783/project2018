
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractBillingApplication;

@Repository
public class ContractBillingApplicationDao extends BaseDao<ContractBillingApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractBillingApplication.class;
	}

}