package com.hotent.makshi.service.seal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.seal.SealUseApplication;
import com.hotent.makshi.dao.seal.SealUseApplicationDao;
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
public class SealUseApplicationService extends WfBaseService<SealUseApplication>
{
	@Resource
	private SealUseApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public SealUseApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<SealUseApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<SealUseApplication> getAll(QueryFilter queryFilter){
		List<SealUseApplication> sealUseApplicationList=super.getAll(queryFilter);
		List<SealUseApplication> sealUseApplications=new ArrayList<SealUseApplication>();
		for(SealUseApplication sealUseApplication:sealUseApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(sealUseApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(null!= processRun.getRunId()){
					sealUseApplication.setRunId(processRun.getRunId());
				}
				if(null!= processRun.getActInstId()){
					tasks = bpmService.getTasks(processRun.getActInstId());
				}
				if(tasks!=null && tasks.size()>0){
					sealUseApplication.setStatus(tasks.get(0).getName());
				}else{
					sealUseApplication.setStatus("流程已结束");
				}
				sealUseApplication.setGlowbalNo(processRun.getGlobalFlowNo());
				sealUseApplication.setCreateTime(processRun.getCreatetime());
			}
			sealUseApplications.add(sealUseApplication);
		}
		return sealUseApplications;
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
			SealUseApplication sealUseApplication=getSealUseApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				sealUseApplication.setId(genId);
				this.add(sealUseApplication);
			}else{
				sealUseApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(sealUseApplication);
			}
			cmd.setBusinessKey(sealUseApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取SealUseApplication对象
	 * @param json
	 * @return
	 */
	public SealUseApplication getSealUseApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		SealUseApplication sealUseApplication = (SealUseApplication)JSONObject.toBean(obj, SealUseApplication.class);
		return sealUseApplication;
	}
	/**
	 * 保存 公章使用申请表 信息
	 * @param sealUseApplication
	 */

	@WorkFlow(flowKey="seal_use",tableName="seal_use_application")
	public void save(SealUseApplication sealUseApplication) throws Exception{
		Long id=sealUseApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			sealUseApplication.setId(id);
		    this.add(sealUseApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(sealUseApplication.getId().toString(), null , true,  "seal_use_application");
		}
		else{
		    this.update(sealUseApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
