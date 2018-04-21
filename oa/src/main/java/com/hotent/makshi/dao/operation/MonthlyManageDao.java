
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.MonthlyManage;

@Repository
public class MonthlyManageDao extends BaseDao<MonthlyManage>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MonthlyManage.class;
	}

}