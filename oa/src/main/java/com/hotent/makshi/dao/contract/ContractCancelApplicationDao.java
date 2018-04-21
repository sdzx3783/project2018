
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.contract.ContractCancelApplication;

@Repository
public class ContractCancelApplicationDao extends WfBaseDao<ContractCancelApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractCancelApplication.class;
	}

}