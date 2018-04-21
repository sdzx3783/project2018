

package com.hotent.makshi.controller.receipt;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysProperty;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.util.WarningSetting;

import com.hotent.makshi.model.dispatch.Dispatch;
import com.hotent.makshi.model.receipt.ReceiptAll;
import com.hotent.makshi.service.receipt.ReceiptAllService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.bpm.TaskRead;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskReminderService;
import com.hotent.platform.service.form.TaskReadService;
/**
 * 对象功能:收文汇总表 控制器类
 */
@Controller
@RequestMapping("/makshi/receipt/receiptAll/")
public class ReceiptAllController extends BaseController
{
	@Resource
	private ReceiptAllService receiptAllService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskReadService taskReadService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskReminderService reminderService;
	@Resource TaskOpinionService taskOpinionService;
	
	private Integer myDraft=4;//我的草稿类型
	/**
	 * 添加或更新收文汇总表。
	 * @param request
	 * @param response
	 * @param receiptAll 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新收文汇总表")
	public void save(HttpServletRequest request, HttpServletResponse response,ReceiptAll receiptAll) throws Exception
	{
		String resultMsg=null;		
		try{
			if(receiptAll.getId()==null){
				receiptAllService.save(receiptAll);
				resultMsg=getText("添加","收文汇总表");
			}else{
			    receiptAllService.save(receiptAll);
				resultMsg=getText("更新","收文汇总表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得收文汇总表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看收文汇总表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ReceiptAll>  list=receiptAllService.getAll(new QueryFilter(request,"receiptAllItem"));
		list.removeAll(list);
		ModelAndView mv=this.getAutoView().addObject("receiptAllList",list);
		return mv;
	}
	/**
	 * 收文经办
	 */
	@RequestMapping("alreadyMattersList")
	@Action(description = "查看已办事宜流程列表")
	public ModelAndView alreadyMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("processName", "收文");
		String nodePath = RequestUtil.getString(request, "nodePath");// 右侧流程分类
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		List<ProcessRun> list = processRunService.getAlreadyMattersList(filter);
		for (ProcessRun processRun : list) {
			if (processRun.getStatus().shortValue() != ProcessRun.STATUS_FINISH.shortValue()) {
				// 1.查找当前用户是否有最新审批的任务
				TaskOpinion taskOpinion = taskOpinionService.getLatestUserOpinion(processRun.getActInstId(), ContextUtil.getCurrentUserId());
				if (BeanUtils.isNotEmpty(taskOpinion))
					processRun.setRecover(ProcessRun.STATUS_RECOVER);
			}
		}
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		return this.getAutoView().addObject("processRunList", list).addObject("hasGlobalFlowNo", hasGlobalFlowNo);
	}
	/**
	 * 收文待办
	 */
	@RequestMapping("pendingMattersList")
	public ModelAndView pendingMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		filter.addFilter("processName", "收文");
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<TaskEntity> list = bpmService.getMyTasks(filter);
		Map<Integer, WarningSetting> warningSetMap = reminderService.getWaringSetMap();
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		return getAutoView().addObject("taskList", list).addObject("warningSet", warningSetMap).addObject("hasGlobalFlowNo", hasGlobalFlowNo);

	}
	
	
	
	/**
	 * 获取收文列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myList")
	@Action(description="查看发文总表分页列表")
	public ModelAndView mylist(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//获取所有我的收文
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("processName", "收文");
		filter.addFilter("exceptStatus", myDraft);
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> list = processRunService.getAll(filter);
		//编辑ProcessRun对象
		if (BeanUtils.isNotEmpty(list)) {
			for (ProcessRun processRun : list) {
				processRun.setIsRead(false);
				//获取businessKey，通过businessKey获取申请数据
				String businessKey = processRun.getBusinessKey();
				ReceiptAll rec = receiptAllService.getBygetBusinessKey(Long.valueOf(businessKey));
				//设置流程定义名称为收文字号
				if(null != rec && !("").equals(rec)){
					processRun.setSubject(rec.getTitle());
					processRun.setProcessName(rec.getType());
				}
				String actInstId = processRun.getActInstId();//流程实例id
				List<TaskRead> taskReadByInstId = taskReadService.getTaskReadByInstId(Long.parseLong(actInstId==null?"1":actInstId));
				if(taskReadByInstId!=null && taskReadByInstId.size()>0){
					processRun.setIsRead(true);//已读，不能追回
				}
				BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(processRun.getActDefId());
				if (bpmDefinition.getIsPrintForm() == 1) {
					processRun.setIsPrintForm(bpmDefinition.getIsPrintForm());
				}
			}
		}
		ModelAndView mv = this.getAutoView().addObject("processRunList", list);
		return mv;
	}
	
	/**
	 * 删除收文汇总表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除收文汇总表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"runId");
			//receiptAllService.delByIds(lAryId);
			processRunService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除收文汇总表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑收文汇总表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑收文汇总表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		ReceiptAll receiptAll=receiptAllService.getById(id);
		if(BeanUtils.isEmpty(receiptAll)){
			receiptAll=new ReceiptAll();
			String time_script="return scriptImpl.getCurrentDate();";
			receiptAll.setTime(engine.executeObject(time_script, null).toString());
		}
		return getAutoView().addObject("receiptAll",receiptAll)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得收文汇总表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看收文汇总表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Integer type = RequestUtil.getInt(request,"type");
		ReceiptAll receiptAll=receiptAllService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		receiptAllService.updateReadState(id);
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("receiptAll", receiptAll).addObject("runId", runId).addObject("type", type);
	}
	
	/**
	 * 流程url表单 绑定的表单明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description="表单明细")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ReceiptAll receiptAll = receiptAllService.getById(id);	
		return getAutoView().addObject("receiptAll", receiptAll);
	}
	
	/**
	 * 流程url表单 绑定的表单编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modify")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ReceiptAll receiptAll = receiptAllService.getById(id);	
		return getAutoView().addObject("receiptAll", receiptAll);
	}
	
	
	
	/**
	 * 启动流程
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 由于修改了流程启动方式，不需要这个方法，因此先注释
	@RequestMapping("run")
	@Action(description="启动流程")
	public void run(HttpServletRequest request, HttpServletResponse response,ReceiptAll receiptAll) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	ReceiptAll receiptAllTemd = receiptAllService.getById(receiptAll.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(receiptAllTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("receipt");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				receiptAll=receiptAllService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				receiptAllService.save(receiptAll);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				receiptAll.setId(genId);
				processRunService.startProcess(processCmd);
				receiptAllService.save(receiptAll);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看收文汇总表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<ReceiptAll> list=receiptAllService.getMyTodoTask(userId, new QueryFilter(request,"receiptAllItem"));
		ModelAndView mv=this.getAutoView().addObject("receiptAllList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看收文汇总表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<ReceiptAll> list=receiptAllService.getMyDraft(userId, new QueryFilter(request,"receiptAllItem"));
		ModelAndView mv=this.getAutoView().addObject("receiptAllList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的收文汇总表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<ReceiptAll> list=receiptAllService.getMyEnd(userId, new QueryFilter(request,"receiptAllItem"));
		ModelAndView mv=this.getAutoView().addObject("receiptAllList",list);
		return mv;
	}
}