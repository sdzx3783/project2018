package com.hotent.makshi.service.operation;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.operation.CarUseSegmentDao;
import com.hotent.makshi.model.operation.CarUseSegment;


@Service
public class CarUseSegmentService extends BaseService<CarUseSegment>
{
	@Resource
	private CarUseSegmentDao dao;
	
	public CarUseSegmentService()
	{
	}
	
	@Override
	protected IEntityDao<CarUseSegment,Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CarUseSegment> getTimePoint(Date date){
		return dao.getBySqlKey("getAll",date);
	}
}
