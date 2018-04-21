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
import com.hotent.makshi.model.contract.ContractBorrowApplication;
import com.hotent.makshi.model.honor.PersonalHonor;
import com.hotent.makshi.dao.contract.ContractBorrowApplicationDao;
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
public class ContractBorrowApplicationService extends WfBaseService<ContractBorrowApplication>
{
	@Resource
	private ContractBorrowApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public ContractBorrowApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<ContractBorrowApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<ContractBorrowApplication> getAll(QueryFilter queryFilter){
		List<ContractBorrowApplication> contractBorrowApplicationList=super.getAll(queryFilter);
		List<ContractBorrowApplication> contractBorrowApplications=new ArrayList<ContractBorrowApplication>();
		for(ContractBorrowApplication contractBorrowApplication:contractBorrowApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(contractBorrowApplication.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				contractBorrowApplication.setRunId(processRun.getRunId());
				contractBorrowApplication.setCreator(processRun.getCreator());
				contractBorrowApplication.setCreateTime(processRun.getCreatetime());
				contractBorrowApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			contractBorrowApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				contractBorrowApplication.setProcessStatus(tasks.get(0).getName());
			}
			contractBorrowApplications.add(contractBorrowApplication);
		}
		return contractBorrowApplications;
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
			ContractBorrowApplication contractBorrowApplication=getContractBorrowApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractBorrowApplication.setId(genId);
				this.add(contractBorrowApplication);
			}else{
				contractBorrowApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractBorrowApplication);
			}
			cmd.setBusinessKey(contractBorrowApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractBorrowApplication对象
	 * @param json
	 * @return
	 */
	public ContractBorrowApplication getContractBorrowApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractBorrowApplication contractBorrowApplication = (ContractBorrowApplication)JSONObject.toBean(obj, ContractBorrowApplication.class);
		return contractBorrowApplication;
	}
	/**
	 * 保存 合同借阅申请 信息
	 * @param contractBorrowApplication
	 */

	@WorkFlow(flowKey="contract_borrow",tableName="contract_borrow_application")
	public void save(ContractBorrowApplication contractBorrowApplication) throws Exception{
		Long id=contractBorrowApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractBorrowApplication.setId(id);
		    this.add(contractBorrowApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(contractBorrowApplication.getId().toString(), null , true,  "contract_borrow_application");
		}
		else{
		    this.update(contractBorrowApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
	
	
	public List<ContractBorrowApplication> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<ContractBorrowApplication> list = dao.getBySqlKey("getByUid", params);
		return list;
	}
	
}
