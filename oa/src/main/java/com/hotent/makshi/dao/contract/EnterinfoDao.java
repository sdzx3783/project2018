
package com.hotent.makshi.dao.contract;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.contract.Enterinfo;

@Repository
public class EnterinfoDao extends BaseDao<Enterinfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Enterinfo.class;
	}

	/**
	 * 根据外键获取到账信息列表
	 * @param refId
	 * @return
	 */
	public List<Enterinfo> getByMainId(Long refId) {
		return this.getBySqlKey("getEnterinfoList", refId);
	}
	
	/**
	 * 根据外键删除到账信息
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}