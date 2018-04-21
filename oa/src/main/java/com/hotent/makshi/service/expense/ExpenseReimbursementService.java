package com.hotent.makshi.service.expense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.expense.ExpenseReimbursementDao;
import com.hotent.makshi.model.expense.ExpenseReimbursement;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExpenseReimbursementService extends BaseService<ExpenseReimbursement> {

	@Resource
	private ExpenseReimbursementDao expenseReimbursementDao;

	@Override
	protected IEntityDao<ExpenseReimbursement, Long> getEntityDao() {
		return expenseReimbursementDao;
	}

	public List<ExpenseReimbursement> getStatics(String orgId, String start, String end, int type) {
		Map<String, String> params = assembleDepartParams(orgId, start, end, type);
		List list = expenseReimbursementDao.getBySqlKey("getStatics", params);
		return list;
	}

	private Map<String, String> assembleDepartParams(String orgId, String start, String end, int type) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type + "");
		params.put("orgId", orgId);
		params.put("orgidlike", "%" + orgId + "%");
		params.put("start", start);
		params.put("end", end);
		return params;
	}

	public List getTypes(String orgId, String start, String end, int type) {
		Map<String, String> params = assembleDepartParams(orgId, start, end, type);
		List list = expenseReimbursementDao.getBySqlKey("getTypes", params);
		return list;
	}

	public List getOrgIds(String start, String end) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", start);
		params.put("end", end);
		List list = expenseReimbursementDao.getBySqlKey("getOrgIds", params);
		return list;
	}

	public List getDepartStatics(String orgId, String start, String end, int type) {
		Map<String, String> params = assembleDepartParams(orgId, start, end, type);
		List list = expenseReimbursementDao.getBySqlKey("getDepartStatics", params);
		return list;
	}

	public List getUsers(String orgId, String start, String end, int type) {
		Map<String, String> params = assembleDepartParams(orgId, start, end, type);
		List list = expenseReimbursementDao.getBySqlKey("getUsers", params);
		return list;
	}

}
