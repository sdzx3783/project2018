
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.Contractinfo;

@Repository
public class ContractinfoDao extends BaseDao<Contractinfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Contractinfo.class;
	}

}