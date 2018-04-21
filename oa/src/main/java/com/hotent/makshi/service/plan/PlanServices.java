package com.hotent.makshi.service.plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.plan.PlanDao;
import com.hotent.makshi.model.plan.Plan;

@Service
public class PlanServices extends BaseService<Plan> {
	@Resource
	private PlanDao dao;
	public PlanServices(){
		
	}
	
	@Override
	protected IEntityDao<Plan, Long> getEntityDao() {
		return dao;
	}
	
	public List<Plan> getUnderPlan(QueryFilter queryFilter){
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("userId", userId);
		List<Plan> list = dao.getBySqlKey("getUnderPlan",queryFilter);
		return list;
	}
}
