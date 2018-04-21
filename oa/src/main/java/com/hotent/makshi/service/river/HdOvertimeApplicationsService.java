package com.hotent.makshi.service.river;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.model.operation.OverTime;
import com.hotent.makshi.model.river.HdOvertimeApplications;
import com.hotent.makshi.dao.river.HdOvertimeApplicationsDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;

import net.sf.json.JSONObject;




import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class HdOvertimeApplicationsService extends WfBaseService<HdOvertimeApplications>
{
	@Resource
	private HdOvertimeApplicationsDao dao;
	public HdOvertimeApplicationsService()
	{
	}
	
	@Override
	protected IEntityDao<HdOvertimeApplications,Long> getEntityDao() 
	{
		return dao;
	}
	public void cleanInfo() {
		dao.update("cleanInfo", null);
	}
	public List<HdOvertimeApplications> getUser(QueryFilter queryFilter){
		return dao.getUser(queryFilter);
	}
	public List<HdOvertimeApplications> getPast(Long id) {
		return dao.getBySqlKey("getPast", id);
	}
}


