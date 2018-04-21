/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.contract.ContractBillingRecordDao;
import com.hotent.makshi.dao.contract.EnterinfoDao;
import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.contract.Enterinfo;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * @author 
 *跟新到账时间和到账金额
 */
@Component("contractBillingFetcher")
public class ContractBillingFetcher implements IFetcher {
	@Resource
	private ContractBillingApplicationService billingApplicationService;
	@Resource
	private ContractinfoService infoService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ContractBillingRecordDao recordDao;
	@Resource
	private EnterinfoDao enterinfoDao;
	
	
	@Resource
	private  HttpServletRequest request;
	
	private static Logger logger=Logger.getLogger(ContractBillingApplicationService.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		
		Boolean hascancel=false;
		Object attribute = request.getAttribute("cancelvoice");
		if(attribute!=null){
			hascancel=(Boolean) attribute;
		}
		String message = "流程数据同步失败";
		try {
			if(!StringUtil.isEmpty(businessKey) && !hascancel.booleanValue()){
				//获取流程数据
				ContractBillingApplication contractBillingApplication = billingApplicationService.getById(Long.parseLong(businessKey));
				if(contractBillingApplication!=null){
					//通过合同编号获取合同信息
					Contractinfo contractinfo = infoService.getContractinfoByNum(contractBillingApplication.getContract_num());
					if(null == contractinfo){
						message="未找到合同编号对应的合同信息";
						throw new Exception(message);
					}
					ProcessRun processRun=processRunService.getByBusinessKey(contractBillingApplication.getId().toString());
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
					//获取流程子表的记录(通过发起人,到账金额,到账时间匹配）
					List<Enterinfo> applicationSubList = enterinfoDao.getByMainId(Long.parseLong(businessKey));
					//获取开票日期
					List<ContractBillingRecord> existRecordList = recordDao.getByLinkId(Long.parseLong(businessKey));
					Date billingDate = null;
					if(existRecordList.size()>0){
						billingDate = existRecordList.get(0).getBilling_date();
					}
					//删除原有信息
					recordDao.delBySqlKey("delByLinkId", contractBillingApplication.getId());
					//获取合同信息开票记录
					List<ContractBillingRecord> contractBillingRecordList = infoService.getContractBillingRecordList(contractinfo.getId());
					//现有累计到账金额
					Double sumEnterNumber = contractinfo.getArrival_amount();
					//将流程子表信息添加到合同信息开票记录中
					for(Enterinfo enterinfo : applicationSubList){
						ContractBillingRecord contractBillingRecord	 = new ContractBillingRecord();
						contractBillingRecord.setLinkId(contractBillingApplication.getId());
						contractBillingRecord.setArrival_money(enterinfo.getEnterNumber().toString());
						contractBillingRecord.setPayment_date(enterinfo.getEnterTime());
						contractBillingRecord.setStatus(status);
						contractBillingRecord.setBilling_date(billingDate);
						contractBillingRecord.setInvoice_money(contractBillingApplication.getBilling_money());
						contractBillingRecord.setOperator(contractBillingApplication.getApplicant());
						contractBillingRecord.setOperatorID(contractBillingApplication.getApplicantID());
						contractBillingRecord.setBearer(contractBillingApplication.getTicketTaker());
						contractBillingRecord.setBearerID(contractBillingApplication.getTicketTakerID());
						sumEnterNumber += enterinfo.getEnterNumber()*0.0001;
						contractBillingRecordList.add(contractBillingRecord);
					}
					/*						String userId = contractBillingApplication.getApplicantID();
					Date arrivelTime = contractBillingApplication.getEnterTime();
					Double enterNumer = contractBillingApplication.getEnterNumber();
				for(ContractBillingRecord contractBillingRecord:contractBillingRecordList){
						//遍历查找对应的记录
						if( null != contractBillingRecord.getPayment_date() || null != contractBillingRecord.getArrival_money()){
							continue;
						}
						if(userId.equals(contractBillingRecord.getOperatorID()) ){
							contractBillingRecord.setPayment_date(arrivelTime);
							contractBillingRecord.setArrival_money(enterNumer==null?"0":enterNumer.toString());
							contractBillingRecord.setStatus(status);
						}
					}
					//本次到账金额
					Double enterNum = contractBillingApplication.getEnterNumber();
					if(null==enterNum){
						enterNum = 0.0;
					}
					//跟新到账时间
					//contractinfo.setArrival_time(arrivelTime);*/
					//累计到账金额
					contractinfo.setArrival_amount(sumEnterNumber);
					contractinfo.setContractBillingRecordList(contractBillingRecordList);
					try {
						infoService.save(contractinfo);
					} catch (Exception e) {
						message="保存合同信息失败";
						throw new Exception(message);
					}
				}
				
			}else if(!StringUtil.isEmpty(businessKey) && hascancel.booleanValue()){
				billingApplicationService.updateInvoiceMoney(businessKey);
			}
			/*else{
				message="未找到流程数据";
				throw new Exception(message);
			}*/
		}catch (Exception e) {
				logger.error(message);
				throw new Exception(message);
			}
			
	}

}

