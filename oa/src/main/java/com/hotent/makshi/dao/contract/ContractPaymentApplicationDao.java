
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractPaymentApplication;

@Repository
public class ContractPaymentApplicationDao extends BaseDao<ContractPaymentApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractPaymentApplication.class;
	}

}