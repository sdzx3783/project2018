
package com.hotent.makshi.dao.title;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.title.VocationQualification;

@Repository
public class VocationQualificationDao extends BaseDao<VocationQualification>
{
	@Override
	public Class<?> getEntityClass()
	{
		return VocationQualification.class;
	}

}