
package com.hotent.makshi.dao.honor;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.honor.GroupHonor;

@Repository
public class GroupHonorDao extends BaseDao<GroupHonor>
{
	@Override
	public Class<?> getEntityClass()
	{
		return GroupHonor.class;
	}

}