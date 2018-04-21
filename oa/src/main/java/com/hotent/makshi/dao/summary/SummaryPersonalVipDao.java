package com.hotent.makshi.dao.summary;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.summary.SummaryPersonalVip;

@Repository
public class SummaryPersonalVipDao extends BaseDao<SummaryPersonalVip> {

	@Override
	public Class<?> getEntityClass() {
		return SummaryPersonalVip.class;
	}

}