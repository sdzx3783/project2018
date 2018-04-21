/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractPaymentApplication;
import com.hotent.makshi.model.contract.ContractPaymentRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractPaymentApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * @author cesc
 *合同付款关联
 */
@Component("contractPaymentFetcher")
public class ContractPaymentFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ContractPaymentApplicationService paymentApplicationService;
	@Resource
	private ContractinfoService infoService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			ContractPaymentApplication contractPaymentApplication = paymentApplicationService.getById(Long.parseLong(businessKey));
			if(contractPaymentApplication!=null){
				Contractinfo contractinfo = infoService.getContractinfoByNum(contractPaymentApplication.getContract_num());
				List<ContractPaymentRecord> contractPaymentRecordList = contractinfo.getContractPaymentRecordList();
				
				if(contractPaymentRecordList==null){
					contractPaymentRecordList= new ArrayList<ContractPaymentRecord>();
				}
				
				ProcessRun processRun=processRunService.getByBusinessKey(contractPaymentApplication.getId().toString());
				List<ProcessTask> tasks = null;
				if(BeanUtils.isNotEmpty(processRun)){
					if(processRun.getActInstId()!=null) {
						tasks = bpmService.getTasks(processRun.getActInstId());
					}
				}
				String status = "已结束";
				if(tasks!=null && tasks.size()>0){
					status=tasks.get(0).getName();
				}
				
				ContractPaymentRecord contractPaymentRecord = new ContractPaymentRecord(contractinfo.getId(),contractPaymentApplication.getApplication_time(),contractPaymentApplication.getThis_paid_money(),
						contractPaymentApplication.getApplicant(),contractPaymentApplication.getApplicantID(),status);
				
				contractPaymentRecordList.add(contractPaymentRecord);
				contractinfo.setContractPaymentRecordList(contractPaymentRecordList);
				try {
					infoService.addSubList(contractinfo);
				} catch (Exception e) {
					log.error("错误信息",e);
				}
				
				
			}
			
		}
		
	}

}
