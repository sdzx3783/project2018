package com.hotent.makshi.service.contract;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.contract.ContractCancelApplication;
import com.hotent.makshi.dao.contract.ContractCancelApplicationDao;
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
public class ContractCancelApplicationService extends WfBaseService<ContractCancelApplication>
{
	@Resource
	private ContractCancelApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public ContractCancelApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<ContractCancelApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<ContractCancelApplication> getAll(QueryFilter queryFilter){
		List<ContractCancelApplication> contractCancelApplicationList=super.getAll(queryFilter);
		List<ContractCancelApplication> contractCancelApplications=new ArrayList<ContractCancelApplication>();
		for(ContractCancelApplication contractCancelApplication:contractCancelApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(contractCancelApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				contractCancelApplication.setRunId(processRun.getRunId());
				contractCancelApplication.setCreator(processRun.getCreator());
				contractCancelApplication.setCreateTime(processRun.getCreatetime());
				contractCancelApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			contractCancelApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				contractCancelApplication.setProcessStatus(tasks.get(0).getName());
			}
			
			contractCancelApplications.add(contractCancelApplication);
		}
		return contractCancelApplications;
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
			ContractCancelApplication contractCancelApplication=getContractCancelApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractCancelApplication.setId(genId);
				this.add(contractCancelApplication);
			}else{
				contractCancelApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractCancelApplication);
			}
			cmd.setBusinessKey(contractCancelApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractCancelApplication对象
	 * @param json
	 * @return
	 */
	public ContractCancelApplication getContractCancelApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractCancelApplication contractCancelApplication = (ContractCancelApplication)JSONObject.toBean(obj, ContractCancelApplication.class);
		return contractCancelApplication;
	}
	/**
	 * 保存 合同作废申请 信息
	 * @param contractCancelApplication
	 */

	@WorkFlow(flowKey="contract_cancel",tableName="contract_cancel_application")
	public void save(ContractCancelApplication contractCancelApplication) throws Exception{
		Long id=contractCancelApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractCancelApplication.setId(id);
		    this.add(contractCancelApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(contractCancelApplication.getId().toString(), null , true,  "contract_cancel_application");
		}
		else{
		    this.update(contractCancelApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
