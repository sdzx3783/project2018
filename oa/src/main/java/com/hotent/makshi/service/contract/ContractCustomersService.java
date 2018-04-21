package com.hotent.makshi.service.contract;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.ContractCustomersDao;
import com.hotent.makshi.model.contract.ContractCustomers;

@Service
public class ContractCustomersService extends BaseService<ContractCustomers> {
	@Resource
	private ContractCustomersDao dao;

	public ContractCustomersService() {
	}

	@Override
	protected IEntityDao<ContractCustomers, Long> getEntityDao() {
		return dao;
	}

}
