package com.hotent.makshi.dao.summary;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.summary.SummaryCompanyVip;

@Repository
public class SummaryCompanyVipDao extends BaseDao<SummaryCompanyVip> {

	@Override
	public Class<?> getEntityClass() {
		return SummaryCompanyVip.class;
	}

}