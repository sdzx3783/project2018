
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractNumAdmin;

@Repository
public class ContractNumAdminDao extends BaseDao<ContractNumAdmin>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractNumAdmin.class;
	}

}