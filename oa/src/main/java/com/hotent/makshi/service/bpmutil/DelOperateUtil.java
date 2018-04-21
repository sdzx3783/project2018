package com.hotent.makshi.service.bpmutil;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.makshi.service.contract.ContractnumService;
import com.hotent.makshi.service.hr.LeaveApplicationService;
import com.hotent.platform.model.bpm.ProcessRun;

@Service
public class DelOperateUtil {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private JdbcTemplate jdbcTemplate;	
	@Resource
	private LeaveApplicationService leaveApplicationService;
	@Resource
	private ContractnumService contractnumService;
	@Resource
	private ContractBillingApplicationService contractBillingApplicationService;
	
	public void invoke(ProcessRun processRun){
		try {
			Long defId = processRun.getDefId();
			//合同开票流程，删除合同信息里的开票记录，跟新合同开票金额，到账金额
			if(defId==10000012900087L){
				String businessKey = processRun.getBusinessKey();
				if(StringUtil.isNotEmpty(businessKey)){
					contractBillingApplicationService.updateInvoiceMoney(businessKey);
				}
			}
			if(defId==10000001600044l){
				//合同盖章流程
				/*String sql="update w_contract_seal_application  set F_isdelete = 1 where id="+Long.valueOf(processRun.getBusinessKey());
				jdbcTemplate.execute(sql);*/
				String sql2="select F_contract_num from w_contract_seal_application WHERE id="+Long.valueOf(processRun.getBusinessKey());
				String sql3="select F_contracttype from w_contract_seal_application WHERE id="+Long.valueOf(processRun.getBusinessKey());
				String contract_num = jdbcTemplate.queryForObject(sql2, String.class);
				String contracttype = jdbcTemplate.queryForObject(sql3, String.class);
				if(StringUtils.isNotEmpty(contract_num) && StringUtils.isNotEmpty(contracttype)){
					contractnumService.addByContractnum(contract_num, contracttype);
				}
			}
			
			String projectTaskSql="UPDATE w_project_stage_task wt set wt.endCount=wt.endCount-1 WHERE wt.endCount>0 AND wt.id=("
					+"SELECT DISTINCT taskId from w_project_task_logs tl where tl.runId="+processRun.getRunId()+")";
			jdbcTemplate.execute(projectTaskSql);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}
}
