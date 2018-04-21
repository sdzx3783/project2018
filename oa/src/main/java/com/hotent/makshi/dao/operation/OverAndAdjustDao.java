
package com.hotent.makshi.dao.operation;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.operation.OverAndAdjust;

@Repository
public class OverAndAdjustDao extends BaseDao<OverAndAdjust>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OverAndAdjust.class;
	}

}