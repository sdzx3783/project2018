
package com.hotent.makshi.dao.seal;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.seal.SealUseApplication;

@Repository
public class SealUseApplicationDao extends WfBaseDao<SealUseApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SealUseApplication.class;
	}

}