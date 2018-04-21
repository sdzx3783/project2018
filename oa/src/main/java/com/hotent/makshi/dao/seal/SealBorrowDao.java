
package com.hotent.makshi.dao.seal;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.seal.SealBorrow;

@Repository
public class SealBorrowDao extends WfBaseDao<SealBorrow>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SealBorrow.class;
	}

}