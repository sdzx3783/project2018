
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.WeekPlan;

@Repository
public class WeekPlanDao extends BaseDao<WeekPlan>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WeekPlan.class;
	}

	/**
	 * 根据外键获取周计划列表
	 * @param refId
	 * @return
	 */
	public List<WeekPlan> getByMainId(Long refId) {
		return this.getBySqlKey("getWeekPlanList", refId);
	}
	
	/**
	 * 根据外键删除周计划
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}