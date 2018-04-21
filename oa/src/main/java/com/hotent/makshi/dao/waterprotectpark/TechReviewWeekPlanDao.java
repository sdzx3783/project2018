
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.TechReviewWeekPlan;

@Repository
public class TechReviewWeekPlanDao extends BaseDao<TechReviewWeekPlan>
{
	@Override
	public Class<?> getEntityClass()
	{
		return TechReviewWeekPlan.class;
	}

}