
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.EntryJtcy;

@Repository
public class EntryJtcyDao extends WfBaseDao<EntryJtcy>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryJtcy.class;
	}

	/**
	 * 根据外键获取家庭成员列表
	 * @param refId
	 * @return
	 */
	public List<EntryJtcy> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryJtcyList", refId);
	}
	
	/**
	 * 根据外键删除家庭成员
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}