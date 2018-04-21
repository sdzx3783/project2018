
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.OperationWeeklyManage;

@Repository
public class OperationWeeklyManageDao extends BaseDao<OperationWeeklyManage>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OperationWeeklyManage.class;
	}

}