package com.hotent.makshi.service.operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.DrainageMonthly;
import com.hotent.makshi.dao.operation.DrainageMonthlyDao;
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
public class DrainageMonthlyService extends WfBaseService<DrainageMonthly>
{
	@Resource
	private DrainageMonthlyDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public DrainageMonthlyService()
	{
	}
	
	@Override
	protected IEntityDao<DrainageMonthly,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<DrainageMonthly> getAll(QueryFilter queryFilter){
		List<DrainageMonthly> drainageMonthlyList=super.getAll(queryFilter);
		List<DrainageMonthly> drainageMonthlys=new ArrayList<DrainageMonthly>();
		for(DrainageMonthly drainageMonthly:drainageMonthlyList){
			ProcessRun processRun=processRunService.getByBusinessKey(drainageMonthly.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				drainageMonthly.setRunId(processRun.getRunId());
			}
			drainageMonthlys.add(drainageMonthly);
		}
		return drainageMonthlys;
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
			DrainageMonthly drainageMonthly=getDrainageMonthly(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				drainageMonthly.setId(genId);
				this.add(drainageMonthly);
			}else{
				drainageMonthly.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(drainageMonthly);
			}
			cmd.setBusinessKey(drainageMonthly.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取DrainageMonthly对象
	 * @param json
	 * @return
	 */
	public DrainageMonthly getDrainageMonthly(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		DrainageMonthly drainageMonthly = (DrainageMonthly)JSONObject.toBean(obj, DrainageMonthly.class);
		return drainageMonthly;
	}
	/**
	 * 保存 排水处月报管理 信息
	 * @param drainageMonthly
	 */

	@WorkFlow(flowKey="drainageMonthly",tableName="drainage_monthly")
	public void save(DrainageMonthly drainageMonthly) throws Exception{
		Long id=drainageMonthly.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			drainageMonthly.setId(id);
		    this.add(drainageMonthly);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(drainageMonthly.getId().toString(), null , true,  "drainage_monthly");
		}
		else{
		    this.update(drainageMonthly);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
