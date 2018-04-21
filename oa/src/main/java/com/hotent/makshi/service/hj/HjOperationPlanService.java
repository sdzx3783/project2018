package com.hotent.makshi.service.hj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hj.HjOperationPlan;
import com.hotent.makshi.dao.hj.HjOperationPlanDao;
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
public class HjOperationPlanService extends WfBaseService<HjOperationPlan>
{
	@Resource
	private HjOperationPlanDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjOperationPlanService()
	{
	}
	
	@Override
	protected IEntityDao<HjOperationPlan,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjOperationPlan> getAll(QueryFilter queryFilter){
		List<HjOperationPlan> hjOperationPlanList=super.getAll(queryFilter);
		List<HjOperationPlan> hjOperationPlans=new ArrayList<HjOperationPlan>();
		for(HjOperationPlan hjOperationPlan:hjOperationPlanList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjOperationPlan.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjOperationPlan.setRunId(processRun.getRunId());
			}
			hjOperationPlans.add(hjOperationPlan);
		}
		return hjOperationPlans;
	}
	

		
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			HjOperationPlan hjOperationPlan=getHjOperationPlan(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjOperationPlan.setId(genId);
				this.add(hjOperationPlan);
			}else{
				hjOperationPlan.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjOperationPlan);
			}
			cmd.setBusinessKey(hjOperationPlan.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjOperationPlan对象
	 * @param json
	 * @return
	 */
	public HjOperationPlan getHjOperationPlan(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjOperationPlan hjOperationPlan = (HjOperationPlan)JSONObject.toBean(obj, HjOperationPlan.class);
		return hjOperationPlan;
	}
	/**
	 * 保存 00 信息
	 * @param hjOperationPlan
	 */

	@WorkFlow(flowKey="e1",tableName="hj_operation_plan")
	public void save(HjOperationPlan hjOperationPlan) throws Exception{
		Long id=hjOperationPlan.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjOperationPlan.setId(id);
		    this.add(hjOperationPlan);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjOperationPlan.getId().toString(), null , true,  "hj_operation_plan");
		}
		else{
		    this.update(hjOperationPlan);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
