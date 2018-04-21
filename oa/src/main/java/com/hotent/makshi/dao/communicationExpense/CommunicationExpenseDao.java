
package com.hotent.makshi.dao.communicationExpense;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.communicationExpense.CommunicationExpense;

@Repository
public class CommunicationExpenseDao extends BaseDao<CommunicationExpense>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CommunicationExpense.class;
	}

}