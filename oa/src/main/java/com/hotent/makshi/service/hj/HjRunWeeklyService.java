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
import com.hotent.makshi.model.hj.HjRunWeekly;
import com.hotent.makshi.dao.hj.HjRunWeeklyDao;
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
public class HjRunWeeklyService extends WfBaseService<HjRunWeekly>
{
	@Resource
	private HjRunWeeklyDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjRunWeeklyService()
	{
	}
	
	@Override
	protected IEntityDao<HjRunWeekly,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjRunWeekly> getAll(QueryFilter queryFilter){
		List<HjRunWeekly> hjRunWeeklyList=super.getAll(queryFilter);
		List<HjRunWeekly> hjRunWeeklys=new ArrayList<HjRunWeekly>();
		for(HjRunWeekly hjRunWeekly:hjRunWeeklyList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjRunWeekly.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjRunWeekly.setRunId(processRun.getRunId());
			}
			hjRunWeeklys.add(hjRunWeekly);
		}
		return hjRunWeeklys;
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
			HjRunWeekly hjRunWeekly=getHjRunWeekly(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjRunWeekly.setId(genId);
				this.add(hjRunWeekly);
			}else{
				hjRunWeekly.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjRunWeekly);
			}
			cmd.setBusinessKey(hjRunWeekly.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjRunWeekly对象
	 * @param json
	 * @return
	 */
	public HjRunWeekly getHjRunWeekly(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjRunWeekly hjRunWeekly = (HjRunWeekly)JSONObject.toBean(obj, HjRunWeekly.class);
		return hjRunWeekly;
	}
	/**
	 * 保存 22 信息
	 * @param hjRunWeekly
	 */

	@WorkFlow(flowKey="qq123",tableName="hj_run_weekly")
	public void save(HjRunWeekly hjRunWeekly) throws Exception{
		Long id=hjRunWeekly.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjRunWeekly.setId(id);
		    this.add(hjRunWeekly);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjRunWeekly.getId().toString(), null , true,  "hj_run_weekly");
		}
		else{
		    this.update(hjRunWeekly);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
