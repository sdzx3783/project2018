package com.hotent.makshi.service.operation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.operation.OverAndAdjustDao;
import com.hotent.makshi.model.operation.OverAndAdjust;


@Service
public class OverAndAdjustService extends BaseService<OverAndAdjust>
{
	@Resource
	private OverAndAdjustDao dao;
	@Override
	protected IEntityDao<OverAndAdjust,Long> getEntityDao() 
	{
		return dao;
	}
	public List<OverAndAdjust> getOverListByUsrId(Long id) {
		return dao.getBySqlKey("getOverListByUsrId",id);
	}
	public List<OverAndAdjust> getAdjustListByUsrId(Long id) {
		return dao.getBySqlKey("getAdjustListByUsrId",id);
	}
	public void cleanInfo() {
		dao.update("cleanInfo", null);
	}
	
	public List<OverAndAdjust> getAllAdjustByDate(String start, String end ,Long userId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("end", end);
		return dao.getBySqlKey("getAllAdjustByDate",params);
	}
	
	public List<OverAndAdjust> queryAdjustList(Map<String,Object> map) {
		return dao.getBySqlKey("queryAdjustList",map);
	}
}
