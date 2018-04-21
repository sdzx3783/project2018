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
import com.hotent.makshi.model.hj.HjReviewAnnualReport;
import com.hotent.makshi.dao.hj.HjReviewAnnualReportDao;
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
public class HjReviewAnnualReportService extends WfBaseService<HjReviewAnnualReport>
{
	@Resource
	private HjReviewAnnualReportDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjReviewAnnualReportService()
	{
	}
	
	@Override
	protected IEntityDao<HjReviewAnnualReport,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjReviewAnnualReport> getAll(QueryFilter queryFilter){
		List<HjReviewAnnualReport> hjReviewAnnualReportList=super.getAll(queryFilter);
		List<HjReviewAnnualReport> hjReviewAnnualReports=new ArrayList<HjReviewAnnualReport>();
		for(HjReviewAnnualReport hjReviewAnnualReport:hjReviewAnnualReportList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjReviewAnnualReport.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjReviewAnnualReport.setRunId(processRun.getRunId());
			}
			hjReviewAnnualReports.add(hjReviewAnnualReport);
		}
		return hjReviewAnnualReports;
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
			HjReviewAnnualReport hjReviewAnnualReport=getHjReviewAnnualReport(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjReviewAnnualReport.setId(genId);
				this.add(hjReviewAnnualReport);
			}else{
				hjReviewAnnualReport.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjReviewAnnualReport);
			}
			cmd.setBusinessKey(hjReviewAnnualReport.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjReviewAnnualReport对象
	 * @param json
	 * @return
	 */
	public HjReviewAnnualReport getHjReviewAnnualReport(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjReviewAnnualReport hjReviewAnnualReport = (HjReviewAnnualReport)JSONObject.toBean(obj, HjReviewAnnualReport.class);
		return hjReviewAnnualReport;
	}
	/**
	 * 保存 jgndbgsh 信息
	 * @param hjReviewAnnualReport
	 */

	@WorkFlow(flowKey="jgndbgsh",tableName="hj_Review_annual_report")
	public void save(HjReviewAnnualReport hjReviewAnnualReport) throws Exception{
		Long id=hjReviewAnnualReport.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjReviewAnnualReport.setId(id);
		    this.add(hjReviewAnnualReport);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjReviewAnnualReport.getId().toString(), null , true,  "hj_Review_annual_report");
		}
		else{
		    this.update(hjReviewAnnualReport);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
