
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.EntryXxjl;

@Repository
public class EntryXxjlDao extends WfBaseDao<EntryXxjl>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryXxjl.class;
	}

	/**
	 * 根据外键获取学习经历列表
	 * @param refId
	 * @return
	 */
	public List<EntryXxjl> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryXxjlList", refId);
	}
	
	/**
	 * 根据外键删除学习经历
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}