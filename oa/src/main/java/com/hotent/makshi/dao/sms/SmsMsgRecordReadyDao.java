package com.hotent.makshi.dao.sms;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.sms.SmsMsgRecordReady;

@Repository
public class SmsMsgRecordReadyDao extends BaseDao<SmsMsgRecordReady> {
	@Override
	public Class<?> getEntityClass() {
		return SmsMsgRecordReady.class;
	}

}
