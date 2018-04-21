
package com.hotent.makshi.dao.finance;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.finance.CustomTaskHour;

@Repository
public class CustomTaskHourDao extends BaseDao<CustomTaskHour>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CustomTaskHour.class;
	}

	/**
	 * 根据外键获取自定义任务工时列表
	 * @param refId
	 * @return
	 */
	public List<CustomTaskHour> getByMainId(Long refId) {
		return this.getBySqlKey("getCustomTaskHourList", refId);
	}
	
	/**
	 * 根据外键删除自定义任务工时
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}