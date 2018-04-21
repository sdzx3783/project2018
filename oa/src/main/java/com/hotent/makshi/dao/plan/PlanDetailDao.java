package com.hotent.makshi.dao.plan;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.plan.PlanDetail;

@Repository
public class PlanDetailDao extends BaseDao<PlanDetail> {

	@Override
	public Class<?> getEntityClass() {
		return PlanDetail.class;
	}

}
