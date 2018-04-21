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
import com.hotent.makshi.model.hj.HjTechnicalReportExamin;
import com.hotent.makshi.dao.hj.HjTechnicalReportExaminDao;
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
public class HjTechnicalReportExaminService extends WfBaseService<HjTechnicalReportExamin>
{
	@Resource
	private HjTechnicalReportExaminDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjTechnicalReportExaminService()
	{
	}
	
	@Override
	protected IEntityDao<HjTechnicalReportExamin,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjTechnicalReportExamin> getAll(QueryFilter queryFilter){
		List<HjTechnicalReportExamin> hjTechnicalReportExaminList=super.getAll(queryFilter);
		List<HjTechnicalReportExamin> hjTechnicalReportExamins=new ArrayList<HjTechnicalReportExamin>();
		for(HjTechnicalReportExamin hjTechnicalReportExamin:hjTechnicalReportExaminList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjTechnicalReportExamin.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjTechnicalReportExamin.setRunId(processRun.getRunId());
			}
			hjTechnicalReportExamins.add(hjTechnicalReportExamin);
		}
		return hjTechnicalReportExamins;
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
			HjTechnicalReportExamin hjTechnicalReportExamin=getHjTechnicalReportExamin(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjTechnicalReportExamin.setId(genId);
				this.add(hjTechnicalReportExamin);
			}else{
				hjTechnicalReportExamin.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjTechnicalReportExamin);
			}
			cmd.setBusinessKey(hjTechnicalReportExamin.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjTechnicalReportExamin对象
	 * @param json
	 * @return
	 */
	public HjTechnicalReportExamin getHjTechnicalReportExamin(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjTechnicalReportExamin hjTechnicalReportExamin = (HjTechnicalReportExamin)JSONObject.toBean(obj, HjTechnicalReportExamin.class);
		return hjTechnicalReportExamin;
	}
	/**
	 * 保存 技术报告报审 信息
	 * @param hjTechnicalReportExamin
	 */

	@WorkFlow(flowKey="jsbgbs",tableName="hj_Technical_report_examin")
	public void save(HjTechnicalReportExamin hjTechnicalReportExamin) throws Exception{
		Long id=hjTechnicalReportExamin.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjTechnicalReportExamin.setId(id);
		    this.add(hjTechnicalReportExamin);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjTechnicalReportExamin.getId().toString(), null , true,  "hj_Technical_report_examin");
		}
		else{
		    this.update(hjTechnicalReportExamin);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
