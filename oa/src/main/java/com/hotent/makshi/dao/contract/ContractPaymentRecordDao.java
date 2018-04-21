
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractPaymentRecord;

@Repository
public class ContractPaymentRecordDao extends BaseDao<ContractPaymentRecord>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractPaymentRecord.class;
	}

	/**
	 * 根据外键获取合同付款记录列表
	 * @param refId
	 * @return
	 */
	public List<ContractPaymentRecord> getByMainId(Long refId) {
		return this.getBySqlKey("getContractPaymentRecordList", refId);
	}
	
	/**
	 * 根据外键删除合同付款记录
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}