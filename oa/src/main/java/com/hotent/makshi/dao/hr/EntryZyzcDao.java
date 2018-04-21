
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.EntryZyzc;

@Repository
public class EntryZyzcDao extends WfBaseDao<EntryZyzc>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryZyzc.class;
	}

	/**
	 * 根据外键获取专业职称列表
	 * @param refId
	 * @return
	 */
	public List<EntryZyzc> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryZyzcList", refId);
	}
	
	/**
	 * 根据外键删除专业职称
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}