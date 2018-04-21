package com.hotent.makshi.service.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.sms.SmsMsgRecordReadyDao;
import com.hotent.makshi.model.sms.SmsMsgRecordReady;

@Service
public class SmsMsgRecordReadyService extends BaseService<SmsMsgRecordReady> {

	@Resource
	private SmsMsgRecordReadyDao smsMsgRecordReadyDao;

	@Override
	protected IEntityDao<SmsMsgRecordReady, Long> getEntityDao() {
		return smsMsgRecordReadyDao;
	}

	public void add(Long userid, String username, String mobile, String smsContent) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsContent))
			return;
		SmsMsgRecordReady ready = new SmsMsgRecordReady();
		ready.setId(UniqueIdUtil.genId());
		ready.setUserid(null != userid ? String.valueOf(userid) : null);
		ready.setUsername(username);
		ready.setMobile(mobile);
		ready.setContent(smsContent);
		smsMsgRecordReadyDao.add(ready);
	}

	public List<SmsMsgRecordReady> getRecords(int sizes) {
		if (sizes <= 0)
			return null;
		Map<String, Object> params = new HashMap<>();
		params.put("sizes", sizes);
		return smsMsgRecordReadyDao.getBySqlKey("getRecords", params);
	}
}
