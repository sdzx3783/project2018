package com.hotent.makshi.service.housereimburse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.housereimburse.HouseReimburseRecordDao;
import com.hotent.makshi.model.housereimburse.HouseReimburseRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class HouseReimburseRecordService extends BaseService<HouseReimburseRecord> {

	@Resource
	private HouseReimburseRecordDao houseReimburseRecordDao;

	@Override
	protected IEntityDao<HouseReimburseRecord, Long> getEntityDao() {
		return houseReimburseRecordDao;
	}

	public void saveAll(Long reimburse_id, String items) {
		delAllByReimburseId(reimburse_id);
		if (StringUtils.isNotEmpty(items)) {
			JSONArray arry = JSONArray.fromObject(items);
			for (Object object : arry) {
				JSONObject o = JSONObject.fromObject(object);
				HouseReimburseRecord item = new HouseReimburseRecord();
				item.setId(UniqueIdUtil.genId());
				item.setMoneys(o.containsKey("moneys") ? o.getString("moneys") : null);
				item.setPerson(o.containsKey("person") ? o.getString("person") : null);
				item.setReimburse_at(o.containsKey("reimburse_at") ? o.getString("reimburse_at") : null);
				item.setReimburse_id(reimburse_id);
				add(item);
			}
		}
	}

	public void delAllByReimburseId(Long reimburseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reimburseId", reimburseId);
		houseReimburseRecordDao.update("delAllByReimburseId", params);
	}

	public List<HouseReimburseRecord> getALlByReimburseId(Long reimburseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reimburseId", reimburseId);
		return houseReimburseRecordDao.getBySqlKey("getALlByReimburseId", params);
	}
}
