package com.hotent.makshi.service.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.project.ProjectStageDao;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.StageTaskLibrary;

@Service
public class ProjectStageService extends BaseService<ProjectStage> {
	@Resource
	private ProjectStageDao dao;
	
	public ProjectStageService(){
		
	}
	
	@Override
	protected IEntityDao<ProjectStage, Long> getEntityDao() {
		return dao;
	}
	
	
	public List<ProjectStage> getProjectStageByProId(Integer proId){
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("proId", proId);
		return dao.getBySqlKey("getProjectStageByProId", params);
	}

	public ProjectStage getProjectStageByCstIdAndProId(Long csId,Integer proId){
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("csId", csId);
		params.put("proId", proId);
		List<ProjectStage> bySqlKey = dao.getBySqlKey("getProjectStageByCstIdAndProId", params);
		return bySqlKey==null || bySqlKey.size()==0?null:bySqlKey.get(0);
	}
	
}
