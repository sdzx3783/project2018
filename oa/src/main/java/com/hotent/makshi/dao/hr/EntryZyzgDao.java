
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hr.EntryZyzg;

@Repository
public class EntryZyzgDao extends WfBaseDao<EntryZyzg>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryZyzg.class;
	}

	/**
	 * 根据外键获取执业资格列表
	 * @param refId
	 * @return
	 */
	public List<EntryZyzg> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryZyzgList", refId);
	}
	
	/**
	 * 根据外键删除执业资格
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}