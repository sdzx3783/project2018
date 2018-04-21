package com.hotent.makshi.service.contract;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.contract.ContractNumYearDao;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumYear;


@Service
public class ContractNumYearService extends BaseService<ContractNumYear>
{
	@Resource
	private ContractNumYearDao dao;
	
	public ContractNumYearService()
	{
	}
	
	@Override
	protected IEntityDao<ContractNumYear,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void save(ContractNumYear contractNumYear) throws Exception{
		ContractNumYear contractNumYearExist = this.getByRefIdAndYear(contractNumYear.getContractId(),contractNumYear.getYear());
		Long id = 0L;
		if(null==contractNumYearExist){
			id=UniqueIdUtil.genId();
			contractNumYear.setId(id);
			this.add(contractNumYear);
		}
		else{
			contractNumYear.setId(contractNumYearExist.getId());
		    this.update(contractNumYear);
		}
	}

	public ContractNumYear getByRefId(Long contractId) {
		List<ContractNumYear> list = dao.getBySqlKey("getByRefId",contractId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public ContractNumYear getByRefIdAndYear(Long contractId,String year) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contractId", contractId);
		params.put("year", year);
		List<ContractNumYear> list = dao.getBySqlKey("getByRefIdAndYear",params);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public int updateFlowNo(Long id) {
		return dao.update("updateFlowNo", id);
		
	}
	
}
