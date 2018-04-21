
package com.hotent.makshi.dao.contract;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.contract.InvoiceDetail;

@Repository
public class InvoiceDetailDao extends BaseDao<InvoiceDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return InvoiceDetail.class;
	}

	/**
	 * 根据外键获取发票明细列表
	 * @param refId
	 * @return
	 */
	public List<InvoiceDetail> getByMainId(Long refId) {
		return this.getBySqlKey("getInvoiceDetailList", refId);
	}
	
	/**
	 * 根据外键删除发票明细
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}