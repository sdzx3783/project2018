
package com.hotent.makshi.dao.common;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.common.TaskDataFetcher;

@Repository
public class TaskDataFetcherDao extends BaseDao<TaskDataFetcher>
{
	@Override
	public Class<?> getEntityClass()
	{
		return TaskDataFetcher.class;
	}

}