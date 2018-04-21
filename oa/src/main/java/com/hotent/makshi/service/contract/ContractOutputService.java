package com.hotent.makshi.service.contract;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.ContractOutputDao;
import com.hotent.makshi.model.contract.ContractOutput;

@Service
public class ContractOutputService extends BaseService<ContractOutput> {
	@Resource
	private ContractOutputDao dao;

	public ContractOutputService() {
	}

	@Override
	protected IEntityDao<ContractOutput, Long> getEntityDao() {
		return dao;
	}

}
