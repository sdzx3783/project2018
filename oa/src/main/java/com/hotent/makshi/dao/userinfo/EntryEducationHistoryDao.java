
package com.hotent.makshi.dao.userinfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.userinfo.EntryEducationHistory;

@Repository
public class EntryEducationHistoryDao extends BaseDao<EntryEducationHistory>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryEducationHistory.class;
	}

	/**
	 * 根据外键获取学习经历列表
	 * @param refId
	 * @return
	 */
	public List<EntryEducationHistory> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryEducationHistoryList", refId);
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