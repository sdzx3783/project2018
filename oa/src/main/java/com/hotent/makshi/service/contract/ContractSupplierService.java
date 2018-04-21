package com.hotent.makshi.service.contract;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.ContractSupplierDao;
import com.hotent.makshi.model.contract.ContractSupplier;

@Service
public class ContractSupplierService extends BaseService<ContractSupplier> {
	@Resource
	private ContractSupplierDao dao;

	public ContractSupplierService() {
	}

	@Override
	protected IEntityDao<ContractSupplier, Long> getEntityDao() {
		return dao;
	}

	public List<ContractSupplier> getAllSupplier(Date startDate,Date endDate) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		List<ContractSupplier> list = dao.getBySqlKey("getAllSupplier", params);
		return list;
	}

}
