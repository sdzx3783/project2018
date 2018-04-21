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
import com.hotent.makshi.model.contract.ContractChangeApplication;
import com.hotent.makshi.dao.contract.ContractChangeApplicationDao;
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
public class ContractChangeApplicationService extends WfBaseService<ContractChangeApplication>
{
	@Resource
	private ContractChangeApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public ContractChangeApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<ContractChangeApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<ContractChangeApplication> getAll(QueryFilter queryFilter){
		List<ContractChangeApplication> contractChangeApplicationList=super.getAll(queryFilter);
		List<ContractChangeApplication> contractChangeApplications=new ArrayList<ContractChangeApplication>();
		for(ContractChangeApplication contractChangeApplication:contractChangeApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(contractChangeApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				contractChangeApplication.setRunId(processRun.getRunId());
				contractChangeApplication.setCreator(processRun.getCreator());
				contractChangeApplication.setCreateTime(processRun.getCreatetime());
				contractChangeApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			contractChangeApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				contractChangeApplication.setProcessStatus(tasks.get(0).getName());
			}
			contractChangeApplications.add(contractChangeApplication);
		}
		return contractChangeApplications;
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
			ContractChangeApplication contractChangeApplication=getContractChangeApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractChangeApplication.setId(genId);
				this.add(contractChangeApplication);
			}else{
				contractChangeApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractChangeApplication);
			}
			cmd.setBusinessKey(contractChangeApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractChangeApplication对象
	 * @param json
	 * @return
	 */
	public ContractChangeApplication getContractChangeApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractChangeApplication contractChangeApplication = (ContractChangeApplication)JSONObject.toBean(obj, ContractChangeApplication.class);
		return contractChangeApplication;
	}
	/**
	 * 保存 合同人员变更申请 信息
	 * @param contractChangeApplication
	 */

	@WorkFlow(flowKey="contract_change",tableName="contract_change_application")
	public void save(ContractChangeApplication contractChangeApplication) throws Exception{
		Long id=contractChangeApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractChangeApplication.setId(id);
		    this.add(contractChangeApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(contractChangeApplication.getId().toString(), null , true,  "contract_change_application");
		}
		else{
		    this.update(contractChangeApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}

	public List<ContractChangeApplication> getList(String contractNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNum", contractNum);
		return dao.getBySqlKey("getByContractNum",params);
	}
}
