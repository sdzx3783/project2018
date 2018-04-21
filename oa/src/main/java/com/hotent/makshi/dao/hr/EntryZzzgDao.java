
package com.hotent.makshi.dao.hr;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.hr.EntryZzzg;

@Repository
public class EntryZzzgDao extends BaseDao<EntryZzzg>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryZzzg.class;
	}

	/**
	 * 根据外键获取注册资格列表
	 * @param refId
	 * @return
	 */
	public List<EntryZzzg> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryZzzgList", refId);
	}
	
	/**
	 * 根据外键删除注册资格
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}