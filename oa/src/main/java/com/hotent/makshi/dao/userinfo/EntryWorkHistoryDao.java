
package com.hotent.makshi.dao.userinfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.userinfo.EntryWorkHistory;

@Repository
public class EntryWorkHistoryDao extends BaseDao<EntryWorkHistory>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryWorkHistory.class;
	}

	/**
	 * 根据外键获取工作经历列表
	 * @param refId
	 * @return
	 */
	public List<EntryWorkHistory> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryWorkHistoryList", refId);
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