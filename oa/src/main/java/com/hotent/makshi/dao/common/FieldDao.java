
package com.hotent.makshi.dao.common;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.common.FieldData;

@Repository
public class FieldDao extends BaseDao<FieldData>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FieldData.class;
	}

}