package com.hotent.makshi.service.worksheet;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.worksheet.WorkCountDao;
import com.hotent.makshi.model.worksheet.WorkCount;


@Service
public class WorkCountService extends BaseService<WorkCount>
{
	@Resource
	private WorkCountDao dao;
	
	public WorkCountService()
	{
	}
	
	@Override
	protected IEntityDao<WorkCount,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 批量插入出勤统计数据
	 * @param map
	 * @return
	 */
	public void batchInsert(Map<String,Object> map){
		dao.batchInsert( map);
	}
	
}
