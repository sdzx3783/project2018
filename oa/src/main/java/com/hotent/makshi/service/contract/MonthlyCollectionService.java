package com.hotent.makshi.service.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.MonthlyCollectionDao;
import com.hotent.makshi.model.contract.MonthlyCollection;

@Service
public class MonthlyCollectionService extends BaseService<MonthlyCollection> {
	@Resource
	private MonthlyCollectionDao dao;

	public MonthlyCollectionService() {
	}

	@Override
	protected IEntityDao<MonthlyCollection, Long> getEntityDao() {
		return dao;
	}

	public List<MonthlyCollection> getMonthlyCollection(Integer year, Integer month) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("year", year);
		params.put("month", month);
		List<MonthlyCollection> monthlyCollectionList = dao.getBySqlKey("getAllDepartment", params);
		return monthlyCollectionList;
	}

	public MonthlyCollection getMonthlyNewCollection(String orgId, Integer year) {
		if (null == year || StringUtils.isEmpty(orgId))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("contractYear", year - 2);
		params.put("contractBillYear", year);
		return (MonthlyCollection)dao.getOne("getMonthlyNewCollection", params);
	}

}
