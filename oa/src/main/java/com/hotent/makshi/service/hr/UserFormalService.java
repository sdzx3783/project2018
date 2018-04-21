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
import com.hotent.makshi.model.hr.UserFormal;
import com.hotent.makshi.dao.hr.UserFormalDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;


@Service
public class UserFormalService extends WfBaseService<UserFormal>
{
	@Resource
	private UserFormalDao dao;
	
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
	public UserFormalService()
	{
	}
	
	@Override
	protected IEntityDao<UserFormal,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<UserFormal> getAll(QueryFilter queryFilter){
		List<UserFormal> userFormalList=super.getAll(queryFilter);
		List<UserFormal> userFormals=new ArrayList<UserFormal>();
		for(UserFormal userFormal:userFormalList){
			ProcessRun processRun=processRunService.getByBusinessKey(userFormal.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){ 
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				userFormal.setRunId(processRun.getRunId());
				userFormal.setCreator(processRun.getCreator());
				userFormal.setCreateTime(processRun.getCreatetime());
				userFormal.setGlobalFlowNo(processRun.getGlobalFlowNo());
				SysUser user = sysUserService.getById(processRun.getCreatorId());
				if(user!=null &&user.getEntryDate()!=null){
					userFormal.setEntryTime(user.getEntryDate());
				}
				SysOrg org = sysOrgService.getPrimaryOrgByUserId(processRun.getCreatorId());
				if(org!=null &&org.getOrgName()!=null){
					userFormal.setCreatorOrgName(org.getOrgName());
				}
			}
			userFormal.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				userFormal.setProcessStatus(tasks.get(0).getName());
			}
			userFormals.add(userFormal);
		}
		return userFormals;
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
			UserFormal userFormal=getUserFormal(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				userFormal.setId(genId);
				this.add(userFormal);
			}else{
				userFormal.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(userFormal);
			}
			cmd.setBusinessKey(userFormal.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取UserFormal对象
	 * @param json
	 * @return
	 */
	public UserFormal getUserFormal(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		UserFormal userFormal = (UserFormal)JSONObject.toBean(obj, UserFormal.class);
		return userFormal;
	}
	/**
	 * 保存 员工转正表 信息
	 * @param userFormal
	 */

	@WorkFlow(flowKey="zzlc",tableName="user_formal")
	public void save(UserFormal userFormal) throws Exception{
		Long id=userFormal.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			userFormal.setId(id);
		    this.add(userFormal);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(userFormal.getId().toString(), null , true,  "user_formal");
		}
		else{
		    this.update(userFormal);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
