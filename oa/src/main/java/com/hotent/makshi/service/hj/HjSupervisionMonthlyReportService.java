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
import com.hotent.makshi.model.hj.HjSupervisionMonthlyReport;
import com.hotent.makshi.dao.hj.HjSupervisionMonthlyReportDao;
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
public class HjSupervisionMonthlyReportService extends WfBaseService<HjSupervisionMonthlyReport>
{
	@Resource
	private HjSupervisionMonthlyReportDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjSupervisionMonthlyReportService()
	{
	}
	
	@Override
	protected IEntityDao<HjSupervisionMonthlyReport,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjSupervisionMonthlyReport> getAll(QueryFilter queryFilter){
		List<HjSupervisionMonthlyReport> hjSupervisionMonthlyReportList=super.getAll(queryFilter);
		List<HjSupervisionMonthlyReport> hjSupervisionMonthlyReports=new ArrayList<HjSupervisionMonthlyReport>();
		for(HjSupervisionMonthlyReport hjSupervisionMonthlyReport:hjSupervisionMonthlyReportList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjSupervisionMonthlyReport.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjSupervisionMonthlyReport.setRunId(processRun.getRunId());
			}
			hjSupervisionMonthlyReports.add(hjSupervisionMonthlyReport);
		}
		return hjSupervisionMonthlyReports;
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
			HjSupervisionMonthlyReport hjSupervisionMonthlyReport=getHjSupervisionMonthlyReport(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjSupervisionMonthlyReport.setId(genId);
				this.add(hjSupervisionMonthlyReport);
			}else{
				hjSupervisionMonthlyReport.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjSupervisionMonthlyReport);
			}
			cmd.setBusinessKey(hjSupervisionMonthlyReport.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjSupervisionMonthlyReport对象
	 * @param json
	 * @return
	 */
	public HjSupervisionMonthlyReport getHjSupervisionMonthlyReport(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjSupervisionMonthlyReport hjSupervisionMonthlyReport = (HjSupervisionMonthlyReport)JSONObject.toBean(obj, HjSupervisionMonthlyReport.class);
		return hjSupervisionMonthlyReport;
	}
	/**
	 * 保存 00 信息
	 * @param hjSupervisionMonthlyReport
	 */

	@WorkFlow(flowKey="mm",tableName="hj_Supervision_monthly_report")
	public void save(HjSupervisionMonthlyReport hjSupervisionMonthlyReport) throws Exception{
		Long id=hjSupervisionMonthlyReport.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjSupervisionMonthlyReport.setId(id);
		    this.add(hjSupervisionMonthlyReport);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjSupervisionMonthlyReport.getId().toString(), null , true,  "hj_Supervision_monthly_report");
		}
		else{
		    this.update(hjSupervisionMonthlyReport);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
