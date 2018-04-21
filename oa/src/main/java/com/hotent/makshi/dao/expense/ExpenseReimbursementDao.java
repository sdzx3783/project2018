package com.hotent.makshi.dao.expense;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.expense.ExpenseReimbursement;

@Repository
public class ExpenseReimbursementDao extends BaseDao<ExpenseReimbursement> {
	@Override
	public Class<?> getEntityClass() {
		return ExpenseReimbursement.class;
	}

}
