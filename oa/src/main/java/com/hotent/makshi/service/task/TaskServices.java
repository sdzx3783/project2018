package com.hotent.makshi.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.task.TasksDao;
import com.hotent.makshi.model.task.Task;

@Service
public class TaskServices extends BaseService<Task> {
	@Resource
	private TasksDao dao;	
	public TaskServices(){	
		
	}
	
	@Override
	protected IEntityDao<Task, Long> getEntityDao() {
		return dao;
	}
	
	public List<Task> getUnderPlan(Long userId ,QueryFilter queryFilter){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		List<Task> list = dao.getBySqlKey("getUnderPlan",map,queryFilter.getPageBean());
		return list;
	}
}
