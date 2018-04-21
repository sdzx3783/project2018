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
import com.hotent.makshi.model.hr.UserResignation;
import com.hotent.makshi.dao.hr.UserResignationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.Job;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.system.JobService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;


@Service
public class UserResignationService extends WfBaseService<UserResignation>
{
	@Resource
	private UserResignationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private JobService jobService;
	@Resource
	private UserPositionService userPositionService;
	public UserResignationService()
	{
	}
	
	@Override
	protected IEntityDao<UserResignation,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<UserResignation> getAll(QueryFilter queryFilter){
		List<UserResignation> userResignationList=super.getAll(queryFilter);
		List<UserResignation> userResignations=new ArrayList<UserResignation>();
		for(UserResignation userResignation:userResignationList){
			ProcessRun processRun=processRunService.getByBusinessKey(userResignation.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){ 
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks((processRun.getActInstId())==null?"1":processRun.getActInstId());
				userResignation.setRunId(processRun.getRunId());
				userResignation.setCreator(processRun.getCreator());
				userResignation.setCreateTime(processRun.getCreatetime());
				userResignation.setGlobalFlowNo(processRun.getGlobalFlowNo());
				SysUser user = sysUserService.getById(processRun.getCreatorId());
				if(user!=null &&user.getEntryDate()!=null){
					userResignation.setEntryTime(user.getEntryDate());
				}
				SysOrg org = sysOrgService.getPrimaryOrgByUserId(processRun.getCreatorId());
				if(org!=null &&org.getOrgName()!=null){
					userResignation.setCreatorOrgName(org.getOrgName());
				}
				List<UserPosition> positionList = userPositionService.getByUserId(processRun.getCreatorId());
				if(positionList!=null && positionList.size()>0){
					for(UserPosition pos:positionList){
						Job job = jobService.getById(pos.getJobId());
						if(job!=null && job.getJobname()!=null){
							userResignation.setJobName((userResignation.getJobName()==null?"":userResignation.getJobName())+job.getJobname());
						}
					}
				}
				
			}
			userResignation.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				userResignation.setProcessStatus(tasks.get(0).getName());
			}
			userResignations.add(userResignation);
		}
		return userResignations;
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
			UserResignation userResignation=getUserResignation(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				userResignation.setId(genId);
				this.add(userResignation);
			}else{
				userResignation.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(userResignation);
			}
			cmd.setBusinessKey(userResignation.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取UserResignation对象
	 * @param json
	 * @return
	 */
	public UserResignation getUserResignation(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		UserResignation userResignation = (UserResignation)JSONObject.toBean(obj, UserResignation.class);
		return userResignation;
	}
	/**
	 * 保存 员工离职表 信息
	 * @param userResignation
	 */

	@WorkFlow(flowKey="lzlc",tableName="user_resignation")
	public void save(UserResignation userResignation) throws Exception{
		Long id=userResignation.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			userResignation.setId(id);
		    this.add(userResignation);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(userResignation.getId().toString(), null , true,  "user_resignation");
		}
		else{
		    this.update(userResignation);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
