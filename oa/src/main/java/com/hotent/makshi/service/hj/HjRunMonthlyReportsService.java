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
import com.hotent.makshi.model.hj.HjRunMonthlyReports;
import com.hotent.makshi.dao.hj.HjRunMonthlyReportsDao;
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
public class HjRunMonthlyReportsService extends WfBaseService<HjRunMonthlyReports>
{
	@Resource
	private HjRunMonthlyReportsDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjRunMonthlyReportsService()
	{
	}
	
	@Override
	protected IEntityDao<HjRunMonthlyReports,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjRunMonthlyReports> getAll(QueryFilter queryFilter){
		List<HjRunMonthlyReports> hjRunMonthlyReportsList=super.getAll(queryFilter);
		List<HjRunMonthlyReports> hjRunMonthlyReportss=new ArrayList<HjRunMonthlyReports>();
		for(HjRunMonthlyReports hjRunMonthlyReports:hjRunMonthlyReportsList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjRunMonthlyReports.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjRunMonthlyReports.setRunId(processRun.getRunId());
			}
			hjRunMonthlyReportss.add(hjRunMonthlyReports);
		}
		return hjRunMonthlyReportss;
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
			HjRunMonthlyReports hjRunMonthlyReports=getHjRunMonthlyReports(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjRunMonthlyReports.setId(genId);
				this.add(hjRunMonthlyReports);
			}else{
				hjRunMonthlyReports.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjRunMonthlyReports);
			}
			cmd.setBusinessKey(hjRunMonthlyReports.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjRunMonthlyReports对象
	 * @param json
	 * @return
	 */
	public HjRunMonthlyReports getHjRunMonthlyReports(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjRunMonthlyReports hjRunMonthlyReports = (HjRunMonthlyReports)JSONObject.toBean(obj, HjRunMonthlyReports.class);
		return hjRunMonthlyReports;
	}
	/**
	 * 保存 llyy 信息
	 * @param hjRunMonthlyReports
	 */

	@WorkFlow(flowKey="ll",tableName="hj_run_monthly_reports")
	public void save(HjRunMonthlyReports hjRunMonthlyReports) throws Exception{
		Long id=hjRunMonthlyReports.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjRunMonthlyReports.setId(id);
		    this.add(hjRunMonthlyReports);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjRunMonthlyReports.getId().toString(), null , true,  "hj_run_monthly_reports");
		}
		else{
		    this.update(hjRunMonthlyReports);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
