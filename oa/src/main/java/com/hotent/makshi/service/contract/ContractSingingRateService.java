package com.hotent.makshi.service.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.contract.ContractSingingRateDao;
import com.hotent.makshi.model.contract.ContractSingingRate;

@Service
public class ContractSingingRateService extends BaseService<ContractSingingRate> {
	@Resource
	private ContractSingingRateDao dao;

	public ContractSingingRateService() {
	}

	@Override
	protected IEntityDao<ContractSingingRate, Long> getEntityDao() {
		return dao;
	}

	private Map<String, Object> contractSingingRateQueryParams(String years, String orgIds, String cotps, List<String> cotpsList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cotpsList", cotpsList);
		if (StringUtil.isNotEmpty(years))
			map.put("years", years.split(","));
		if (StringUtil.isNotEmpty(orgIds))
			map.put("orgIds", orgIds.split(","));
		if (StringUtil.isNotEmpty(cotps))
			map.put("cotps", cotps.split(","));
		return map;
	}

	public List<ContractSingingRate> getAllDepartmentContractSigningStatistics(String years, String orgIds, String cotps, List<String> cotpsList) {
		return (List<ContractSingingRate>) dao.getBySqlKey("getAllDepartmentContractSigningStatistics", contractSingingRateQueryParams(years, orgIds, cotps , cotpsList ));
	}

	public ContractSingingRate getAllDepartmentTotalContractSigningStatistics(String years, String orgIds, String cotps , List<String> cotpsList) {
		ContractSingingRate d = (ContractSingingRate) dao.getOne("getAllDepartmentTotalContractSigningStatistics", contractSingingRateQueryParams(years, orgIds, cotps , cotpsList ));
		if (d != null) {
			d.setDepartment("合计");
			d.setContracttype(null);
		}
		return d;
	}

}
