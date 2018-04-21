package com.hotent.makshi.dao.plan;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.plan.PlanReply;

@Repository
public class PlanReplyDao extends BaseDao<PlanReply> {

	@Override
	public Class<?> getEntityClass() {
		return PlanReply.class;
	}

}
