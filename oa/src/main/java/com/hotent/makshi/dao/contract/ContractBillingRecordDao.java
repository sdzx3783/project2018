
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractBillingRecord;

@Repository
public class ContractBillingRecordDao extends BaseDao<ContractBillingRecord>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractBillingRecord.class;
	}

	/**
	 * 根据外键获取合同开票记录列表
	 * @param refId
	 * @return
	 */
	public List<ContractBillingRecord> getByMainId(Long refId) {
		return this.getBySqlKey("getContractBillingRecordList", refId);
	}
	
	/**
	 * 根据外键删除合同开票记录
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
	
	public void delByLinkId(Long linkId){
		this.delBySqlKey("delByLinkId", linkId);
	}
	
	public List<ContractBillingRecord> getByLinkId(Long linkId){
		return this.getBySqlKey("getByLinkId", linkId);
	}
}