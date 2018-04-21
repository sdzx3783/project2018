
package com.hotent.makshi.dao.userinfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.userinfo.EntryFamily;

@Repository
public class EntryFamilyDao extends BaseDao<EntryFamily>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryFamily.class;
	}

	/**
	 * 根据外键获取家庭成员列表
	 * @param refId
	 * @return
	 */
	public List<EntryFamily> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryFamilyList", refId);
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