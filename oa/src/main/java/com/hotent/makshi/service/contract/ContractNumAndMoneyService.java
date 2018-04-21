package com.hotent.makshi.service.contract;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.makshi.model.contract.ContractNumAndMoney;
import com.hotent.makshi.dao.contract.ContractNumAndMoneyDao;
import com.hotent.core.service.BaseService;


@Service
public class ContractNumAndMoneyService extends BaseService<ContractNumAndMoney>
{
	@Resource
	private ContractNumAndMoneyDao dao;
	
	public ContractNumAndMoneyService()
	{
	}
	
	@Override
	protected IEntityDao<ContractNumAndMoney,Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<ContractNumAndMoney> getNumAndMoney(Map<String,Object> param){
		return dao.getBySqlKey("getAllInfo",param);
	}

	public List<ContractNumAndMoney> getNumAndMoneyCol(Map<String, Object> param) {
		return dao.getBySqlKey("getColInfo",param);
	}

}
