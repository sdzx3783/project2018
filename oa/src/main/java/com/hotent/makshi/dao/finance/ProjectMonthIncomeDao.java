
package com.hotent.makshi.dao.finance;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.finance.ProjectMonthIncome;

@Repository
public class ProjectMonthIncomeDao extends BaseDao<ProjectMonthIncome>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ProjectMonthIncome.class;
	}

}