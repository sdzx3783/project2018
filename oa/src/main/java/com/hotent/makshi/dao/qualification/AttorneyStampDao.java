
package com.hotent.makshi.dao.qualification;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.qualification.AttorneyStamp;

@Repository
public class AttorneyStampDao extends BaseDao<AttorneyStamp>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AttorneyStamp.class;
	}

}