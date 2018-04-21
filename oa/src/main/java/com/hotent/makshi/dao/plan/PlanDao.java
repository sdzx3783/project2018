package com.hotent.makshi.dao.plan;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.plan.Plan;
import com.hotent.makshi.model.project.Project;

@Repository
public class PlanDao extends BaseDao<Plan> {

	@Override
	public Class<?> getEntityClass() {
		return Plan.class;
	}

}
