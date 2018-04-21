
package com.hotent.makshi.dao.template;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.template.RuleBookmark;

@Repository
public class RuleBookmarkDao extends BaseDao<RuleBookmark>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RuleBookmark.class;
	}

	/**
	 * 根据外键获取rule_bookmark列表
	 * @param refId
	 * @return
	 */
	public List<RuleBookmark> getByMainId(Long refId) {
		return this.getBySqlKey("getRuleBookmarkList", refId);
	}
	
	/**
	 * 根据外键删除rule_bookmark
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}