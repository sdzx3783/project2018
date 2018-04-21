package com.hotent.makshi.service.operation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.operation.OverTimeAndAdjustDao;
import com.hotent.makshi.model.operation.OverTime;


@Service
public class OverTimeAndAdjustService extends BaseService<OverTime>
{
	@Resource
	private OverTimeAndAdjustDao dao;
	@Override
	protected IEntityDao<OverTime,Long> getEntityDao() 
	{
		return dao;
	}
	public void cleanInfo() {
		dao.update("cleanInfo", null);
	}
	public List<OverTime> getPast(Long id) {
		return dao.getBySqlKey("getPast", id);
	}
	public List<OverTime> getAllInfo(Map<String,Object> params) {
		return dao.getBySqlKey("getAllInfo",params);
	}
}
