
package com.hotent.makshi.dao.qualification;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.qualification.AllQualificationLoans;

@Repository
public class AllQualificationLoansDao extends BaseDao<AllQualificationLoans>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AllQualificationLoans.class;
	}

}