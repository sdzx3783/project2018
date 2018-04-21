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
import com.hotent.makshi.model.hj.HjAnnualReport;
import com.hotent.makshi.dao.hj.HjAnnualReportDao;
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
public class HjAnnualReportService extends WfBaseService<HjAnnualReport>
{
	@Resource
	private HjAnnualReportDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjAnnualReportService()
	{
	}
	
	@Override
	protected IEntityDao<HjAnnualReport,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjAnnualReport> getAll(QueryFilter queryFilter){
		List<HjAnnualReport> hjAnnualReportList=super.getAll(queryFilter);
		List<HjAnnualReport> hjAnnualReports=new ArrayList<HjAnnualReport>();
		for(HjAnnualReport hjAnnualReport:hjAnnualReportList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjAnnualReport.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjAnnualReport.setRunId(processRun.getRunId());
			}
			hjAnnualReports.add(hjAnnualReport);
		}
		return hjAnnualReports;
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
			HjAnnualReport hjAnnualReport=getHjAnnualReport(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjAnnualReport.setId(genId);
				this.add(hjAnnualReport);
			}else{
				hjAnnualReport.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjAnnualReport);
			}
			cmd.setBusinessKey(hjAnnualReport.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjAnnualReport对象
	 * @param json
	 * @return
	 */
	public HjAnnualReport getHjAnnualReport(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjAnnualReport hjAnnualReport = (HjAnnualReport)JSONObject.toBean(obj, HjAnnualReport.class);
		return hjAnnualReport;
	}
	/**
	 * 保存 nianbao 信息
	 * @param hjAnnualReport
	 */

	@WorkFlow(flowKey="ee",tableName="hj_Annual_report")
	public void save(HjAnnualReport hjAnnualReport) throws Exception{
		Long id=hjAnnualReport.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjAnnualReport.setId(id);
		    this.add(hjAnnualReport);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjAnnualReport.getId().toString(), null , true,  "hj_Annual_report");
		}
		else{
		    this.update(hjAnnualReport);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
