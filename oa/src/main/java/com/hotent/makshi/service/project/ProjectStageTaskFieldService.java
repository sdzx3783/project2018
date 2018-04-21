package com.hotent.makshi.service.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.project.ProjectStageTaskFieldDao;
import com.hotent.makshi.model.project.ProjectStageTaskField;

@Service
public class ProjectStageTaskFieldService extends BaseService<ProjectStageTaskField> {
	@Resource
	private ProjectStageTaskFieldDao dao;
	public ProjectStageTaskFieldService(){
		
	}
	
	@Override
	protected IEntityDao<ProjectStageTaskField, Long> getEntityDao() {
		return dao;
	}
	
	
	public List<ProjectStageTaskField> getFieldByTask(Integer taskId){
		List<ProjectStageTaskField> list = dao.getBySqlKey("getFieldByTask", taskId);
		return list;
	}
	
	public List<ProjectStageTaskField> getFieldByTaskAndTime(Integer taskId,String date){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("date", date);
		List<ProjectStageTaskField> list = dao.getBySqlKey("getFieldByTaskAndTime", map);
		return list;
	}

	public List<ProjectStageTaskField> getFieldsInfoByTaskId(Integer taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		List<ProjectStageTaskField> list = dao.getBySqlKey("getFieldsInfoByTaskId", map);
		return list;
	}
	
}
