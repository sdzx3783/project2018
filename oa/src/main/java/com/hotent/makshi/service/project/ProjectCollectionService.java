package com.hotent.makshi.service.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.project.ProjectCollectionDao;
import com.hotent.makshi.model.doc.DocCollection;
import com.hotent.makshi.model.project.ProjectCollection;

@Service
public class ProjectCollectionService extends BaseService<ProjectCollection> {
	@Resource
	private ProjectCollectionDao dao;
	
	public ProjectCollectionService(){
		
	}
	
	@Override
	protected IEntityDao<ProjectCollection, Long> getEntityDao() {
		return dao;
	}
	
	public ProjectCollection getByProjectIdAndUserId(Long userId,Integer projectId){
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("projectId", projectId);
		List<ProjectCollection> list = dao.getBySqlKey("getByProjectIdAndUserId", params);
		if(list==null || list.size()<=0){
			return null;
		}
		return list.get(0);
	}
	
	public boolean isCollection(Long userId,Integer projectId){
		
		ProjectCollection projectCollection = getByProjectIdAndUserId(userId,projectId);
		if(projectCollection!=null && projectCollection.getState()==1){
			return true;
		}else{
			return false;
		}
	}
}
