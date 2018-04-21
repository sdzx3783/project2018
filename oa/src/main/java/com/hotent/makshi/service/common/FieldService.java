package com.hotent.makshi.service.common;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.common.FieldDao;
import com.hotent.makshi.model.common.FieldData;


@Service
public class FieldService extends BaseService<FieldData>
{
	@Resource
	private FieldDao dao;
	
	public FieldService()
	{
	}
	
	@Override
	protected IEntityDao<FieldData,Long> getEntityDao() 
	{
		return dao;
	}

}
