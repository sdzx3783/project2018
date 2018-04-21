package com.hotent.makshi.service.sms;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.sms.SmsMsgRecordSendedDao;
import com.hotent.makshi.model.sms.SmsMsgRecordSended;

@Service
public class SmsMsgRecordSendedService extends BaseService<SmsMsgRecordSended> {

	@Resource
	private SmsMsgRecordSendedDao smsMsgRecordSendedDao;

	@Override
	protected IEntityDao<SmsMsgRecordSended, Long> getEntityDao() {
		return smsMsgRecordSendedDao;
	}

}
