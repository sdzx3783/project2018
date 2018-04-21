
package com.hotent.makshi.dao.hj;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hj.Hjwppurchaselist;

@Repository
public class HjwppurchaselistDao extends WfBaseDao<Hjwppurchaselist>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Hjwppurchaselist.class;
	}

	/**
	 * 根据外键获取物品采购信息列表
	 * @param refId
	 * @return
	 */
	public List<Hjwppurchaselist> getByMainId(Long refId) {
		return this.getBySqlKey("getHjwppurchaselistList", refId);
	}
	
	/**
	 * 根据外键删除物品采购信息
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}