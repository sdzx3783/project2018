package com.hotent.makshi.service.bpmutil;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.makshi.service.contract.ContractnumService;
import com.hotent.makshi.service.hr.LeaveApplicationService;
import com.hotent.platform.model.bpm.ProcessRun;

@Service
public class RejectOperateUtil {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private LeaveApplicationService leaveApplicationService;
	@Resource
	private ContractnumService contractnumService;

	public void invoke(ProcessRun processRun) {
		try {
			Long defId = processRun.getDefId();
			/*
			 * if(defId==10000001600044l){ //合同盖章流程 String sql="update w_contract_seal_application  set F_isdelete = 1 where id="+Long.valueOf(processRun.getBusinessKey()); jdbcTemplate.execute(sql);
			 * String sql2="select F_contract_num from w_contract_seal_application WHERE id="+Long.valueOf(processRun.getBusinessKey()); String
			 * sql3="select F_contracttype from w_contract_seal_application WHERE id="+Long.valueOf(processRun.getBusinessKey()); String contract_num = jdbcTemplate.queryForObject(sql2, String.class);
			 * String contracttype = jdbcTemplate.queryForObject(sql3, String.class); if(StringUtils.isNotEmpty(contract_num) && StringUtils.isNotEmpty(contracttype)){
			 * contractnumService.addByContractnum(contract_num, contracttype); } }
			 */

			if (10000009830004l == defId) {
				// 请假流程需要维护w_anuual_leave的hasLeaveCurrentyear（已请假）字段
				// 请假流程年假销假更改 不需要再次回收了
				/*
				 * LeaveApplication leaveApplication = leaveApplicationService.getById(Long.valueOf(processRun.getBusinessKey())); if(leaveApplication!=null &&
				 * "年假".equals(leaveApplication.getLeave_type())){ String leave_days = leaveApplication.getLeave_days();
				 * 
				 * String sql1="UPDATE w_anuual_leave set hasleaveCurrentyear=hasleaveCurrentyear-"+Double.valueOf(leave_days).doubleValue() +",utime=NOW() where userid="+processRun.getCreatorId();
				 * jdbcTemplate.execute(sql1); }
				 */
			}
		} catch (Exception e) {
			log.error("错误信息", e);
		}
	}
}
