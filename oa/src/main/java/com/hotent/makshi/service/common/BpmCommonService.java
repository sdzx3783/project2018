package com.hotent.makshi.service.common;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.contract.EnterinfoDao;
import com.hotent.makshi.dao.qualification.JyzzDao;
import com.hotent.makshi.model.contract.Enterinfo;
import com.hotent.makshi.model.qualification.CompanyQualificationCertificate;
import com.hotent.makshi.model.qualification.Jyzz;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.makshi.service.contract.ContractSealApplicationService;
import com.hotent.makshi.service.hr.EntryService;
import com.hotent.makshi.service.qualification.CompanyQualificationCertificateService;

@Service
public class BpmCommonService {
	private final Logger log = Logger.getLogger(this.getClass());

	public static final String COMPANY_QUALIFICATION = "gsglzzyjjylc:1:30000001631511";
	
	public static final String CONTRACT_BILING = "gsglzzyjjylc:1:30000001631511";
	
	public static final String ENTRY = "rzlc:1:10000000050326";

	@Resource
	private JyzzDao jyzzDao;
	@Resource
	private CompanyQualificationCertificateService companyQualificationCertificateService;
	@Resource
	private ContractSealApplicationService contractSealApplicationService;
	@Resource
	private ContractBillingApplicationService contractBillingApplicationService;
	@Resource
	private EntryService entryService;
	@Resource
	private EnterinfoDao enterinfoDao;

	public void updateTable(ProcessCmd processCmd) throws Exception {
		String actDefId = processCmd.getActDefId();
		String businessKey = processCmd.getBusinessKey();
		// 公司各类资质借用
		if (!StringUtil.isEmpty(actDefId) && COMPANY_QUALIFICATION.equals(actDefId)) {
			handleCompanyQualificationBorrow(businessKey);
		}
		// 合同开票
		if (!StringUtil.isEmpty(actDefId) && CONTRACT_BILING.equals(actDefId)) {
			handleContractBiling(businessKey);
		}
	}

	private void handleContractBiling(String businessKey) {
		List<Enterinfo> enterInfoList = enterinfoDao.getByMainId(Long.valueOf(businessKey));
	}

	private void handleCompanyQualificationBorrow(String businessKey) throws Exception {
		List<Jyzz> list = jyzzDao.getByMainId(Long.valueOf(businessKey));
		for (Jyzz jyzz : list) {
			String companyQualificationId = jyzz.getListId();
			String listBack = jyzz.getListBack();
			CompanyQualificationCertificate companyQualificationCertificate = companyQualificationCertificateService.getById(Long.valueOf(companyQualificationId));
			if (!StringUtil.isEmpty(listBack) && Integer.valueOf(listBack) == 1) {
				companyQualificationCertificate.setIsborrowed("0");
			}
			if (StringUtil.isEmpty(listBack)) {
				companyQualificationCertificate.setIsborrowed("1");
			}
			companyQualificationCertificateService.save(companyQualificationCertificate);
		}
	}

	public void handleBpmCommon(DelegateTask delegateTask, ProcessCmd processCmd) throws Exception {
		try {
			contractSealApplicationService.updInfo(delegateTask, processCmd);
			contractBillingApplicationService.updInfo(delegateTask, processCmd);
			handCompanyQualificatin(delegateTask, processCmd);
		//	handEntry(delegateTask, processCmd);
		} catch (Exception e) {
			throw new Exception("同步信息失败", e);
		}
	}

	private void handEntry(DelegateTask delegateTask, ProcessCmd processCmd) {
		Short voteAgree = processCmd.getVoteAgree();
		String actDefId = processCmd.getActDefId();
		String taskName = delegateTask.getName();
		String businessKey = processCmd.getBusinessKey();
		// 判断是否为入职流程
		if(StringUtil.isEmpty(actDefId)){
			actDefId = delegateTask.getProcessDefinitionId();
		}
		if (!StringUtil.isEmpty(actDefId) && actDefId.equals(ENTRY)  && taskName.equals("验原件")) {
			try {
				if(voteAgree == 1){
					//同意则同步
					entryService.addEntry(businessKey);
				}else{
					entryService.delEntry(businessKey);
				}
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
	}
	
	private void handCompanyQualificatin(DelegateTask delegateTask, ProcessCmd processCmd) {
		Short voteAgree = processCmd.getVoteAgree();
		String actDefId = processCmd.getActDefId();
		String taskName = delegateTask.getName();
		String businessKey = processCmd.getBusinessKey();
		// 判断是否为公司各类资质借用
		if (!StringUtil.isEmpty(actDefId) && actDefId.equals(COMPANY_QUALIFICATION) && voteAgree == 1 && taskName.equals("确认借出")) {
			try {
				handleCompanyQualificationBorrow(businessKey);
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
	}

}
