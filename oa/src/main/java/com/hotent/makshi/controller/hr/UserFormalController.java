

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
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.hr.UserFormal;
import com.hotent.makshi.service.hr.UserFormalService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysOrgService;
/**
 * 对象功能:员工转正表 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/userFormal/")
public class UserFormalController extends BaseController
{
	@Resource
	private UserFormalService userFormalService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SysOrgService sysOrgService;
	
	/**
	 * 添加或更新员工转正表。
	 * @param request
	 * @param response
	 * @param userFormal 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新员工转正表")
	public void save(HttpServletRequest request, HttpServletResponse response,UserFormal userFormal) throws Exception
	{
		String resultMsg=null;		
		try{
			if(userFormal.getId()==null){
				userFormalService.save(userFormal);
				resultMsg=getText("添加","员工转正表");
			}else{
			    userFormalService.save(userFormal);
				resultMsg=getText("更新","员工转正表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得员工转正表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看员工转正表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long chooseOrgId =(Long) request.getSession().getAttribute("chooseOrgId");
		String orgName = sysOrgService.changeOrgIdToCh(chooseOrgId+"");
		boolean isOrg = false;
		if(!StringUtil.isEmpty(orgName)){
			isOrg=true;
		}
		//List<UserFormal> list=userFormalService.getAll(new QueryFilter(request,"userFormalItem"));
		ModelAndView mv=this.getAutoView().addObject("isOrg",isOrg);
		return mv;
	}
	
	/**
	 * 删除员工转正表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除员工转正表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			userFormalService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除员工转正表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑员工转正表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑员工转正表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		UserFormal userFormal=userFormalService.getById(id);
		if(BeanUtils.isEmpty(userFormal)){
			userFormal=new UserFormal();
			String user_id_script="return scriptImpl.getCurrentUserId();";
			userFormal.setUser_id(engine.executeObject(user_id_script, null).toString());
		}
		
		return getAutoView().addObject("userFormal",userFormal)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得员工转正表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看员工转正表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		UserFormal userFormal=userFormalService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("userFormal", userFormal).addObject("runId", runId);
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
		UserFormal userFormal = userFormalService.getById(id);	
		return getAutoView().addObject("userFormal", userFormal);
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
		UserFormal userFormal = userFormalService.getById(id);	
		return getAutoView().addObject("userFormal", userFormal);
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
	public void run(HttpServletRequest request, HttpServletResponse response,UserFormal userFormal) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	UserFormal userFormalTemd = userFormalService.getById(userFormal.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(userFormalTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("zzlc");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				userFormal=userFormalService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				userFormalService.save(userFormal);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				userFormal.setId(genId);
				processRunService.startProcess(processCmd);
				userFormalService.save(userFormal);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看员工转正表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<UserFormal> list=userFormalService.getMyTodoTask(userId, new QueryFilter(request,"userFormalItem"));
		ModelAndView mv=this.getAutoView().addObject("userFormalList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看员工转正表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<UserFormal> list=userFormalService.getMyDraft(userId, new QueryFilter(request,"userFormalItem"));
		ModelAndView mv=this.getAutoView().addObject("userFormalList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的员工转正表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<UserFormal> list=userFormalService.getMyEnd(userId, new QueryFilter(request,"userFormalItem"));
		ModelAndView mv=this.getAutoView().addObject("userFormalList",list);
		return mv;
	}
}