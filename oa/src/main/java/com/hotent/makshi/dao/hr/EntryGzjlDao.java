
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.EntryGzjl;

@Repository
public class EntryGzjlDao extends WfBaseDao<EntryGzjl>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryGzjl.class;
	}

	/**
	 * 根据外键获取工作经历列表
	 * @param refId
	 * @return
	 */
	public List<EntryGzjl> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryGzjlList", refId);
	}
	
	/**
	 * 根据外键删除工作经历
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}