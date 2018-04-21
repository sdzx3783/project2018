
package com.hotent.makshi.dao.vacation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.vacation.CompanyYearVaction;

@Repository
public class CompanyYearVactionDao extends BaseDao<CompanyYearVaction>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CompanyYearVaction.class;
	}

}