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
import com.hotent.makshi.model.hr.LeaveApplication;
import com.hotent.makshi.dao.hr.LeaveApplicationDao;
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
public class LeaveApplicationService extends WfBaseService<LeaveApplication>
{
	@Resource
	private LeaveApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public LeaveApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<LeaveApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<LeaveApplication> getAll(QueryFilter queryFilter){
		List<LeaveApplication> leaveApplicationList=super.getAll(queryFilter);
		List<LeaveApplication> leaveApplications=new ArrayList<LeaveApplication>();
		for(LeaveApplication leaveApplication:leaveApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(leaveApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				leaveApplication.setRunId(processRun.getRunId());
				leaveApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			leaveApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				leaveApplication.setProcessStatus(tasks.get(0).getName());
			}
			leaveApplications.add(leaveApplication);
		}
		return leaveApplications;
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
			LeaveApplication leaveApplication=getLeaveApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				leaveApplication.setId(genId);
				this.add(leaveApplication);
			}else{
				leaveApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(leaveApplication);
			}
			cmd.setBusinessKey(leaveApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取LeaveApplication对象
	 * @param json
	 * @return
	 */
	public LeaveApplication getLeaveApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		LeaveApplication leaveApplication = (LeaveApplication)JSONObject.toBean(obj, LeaveApplication.class);
		return leaveApplication;
	}
	/**
	 * 保存 请假申请流程表单 信息
	 * @param leaveApplication
	 */

	@WorkFlow(flowKey="leaveApplicanion",tableName="leave_application")
	public void save(LeaveApplication leaveApplication) throws Exception{
		Long id=leaveApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			leaveApplication.setId(id);
		    this.add(leaveApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(leaveApplication.getId().toString(), null , true,  "leave_application");
		}
		else{
		    this.update(leaveApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
