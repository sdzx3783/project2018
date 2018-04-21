
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.DepartmentTransfer;

@Repository
public class DepartmentTransferDao extends WfBaseDao<DepartmentTransfer>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DepartmentTransfer.class;
	}

}