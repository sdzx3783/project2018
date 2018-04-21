/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hotent.core.api.bpm.IBpmDefinitionService;
import com.hotent.core.api.bpm.model.IBpmDefinition;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysUserService;


@Component("contractinvoicecancelFetcher")
public class ContractinvoicecancelFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ContractBillingApplicationService infoService;
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskService taskService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SysUserService userService;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private  HttpServletRequest request;
	private static final Logger logger=Logger.getLogger(ContractinvoicecancelFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		String runBusinessKey = businessKey;
		if(!StringUtil.isEmpty(businessKey)){
			List<ContractBillingApplication> list = infoService.getByCancelId(businessKey);
			if(list!=null){
				for (ContractBillingApplication cba : list) {
					if(StringUtil.isNotEmpty(cba.getTicketId())){
						String businesskey=cba.getId()+"";
						ProcessRun processRun = processRunService.getByBusinessKey(businesskey);
						if(BeanUtils.isNotEmpty(processRun)){
							if(processRun.getActInstId()==null) continue;
							List<ProcessTask> tasks = bpmService.getTasks(processRun.getActInstId());
							if(tasks!=null && tasks.size()>0){
								String taskKey = tasks.get(0).getTaskDefinitionKey();
								if(!("UserTask3").equals(taskKey)&&!("UserTask4").equals(taskKey)){
									return;
								}
								try {
									request.setAttribute("cancelvoice", true);
									complete(tasks.get(0).getId(),businesskey);
									String sql="UPDATE bpm_task_opinion  set checkstatus=46 where taskid="+tasks.get(0).getId();
									jdbcTemplate.update(sql);
								} catch (Exception e) {
									log.error("错误信息",e);
								}finally{
									ContextUtil.setCurrentUser(null);
									request.removeAttribute("cancelvoice");
								}
							}
						}
					}
				}
				String hisSql="UPDATE bpm_pro_run_his  set status=11 where businessKey="+runBusinessKey;
				jdbcTemplate.update(hisSql);
			}
		}
	}
	public void complete(String taskid,String bussinesskey) throws Exception {

		logger.info("自动办理合同开票任务....");

		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();

		String taskId = taskid;
		TaskEntity task = bpmService.getTask(taskId);
		if (task == null) {
			//resultMessage = new ResultMessage(ResultMessage.Fail, "此任务已经执行了!");
			//out.print(resultMessage);
			return;
		}
		String actDefId = task.getProcessDefinitionId();
		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		if (BpmDefinition.STATUS_INST_DISABLED.equals(bpmDefinition.getStatus())) {
			//resultMessage = new ResultMessage(ResultMessage.Fail, "流程实例已经禁止，该任务不能办理！");
			//out.print(resultMessage);
			return;
		}
		Long userId = curUser.getUserId();

		ProcessCmd taskCmd = getProcessCmd(taskId,bussinesskey,curUser);

		taskCmd.setCurrentUserId(userId.toString());

		String assignee = task.getAssignee();
		// 非管理员,并且没有任务的权限。
		boolean isAdmin = taskCmd.getIsManage().shortValue() == 1;
		//if (!isAdmin) {
		//	boolean rtn = processRunService.getHasRightsByTask(new Long(taskId), userId);
		//	if (!rtn) {
				//resultMessage = new ResultMessage(ResultMessage.Fail, "对不起,你不是这个任务的执行人,不能处理此任务!");
				//out.print(resultMessage);
		//	return;
		//	}
		//}

		// 记录日志。
		logger.info(taskCmd.toString());
		//if (ServiceUtil.isAssigneeNotEmpty(assignee) && !task.getAssignee().equals(userId.toString()) && !isAdmin) {
			//resultMessage = new ResultMessage(ResultMessage.Fail, "该任务已被其他人锁定");
		//} else {
			//String errorUrl = RequestUtil.getErrorUrl(request);
			//String ip = RequestUtil.getIpAddr(request);
			try {
				processRunService.nextProcess(taskCmd);
				//resultMessage = new ResultMessage(ResultMessage.Success, "任务成功完成!");

			} catch (Exception ex) {
				log.error("错误信息",ex);
			}
		}
	//}

	private ProcessCmd getProcessCmd(String taskid,String businesskey,SysUser user) {
		ProcessCmd cmd = new ProcessCmd();
		String destTask="EndEvent1";
		//String lastDestTaskId="EndEvent1";
		String taskId=taskid;
		String formData="{'main':{'fields':{'enterTime':'','enterNumber':''}},'sub':[],'opinion':[]}";
		String actDefId="contract_billing:1:10000012900086";
		String businessKey=businesskey;
		String voteContent="自动审批";
		String voteAgree="46";
		String isManage="0";
		String startNode="UserTask1";
		Long relRunId=0L;
		String formKey="contract_billing_application";
		String curUserName=user.getFullname();
		String back="0";
		String curUserId=user.getUserId()+"";
		String defId="10000012900087";
		String undefined="会计确认到账,";
		String currentNode="UserTask4";

		cmd.setTaskId(taskId);
		cmd.setDestTask(destTask);
		cmd.setFormData(formData);
		Map<String,String> formDataMap=new HashMap<>();
		//{taskId=10000000470141, formKey=contract_billing_application, curUserName=徐钰, businessKey=10000000470079, back=0, actDefId=contract_billing:1:10000012900086, startNode=UserTask1, curUserId=10000000010009, defId=10000012900087, formData={"main":{"fields":{"enterTime":"2017-10-18","enterNumber":"100.00"}},"sub":[],"opinion":[]}, undefined=会计确认到账,, voteContent=同意, voteAgree=1, currentNode=UserTask4, isManage=0}
		formDataMap.put("taskId", taskId);
		formDataMap.put("formKey", formKey);
		formDataMap.put("curUserName", curUserName);
		formDataMap.put("businessKey", businessKey);
		formDataMap.put("back", back);
		formDataMap.put("actDefId", actDefId);
		formDataMap.put("startNode", startNode);
		formDataMap.put("curUserId", curUserId);
		formDataMap.put("defId", defId);
		formDataMap.put("formData", formData);
		formDataMap.put("undefined", undefined);
		formDataMap.put("voteContent", voteContent);
		formDataMap.put("voteAgree", voteAgree);
		formDataMap.put("currentNode", currentNode);
		formDataMap.put("isManage", isManage);
		cmd.setFormDataMap(formDataMap);
		cmd.setActDefId(actDefId);
		IBpmDefinitionService bpmDefinitionService = (IBpmDefinitionService)AppUtil.getBean(IBpmDefinitionService.class);
		IBpmDefinition bpmDefinition = null;
		bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		cmd.addTransientVar("bpm_definition", bpmDefinition);
		if (BeanUtils.isNotEmpty(bpmDefinition)) {
			String informType = "";
			informType = bpmDefinition.getInformType();
			cmd.setInformType(informType);
		}
		cmd.setBusinessKey(businessKey);
		cmd.setBack(Integer.parseInt(back));
		cmd.setVoteContent(voteContent);
		cmd.setVoteAgree(new Short(voteAgree));
		cmd.setIsManage(new Short(isManage));
		cmd.setStartNode(startNode);
		cmd.setRelRunId(relRunId);
		return cmd;
	}


}
