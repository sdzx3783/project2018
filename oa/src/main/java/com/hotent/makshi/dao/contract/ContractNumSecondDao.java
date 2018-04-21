
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.ContractNumSecond;

@Repository
public class ContractNumSecondDao extends BaseDao<ContractNumSecond>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractNumSecond.class;
	}

	/**
	 * 根据外键获取二级监理合同列表
	 * @param refId
	 * @return
	 */
	public List<ContractNumSecond> getByMainId(Long refId) {
		return this.getBySqlKey("getContractNumSecondList", refId);
	}
	
	/**
	 * 根据外键删除二级监理合同
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}