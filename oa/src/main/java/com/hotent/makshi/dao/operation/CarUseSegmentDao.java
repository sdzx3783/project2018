
package com.hotent.makshi.dao.operation;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.operation.CarUseSegment;


@Repository
public class CarUseSegmentDao extends BaseDao<CarUseSegment>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CarUseSegment.class;
	}

}