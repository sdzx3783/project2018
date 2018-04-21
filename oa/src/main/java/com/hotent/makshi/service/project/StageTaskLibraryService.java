package com.hotent.makshi.service.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.project.StageTaskLibraryDao;
import com.hotent.makshi.model.project.StageTaskLibrary;

@Service
public class StageTaskLibraryService extends BaseService<StageTaskLibrary> {
	@Resource
	private StageTaskLibraryDao dao;
	
	public StageTaskLibraryService(){
		
	}
	
	@Override
	protected IEntityDao<StageTaskLibrary, Long> getEntityDao() {
		return dao;
	}

	
	public List<StageTaskLibrary> getTasklibByStageno(Integer stageno){
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("stageno", stageno);
		return dao.getBySqlKey("getTasklib", params);
	}
	
	public List<StageTaskLibrary> getTasklibByStageno(QueryFilter filter){
		return dao.getBySqlKey("getTasklib", filter);
	}

	public void updateTaskOrder(Integer taskId, Integer order) {
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("id", taskId);
		params.put("order", order);
		dao.getBySqlKey("updateOrderById", params);
	}

	public int getCount(Integer stageno) {
		List<StageTaskLibrary> tasklibs = getTasklibByStageno(stageno);
		if(tasklibs==null){
			return 0;
		}else{
			return tasklibs.size();
		}
	}
}
