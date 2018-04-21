
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.UserResignation;

@Repository
public class UserResignationDao extends WfBaseDao<UserResignation>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserResignation.class;
	}

}