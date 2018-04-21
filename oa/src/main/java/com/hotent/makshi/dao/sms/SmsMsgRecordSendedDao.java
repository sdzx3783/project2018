package com.hotent.makshi.dao.sms;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.sms.SmsMsgRecordSended;

@Repository
public class SmsMsgRecordSendedDao extends BaseDao<SmsMsgRecordSended> {
	@Override
	public Class<?> getEntityClass() {
		return SmsMsgRecordSended.class;
	}

}
