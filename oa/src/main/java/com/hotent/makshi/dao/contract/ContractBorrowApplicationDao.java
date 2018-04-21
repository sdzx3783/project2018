
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.contract.ContractBorrowApplication;

@Repository
public class ContractBorrowApplicationDao extends WfBaseDao<ContractBorrowApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractBorrowApplication.class;
	}

}