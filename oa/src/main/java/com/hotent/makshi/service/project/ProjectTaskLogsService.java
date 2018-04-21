package com.hotent.makshi.service.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.project.ProjectTaskLogsDao;
import com.hotent.makshi.model.project.ProjectTaskLogs;

@Service
public class ProjectTaskLogsService extends BaseService<ProjectTaskLogs> {
	@Resource
	private ProjectTaskLogsDao dao;
	
	public ProjectTaskLogsService(){
		
	}
	
	@Override
	protected IEntityDao<ProjectTaskLogs, Long> getEntityDao() {
		return dao;
	}

	
	
	public List<ProjectTaskLogs> getByCtimeAndOrg(String date,Long orgId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", "%"+date+"%");
		map.put("orgId", orgId);
		return dao.getBySqlKey("getByCtimeAndOrg", map);
	}
	
	
	public List<ProjectTaskLogs> getByCtimeAndOrgAndStatus(String date,Long orgId,int status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", "%"+date+"%");
		map.put("orgId", orgId);
		map.put("status", status);
		return dao.getBySqlKey("getByCtimeAndOrgAndStatus", map);
	}
	
	public List<ProjectTaskLogs> getByCtimeAndClassifyId(String date,Long classifyId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", "%"+date+"%");
		map.put("classifyId", classifyId);
		return dao.getBySqlKey("getByCtimeAndClassifyId", map);
	}
	
	public List<ProjectTaskLogs> getByCtimeAndClassifyId(String date,Long classifyId,int status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", "%"+date+"%");
		map.put("classifyId", classifyId);
		if(status!=-1){
			map.put("status", status);
		}
		return dao.getBySqlKey("getByCtimeAndClassifyId", map);
	}
}
