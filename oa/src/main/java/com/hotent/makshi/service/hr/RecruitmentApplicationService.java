package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hr.RecruitmentApplication;
import com.hotent.makshi.dao.hr.RecruitmentApplicationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class RecruitmentApplicationService extends WfBaseService<RecruitmentApplication>
{
	@Resource
	private RecruitmentApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public RecruitmentApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<RecruitmentApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<RecruitmentApplication> getAll(QueryFilter queryFilter){
		List<RecruitmentApplication> recruitmentApplicationList=super.getAll(queryFilter);
		List<RecruitmentApplication> recruitmentApplications=new ArrayList<RecruitmentApplication>();
		for(RecruitmentApplication recruitmentApplication:recruitmentApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(recruitmentApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				recruitmentApplication.setRunId(processRun.getRunId());
				recruitmentApplication.setCreator(processRun.getCreator());
				recruitmentApplication.setCreateTime(processRun.getCreatetime());
				recruitmentApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			recruitmentApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				recruitmentApplication.setProcessStatus(tasks.get(0).getName());
			}
			recruitmentApplications.add(recruitmentApplication);
		}
		return recruitmentApplications;
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
			RecruitmentApplication recruitmentApplication=getRecruitmentApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				recruitmentApplication.setId(genId);
				this.add(recruitmentApplication);
			}else{
				recruitmentApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(recruitmentApplication);
			}
			cmd.setBusinessKey(recruitmentApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RecruitmentApplication对象
	 * @param json
	 * @return
	 */
	public RecruitmentApplication getRecruitmentApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		RecruitmentApplication recruitmentApplication = (RecruitmentApplication)JSONObject.toBean(obj, RecruitmentApplication.class);
		return recruitmentApplication;
	}
	/**
	 * 保存 招聘申请 信息
	 * @param recruitmentApplication
	 */

	@WorkFlow(flowKey="recruitment_application",tableName="recruitment_application")
	public void save(RecruitmentApplication recruitmentApplication) throws Exception{
		Long id=recruitmentApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			recruitmentApplication.setId(id);
		    this.add(recruitmentApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(recruitmentApplication.getId().toString(), null , true,  "recruitment_application");
		}
		else{
		    this.update(recruitmentApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
