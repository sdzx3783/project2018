package com.hotent.makshi.service.project;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.project.StageLibraryDao;
import com.hotent.makshi.model.project.StageLibrary;

@Service
public class StageLibraryService extends BaseService<StageLibrary> {
	@Resource
	private StageLibraryDao dao;
	
	public StageLibraryService(){
		
	}
	
	@Override
	protected IEntityDao<StageLibrary, Long> getEntityDao() {
		return dao;
	}

}
