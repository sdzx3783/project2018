
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
import com.hotent.makshi.model.hr.UserResignation;
import com.hotent.makshi.service.hr.UserResignationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * 对象功能:员工离职表 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/userResignation/")
public class UserResignationController extends BaseController {
	@Resource
	private UserResignationService userResignationService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;

	/**
	 * 添加或更新员工离职表。
	 * 
	 * @param request
	 * @param response
	 * @param userResignation 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新员工离职表")
	public void save(HttpServletRequest request, HttpServletResponse response, UserResignation userResignation) throws Exception {
		String resultMsg = null;
		try {
			if (userResignation.getId() == null) {
				userResignationService.save(userResignation);
				resultMsg = getText("添加", "员工离职表");
			} else {
				userResignationService.save(userResignation);
				resultMsg = getText("更新", "员工离职表");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得员工离职表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看员工离职表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// List<UserResignation> list=userResignationService.getAll(new QueryFilter(request,"userResignationItem"));
		// ModelAndView mv=this.getAutoView().addObject("userResignationList",list);
		return getAutoView();
	}

	/**
	 * 删除员工离职表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除员工离职表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			userResignationService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除员工离职表成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑员工离职表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑员工离职表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		Long runId = 0L;
		ProcessRun processRun = processRunService.getByBusinessKey(id.toString());
		if (BeanUtils.isNotEmpty(processRun)) {
			runId = processRun.getRunId();
		}
		String returnUrl = RequestUtil.getPrePage(request);
		UserResignation userResignation = userResignationService.getById(id);
		if (BeanUtils.isEmpty(userResignation)) {
			userResignation = new UserResignation();
			String user_id_script = "return scriptImpl.getCurrentUserId();";
			userResignation.setUser_id(engine.executeObject(user_id_script, null).toString());
			String task_id_script = "return scriptImpl.getAccount() ;";
			userResignation.setTask_id(engine.executeObject(task_id_script, null).toString());
		}

		return getAutoView().addObject("userResignation", userResignation).addObject("runId", runId).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得员工离职表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看员工离职表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		UserResignation userResignation = userResignationService.getById(id);
		Long runId = 0L;
		ProcessRun processRun = processRunService.getByBusinessKey(id.toString());
		if (BeanUtils.isNotEmpty(processRun)) {
			runId = processRun.getRunId();
		}
		return getAutoView().addObject("userResignation", userResignation).addObject("runId", runId);
	}

	/**
	 * 流程url表单 绑定的表单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description = "表单明细")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		UserResignation userResignation = userResignationService.getById(id);
		return getAutoView().addObject("userResignation", userResignation);
	}

	/**
	 * 流程url表单 绑定的表单编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modify")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		UserResignation userResignation = userResignationService.getById(id);
		return getAutoView().addObject("userResignation", userResignation);
	}

	/**
	 * 启动流程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 由于修改了流程启动方式，不需要这个方法，因此先注释 @RequestMapping("run")
	 * @Action(description="启动流程") public void run(HttpServletRequest request, HttpServletResponse response,UserResignation userResignation) throws Exception { Long
	 *                             id=RequestUtil.getLong(request,"id",0L); Integer isList=RequestUtil.getInt(request, "isList",0); ProcessCmd processCmd=new ProcessCmd();
	 *                             //添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错 //if(isList==0){ // processCmd = BpmUtil.getProcessCmd(request); //}else{ // UserResignation userResignationTemd =
	 *                             userResignationService.getById(userResignation.getId()); // Map<String,Object> map = MapUtil.transBean2Map(userResignationTemd); // JSONObject jsonObject =
	 *                             JSONObject.fromObject(map); // String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}"; // JSONObject formData = JSONObject.fromObject(str); //
	 *                             processCmd.setFormData(formData.toString()); //} processCmd.setFlowKey("lzlc"); processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount()); try {
	 *                             if(id!=0L){ if(isList==1){ userResignation=userResignationService.getById(id); } processCmd.setBusinessKey(id.toString());
	 *                             processRunService.startProcess(processCmd); userResignationService.save(userResignation); }else{ Long genId=UniqueIdUtil.genId();
	 *                             processCmd.setBusinessKey(genId.toString()); userResignation.setId(genId); processRunService.startProcess(processCmd); userResignationService.save(userResignation);
	 *                             } writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功")); } catch (Exception e) { log.error("错误信息",e);
	 *                             writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e))); } }
	 */

	@RequestMapping("getMyTodoTask")
	@Action(description = "查看员工离职表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<UserResignation> list = userResignationService.getMyTodoTask(userId, new QueryFilter(request, "userResignationItem"));
		ModelAndView mv = this.getAutoView().addObject("userResignationList", list);
		return mv;
	}

	@RequestMapping("getMyDraft")
	@Action(description = "查看员工离职表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<UserResignation> list = userResignationService.getMyDraft(userId, new QueryFilter(request, "userResignationItem"));
		ModelAndView mv = this.getAutoView().addObject("userResignationList", list);
		return mv;
	}

	@RequestMapping("getMyEnd")
	@Action(description = "查看我结束的员工离职表实例")
	public ModelAndView getMyEnd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<UserResignation> list = userResignationService.getMyEnd(userId, new QueryFilter(request, "userResignationItem"));
		ModelAndView mv = this.getAutoView().addObject("userResignationList", list);
		return mv;
	}
}