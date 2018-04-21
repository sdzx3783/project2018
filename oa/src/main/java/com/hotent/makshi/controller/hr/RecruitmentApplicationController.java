

package com.hotent.makshi.controller.hr;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.page.PageList;
import com.hotent.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.hr.RecruitmentApplication;
import com.hotent.makshi.service.hr.RecruitmentApplicationService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:招聘申请 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/recruitmentApplication/")
public class RecruitmentApplicationController extends BaseController
{
	@Resource
	private RecruitmentApplicationService recruitmentApplicationService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新招聘申请。
	 * @param request
	 * @param response
	 * @param recruitmentApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新招聘申请")
	public void save(HttpServletRequest request, HttpServletResponse response,RecruitmentApplication recruitmentApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(recruitmentApplication.getId()==null){
				recruitmentApplicationService.save(recruitmentApplication);
				resultMsg=getText("添加","招聘申请");
			}else{
			    recruitmentApplicationService.save(recruitmentApplication);
				resultMsg=getText("更新","招聘申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得招聘申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看招聘申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<RecruitmentApplication> list=recruitmentApplicationService.getAll(new QueryFilter(request,"recruitmentApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("recruitmentApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除招聘申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除招聘申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			recruitmentApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除招聘申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑招聘申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑招聘申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		RecruitmentApplication recruitmentApplication=recruitmentApplicationService.getById(id);
		if(BeanUtils.isEmpty(recruitmentApplication)){
			recruitmentApplication=new RecruitmentApplication();
			String user_num_script="return scriptImpl.getCurrentUserId();";
			recruitmentApplication.setUser_num(engine.executeObject(user_num_script, null).toString());
			String applicant_script="return scriptImpl.getCurrentName();";
			recruitmentApplication.setApplicant(engine.executeObject(applicant_script, null).toString());
		}
		
		return getAutoView().addObject("recruitmentApplication",recruitmentApplication)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得招聘申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看招聘申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		RecruitmentApplication recruitmentApplication=recruitmentApplicationService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("recruitmentApplication", recruitmentApplication).addObject("runId", runId);
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
		RecruitmentApplication recruitmentApplication = recruitmentApplicationService.getById(id);	
		return getAutoView().addObject("recruitmentApplication", recruitmentApplication);
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
		RecruitmentApplication recruitmentApplication = recruitmentApplicationService.getById(id);	
		return getAutoView().addObject("recruitmentApplication", recruitmentApplication);
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
	public void run(HttpServletRequest request, HttpServletResponse response,RecruitmentApplication recruitmentApplication) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	RecruitmentApplication recruitmentApplicationTemd = recruitmentApplicationService.getById(recruitmentApplication.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(recruitmentApplicationTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("recruitment_application");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				recruitmentApplication=recruitmentApplicationService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				recruitmentApplicationService.save(recruitmentApplication);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				recruitmentApplication.setId(genId);
				processRunService.startProcess(processCmd);
				recruitmentApplicationService.save(recruitmentApplication);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看招聘申请任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<RecruitmentApplication> list=recruitmentApplicationService.getMyTodoTask(userId, new QueryFilter(request,"recruitmentApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("recruitmentApplicationList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看招聘申请草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<RecruitmentApplication> list=recruitmentApplicationService.getMyDraft(userId, new QueryFilter(request,"recruitmentApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("recruitmentApplicationList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的招聘申请实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<RecruitmentApplication> list=recruitmentApplicationService.getMyEnd(userId, new QueryFilter(request,"recruitmentApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("recruitmentApplicationList",list);
		return mv;
	}
}