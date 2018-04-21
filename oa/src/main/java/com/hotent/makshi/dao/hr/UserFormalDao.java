
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.UserFormal;

@Repository
public class UserFormalDao extends WfBaseDao<UserFormal>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserFormal.class;
	}

}