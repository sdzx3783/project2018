

package com.hotent.makshi.controller.hr;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.hr.LeaveApplication;
import com.hotent.makshi.service.hr.LeaveApplicationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:请假申请流程表单 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/leaveApplication/")
public class LeaveApplicationController extends BaseController
{
	@Resource
	private LeaveApplicationService leaveApplicationService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private SysRoleService roleService;
	
	/**
	 * 添加或更新请假申请流程表单。
	 * @param request
	 * @param response
	 * @param leaveApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新请假申请流程表单")
	public void save(HttpServletRequest request, HttpServletResponse response,LeaveApplication leaveApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(leaveApplication.getId()==null){
				leaveApplicationService.save(leaveApplication);
				resultMsg=getText("添加","请假申请流程表单");
			}else{
			    leaveApplicationService.save(leaveApplication);
				resultMsg=getText("更新","请假申请流程表单");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得请假申请流程表单分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看请假申请流程表单分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		//查询角色
		List<SysRole> roles=roleService.getByUserId(sysUser.getUserId());
		String alias=null;
		if(null!=roles && roles.size()>0){
			for(SysRole role:roles){
				if(role.getAlias().equals("bpm_hr_manager")){
					alias=role.getAlias();
				}
			}
			if(null==alias){
				alias=roles.get(0).getAlias();
			}
		}
		//非人事管理员角色只能查看自己的请假记录
		QueryFilter query=new QueryFilter(request,"leaveApplicationItem");
		if(alias!=null && !alias.equals("bpm_hr_manager")){
			query.addFilter("user_num", sysUser.getUserId());
		}
		List<LeaveApplication> list=leaveApplicationService.getAll(query);
		
		ModelAndView mv=this.getAutoView().addObject("leaveApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除请假申请流程表单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除请假申请流程表单")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			leaveApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除请假申请流程表单成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑请假申请流程表单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑请假申请流程表单")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		LeaveApplication leaveApplication=leaveApplicationService.getById(id);
		if(BeanUtils.isEmpty(leaveApplication)){
			leaveApplication=new LeaveApplication();
			String user_num_script="return scriptImpl.getCurrentUserId();";
			leaveApplication.setUser_num(engine.executeObject(user_num_script, null).toString());
			String applicant_script="return scriptImpl.getCurrentName();";
			leaveApplication.setApplicant(engine.executeObject(applicant_script, null).toString());
		}
		
		return getAutoView().addObject("leaveApplication",leaveApplication)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得请假申请流程表单明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看请假申请流程表单明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		LeaveApplication leaveApplication=leaveApplicationService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("leaveApplication", leaveApplication).addObject("runId", runId);
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
		LeaveApplication leaveApplication = leaveApplicationService.getById(id);	
		return getAutoView().addObject("leaveApplication", leaveApplication);
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
		LeaveApplication leaveApplication = leaveApplicationService.getById(id);	
		return getAutoView().addObject("leaveApplication", leaveApplication);
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
	public void run(HttpServletRequest request, HttpServletResponse response,LeaveApplication leaveApplication) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	LeaveApplication leaveApplicationTemd = leaveApplicationService.getById(leaveApplication.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(leaveApplicationTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("leaveApplicanion");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				leaveApplication=leaveApplicationService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				leaveApplicationService.save(leaveApplication);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				leaveApplication.setId(genId);
				processRunService.startProcess(processCmd);
				leaveApplicationService.save(leaveApplication);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看请假申请流程表单任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<LeaveApplication> list=leaveApplicationService.getMyTodoTask(userId, new QueryFilter(request,"leaveApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("leaveApplicationList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看请假申请流程表单草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<LeaveApplication> list=leaveApplicationService.getMyDraft(userId, new QueryFilter(request,"leaveApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("leaveApplicationList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的请假申请流程表单实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<LeaveApplication> list=leaveApplicationService.getMyEnd(userId, new QueryFilter(request,"leaveApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("leaveApplicationList",list);
		return mv;
	}
}