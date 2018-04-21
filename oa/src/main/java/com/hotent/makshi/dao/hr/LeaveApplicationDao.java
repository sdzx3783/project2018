
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.LeaveApplication;

@Repository
public class LeaveApplicationDao extends WfBaseDao<LeaveApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return LeaveApplication.class;
	}

}