/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractSealApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.contract.ContractSealApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;

/**
 * @author cesc
 *合同盖章关联
 */
@Component("contractSealFetcher")
public class ContractSealFetcher implements IFetcher {
	@Resource
	private ContractSealApplicationService sealApplicationService;
	@Resource
	private ContractinfoService contractinfoService;
	@Resource
	private FlowToEntityService flowToEntityService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private ProcessRunService processRunService;
	
	private static Logger logger=Logger.getLogger(ContractSealFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		String message = "流程数据同步失败";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				ContractSealApplication contractSeal = sealApplicationService.getById(Long.parseLong(businessKey));
				//通过合同编号、合同类型查找合同信息
				String contractId = contractSeal.getContract_num();
				String type = contractSeal.getContracttype();
				Contractinfo contractInfo = contractinfoService.getByTypeAndNum(type,contractId);
				String file_third = flowToEntityService.flowToEntityFile(contractSeal.getFile_third());
				//获取审批意见
				TaskOpinion taskOpinion = getLastOption(businessKey,null);
				TaskOpinion instockTaskOpinion = getLastOption(businessKey,"UserTask8");
				String remark = taskOpinion.getOpinion();
				if(null!=contractInfo){
					contractInfo.setIsrecovery("1");
					contractInfo.setFile_third(file_third);
					contractInfo.setInstock_date(new Date());
					contractInfo.setRemark(remark);
					contractInfo.setInstock_sign(instockTaskOpinion.getExeFullname());
					contractInfo.setSure_sign(ContextUtil.getCurrentUser().getFullname());
					contractInfo.setFilecopies(contractSeal.getFile_copies());
					contractInfo.setFj_sencond_copies(contractSeal.getFj_second_copies());
					try {
						contractinfoService.save(contractInfo);
					} catch (Exception e) {
						message="保存合同信息失败";
						throw new Exception(message);
					}
				}else{
					message="未找到合同信息";
					throw new Exception(message);
				}
				
			}else{
				message="未找到流程数据";
				throw new Exception(message);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(message);
		}
		
	}
	
	
	public TaskOpinion getLastOption(String businessKey,String userTask){
		ProcessRun processRun = processRunService.getByBusinessKey(businessKey);
		String actInstId = processRun.getActInstId();
		List<TaskOpinion> list = taskOpinionService.getByActInstId(actInstId,true,userTask);
		Date endTime =null;
		Date endTimeTem = null;
		Long optionId = 0L;
		for(TaskOpinion taskOpinion : list){
			if(taskOpinion.getCheckStatus() == 1){
				 endTimeTem = taskOpinion.getEndTime();
				 if(endTime==null || endTime.getTime()<endTimeTem.getTime()){
					 endTime = endTimeTem;
					 optionId = taskOpinion.getOpinionId();
				 }
			}
		}
		TaskOpinion taskOpinion = taskOpinionService.getById(optionId);
		return taskOpinion;
	}

}
