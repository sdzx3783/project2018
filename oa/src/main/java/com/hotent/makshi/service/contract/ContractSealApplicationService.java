package com.hotent.makshi.service.contract;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import com.hotent.core.annotion.WorkFlow;
import com.hotent.core.bpm.BpmResult;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.contract.ContractSealApplicationDao;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumYear;
import com.hotent.makshi.model.contract.ContractSealApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.platform.service.system.SysOrgService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


@Service
public class ContractSealApplicationService extends WfBaseService<ContractSealApplication>
{
	@Resource
	private ContractSealApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ContractinfoService infoService;
	@Resource
	private FlowToEntityService flowToEntityService;
	@Resource
	private ContractinfoService contractInfoService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private ContractNumAdminService contractNumAdminService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private ContractNumYearService contractNumYearService;
	@Resource
	private ContractnumService contractnumService;
	
	public ContractSealApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<ContractSealApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<ContractSealApplication> getAll(QueryFilter queryFilter){
		List<ContractSealApplication> contractSealApplicationList=super.getAll(queryFilter);
		/*List<ContractSealApplication> contractSealApplications=new ArrayList<ContractSealApplication>();
		for(ContractSealApplication contractSealApplication:contractSealApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(contractSealApplication.getId().toString());
			List<ProcessTask> tasks = null;
			//List<String> activeList = bpmService.getActiveActIdsByProcessInstanceId(processRun.getActInstId());
			if(BeanUtils.isNotEmpty(processRun)){ 
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				contractSealApplication.setRunId(processRun.getRunId());
				contractSealApplication.setCreator(processRun.getCreator());
				contractSealApplication.setCreateTime(processRun.getCreatetime());
				contractSealApplication.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			contractSealApplication.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				contractSealApplication.setProcessStatus(tasks.get(0).getName());
			}
			contractSealApplications.add(contractSealApplication);
		}*/
		return contractSealApplicationList;
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
			ContractSealApplication contractSealApplication=getContractSealApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractSealApplication.setId(genId);
				this.add(contractSealApplication);
			}else{
				contractSealApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractSealApplication);
			}
			cmd.setBusinessKey(contractSealApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractSealApplication对象
	 * @param json
	 * @return
	 */
	public ContractSealApplication getContractSealApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractSealApplication contractSealApplication = (ContractSealApplication)JSONObject.toBean(obj, ContractSealApplication.class);
		return contractSealApplication;
	}
	/**
	 * 保存 合同盖章申请 信息
	 * @param contractSealApplication
	 */

	@WorkFlow(flowKey="contract_seal",tableName="contract_seal_application")
	public void save(ContractSealApplication contractSealApplication) throws Exception{
		Long id=contractSealApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractSealApplication.setId(id);
		    this.add(contractSealApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(contractSealApplication.getId().toString(), null , true,  "contract_seal_application");
		}
		else{
		    this.update(contractSealApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}

	public void updInfo(DelegateTask delegateTask, ProcessCmd processCmd) throws Exception{
		ResultMessage resultMessage = null;
		try {
			Short voteAgree = processCmd.getVoteAgree();
			//加签提交，当加签人反馈时actDefId为null actDefId = delegateTask.getProcessDefinitionId();
			String actDefId = processCmd.getActDefId();
			if(StringUtil.isEmpty(actDefId)){
				actDefId = delegateTask.getProcessDefinitionId();
			}
			String taskName = delegateTask.getName();
			Integer nodeId = 0;
			//判断是否为合同盖章
			if (actDefId == null || !actDefId.equals("contract_seal:3:10000001600048") ){
				return;
			}
			recovery(processCmd);
			//判断是否为办公室盖章
			if(taskName != null && taskName.equals("办公室盖章")){
			    nodeId = 1;
			}
			//判断是否为合同归档
			if(taskName != null && taskName.equals("合同归档")){
			    nodeId = 2;
			}
			if(nodeId == 0){
				return;
			}
			//办公室盖章审批通过，通过businessKey跟新数据
			if(nodeId==1 && voteAgree==1){
				contractInfoAdd(processCmd);
			}
			//合同归档通过
			if(nodeId==2 && voteAgree==1){
				contractInfoAdd(processCmd);
			}
			//合同归档驳回,删除合同信息
			if(nodeId==2 && voteAgree!=1){
				contractInfoDel(processCmd);
			}
		} catch (Exception e) {
			String message = ExceptionUtil.getExceptionMessage(e);
			resultMessage = new ResultMessage(ResultMessage.Fail, message);
		}
	}

	//当驳回到发起人环节，合同编号进行回收
	public void recovery(ProcessCmd processCmd) throws Exception{
		ResultMessage resultMessage = null;
		try {
			String businessKey = processCmd.getBusinessKey();
			ContractSealApplication contractSeal = dao.getById(Long.valueOf(businessKey));
			if(null==contractSeal || StringUtil.isEmpty(contractSeal.getContract_num())){
				return;
			}
			Integer isBack = processCmd.isBack();
			// 驳回到发起人
		    if (isBack == BpmConst.TASK_BACK_TOSTART) {
		    	contractnumService.addByContractnum(contractSeal.getContract_num(),contractSeal.getContracttype());
		    }
			// 驳回
		    if (isBack == BpmConst.TASK_BACK) {
		    	//下一节点为申请
		    	if(processCmd.getDestTask().equals("UserTask7")){
		    		contractnumService.addByContractnum(contractSeal.getContract_num(),contractSeal.getContracttype());
		    	}
		    }
		} catch (Exception e) {
			String message = ExceptionUtil.getExceptionMessage(e);
			resultMessage = new ResultMessage(ResultMessage.Fail, message);
		}
	}
	
	
	
	
	public void contractInfoAdd(ProcessCmd processCmd) throws Exception{
		ResultMessage resultMessage = null;
		String businessKey = processCmd.getBusinessKey();
		ContractSealApplication contractSeal = dao.getById(Long.valueOf(businessKey));
		//区分是否为采购合同
		String contract_type = "0";
		contract_type = setContractType(contractSeal);
		//添加合同归属部门id
		String department = contractSeal.getContract_department();
		List<SysOrg> sysOrgList = sysOrgService.getByOrgName(department);
		if(sysOrgList.size()>0){
			contractSeal.setContract_departmentID(sysOrgList.get(0).getOrgId().toString());
		}
		String contractNum = contractSeal.getContract_num();
		Contractinfo existContractInfo = contractInfoService.getContractinfoByNum(contractNum);
		if(contractSeal!=null){
			String contract_money = contractSeal.getContract_money();
			if(contract_money==null || !(contract_money.length()>0)){
				contract_money = "0.000000";
			}
			String settlement_money  = contractSeal.getSettlement_money();
			if(settlement_money==null || !(settlement_money.length()>0)){
				settlement_money = "0.000000";
			}
			String project_bid_floorprice = contractSeal.getProject_bid_floorprice();
			if(project_bid_floorprice==null || !(project_bid_floorprice.length()>0)){
				project_bid_floorprice = "0.000000";
			}
			String project_bid_price = contractSeal.getProject_bid_price();
			if(project_bid_price==null || !(project_bid_price.length()>0)){
				project_bid_price = "0.000000";
			}
			String total_investment = contractSeal.getTotal_investment();
			if(total_investment==null || !(total_investment.length()>0)){
				total_investment = "0.000000";
			}
			String invoice_amount = contractSeal.getInvoice_amount();
			if(invoice_amount==null || !(invoice_amount.length()>0)){
				invoice_amount = "0.000000";
			}
			Contractinfo contractinfo = new Contractinfo(contractSeal.getContract_departmentID(), contractSeal.getContract_num(), 
					Double.valueOf(contract_money), contractSeal.getContract_name(), Double.valueOf(settlement_money), 
					contract_type,contractSeal.getContracttype(),contractSeal.getThirdcontracttype(), Double.valueOf(project_bid_floorprice), contractSeal.getContract_status(), 
					Double.valueOf(project_bid_price), contractSeal.getFirst_party(), contractSeal.getRate(), contractSeal.getSecond_party(),
					contractSeal.getProject_content(), contractSeal.getPayment_type(), contractSeal.getProject_scale(), contractSeal.getSigning_time(), 
					contractSeal.getIsrecord(), contractSeal.getProject_director(), contractSeal.getIssave(), contractSeal.getProject_status(), contractSeal.getIsrecovery(),
					contractSeal.getContract_department(), contractSeal.getInout(), contractSeal.getProject_place(), contractSeal.getStart_time(), contractSeal.getOwner_name(),
					contractSeal.getEnd_time(), contractSeal.getProject_origin(), contractSeal.getProject_leader(), contractSeal.getBidding_platform(), contractSeal.getContract_handler(),
					contractSeal.getBidding_method(), contractSeal.getContract_reviewer(), contractSeal.getContract_authorized_person(), contractSeal.getRemark(), contractSeal.getUpdater(), 
					contractSeal.getCreater(), contractSeal.getUpdate_time(), contractSeal.getCreate_time(), contractSeal.getCustodian(), Double.valueOf(total_investment), flowToEntityService.flowToEntityFile(contractSeal.getFile()),
					contractSeal.getNo(), contractSeal.getType(),Double.valueOf(invoice_amount),
					contractSeal.getProject_directorID(),contractSeal.getContract_handlerID(),
					contractSeal.getContract_reviewerID(),contractSeal.getProject_leaderID(),contractSeal.getContract_authorized_personID(),contractSeal.getUpdaterID(),contractSeal.getCreaterID(),
					contractSeal.getCustodianID(),contractSeal.getProjiect_name(),contractSeal.getSections_name(),contractSeal.getFile_second(),contractSeal.getFile_third());
					contractinfo.setProject_chapter(contractSeal.getHaveProjectChapter());
					contractinfo.setFj_sencond_copies(contractSeal.getFj_second_copies());
			if(null!=existContractInfo){
				contractinfo.setId(existContractInfo.getId());
			}
			try {
				infoService.save(contractinfo);
			} catch (Exception e) {
				throw new RuntimeException(e);
				
				/*String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);*/
			}
		}
	}
	
	public void contractInfoDel(ProcessCmd processCmd){
		String businessKey = processCmd.getBusinessKey();
		ContractSealApplication contractSeal = dao.getById(Long.parseLong(businessKey));
		String contractNum = contractSeal.getContract_num();
		Contractinfo existContractInfo = contractInfoService.getContractinfoByNum(contractNum);
		if(null!=existContractInfo){
			infoService.delById(existContractInfo.getId());
		}
	}
	
	public  String setContractType(ContractSealApplication contractSeal) {
		String num = contractSeal.getContract_num();
		String regEx = "(CG)[1,2][0-9]{3}[-]\\d+$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(num);
		boolean flag = matcher.matches();
		if(flag){
			return "1";
		}
		return "0";
	}

	public void updateContractFlowNo(String runId) {
		//通过runId获取合同编号
		ProcessRun processRun = processRunDao.getById(Long.valueOf(runId));
		if(processRun.getStatus()==4){
			return;
		}
		ContractSealApplication contractSeal = dao.getById(Long.parseLong(processRun.getBusinessKey()));
		String contractNum = contractSeal.getContract_num();
		String[] sortArr = contractInfoService.getSortDetail(contractNum);
		//获取流水号
		String yearSort = sortArr[0];
		String numSort = sortArr[1];
		//判断流水号是否存在
		ContractNumAdmin contractNumAdmin = contractNumAdminService.getContractNumAdminByType(contractSeal.getContracttype());
		ContractNumYear contractNumYear = contractNumYearService.getByRefIdAndYear(contractNumAdmin.getId(),yearSort);
		if(null!=contractNumYear && contractNumYear.getFlowNo().equals(numSort)){
			//跟新流水号
			 String flow = Integer.valueOf(Integer.valueOf(contractNumYear.getFlowNo())+1).toString();
			 contractNumYear.setFlowNo(flow);
			 try {
				contractNumYearService.save(contractNumYear);
			} catch (Exception e) {
				throw new RuntimeException("跟新合同流水号异常",e);
			}
		}
	}
	
}
