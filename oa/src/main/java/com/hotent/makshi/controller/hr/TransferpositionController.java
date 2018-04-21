

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
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.hr.Transferposition;
import com.hotent.makshi.service.hr.TransferpositionService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:部门内调岗 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/transferposition/")
public class TransferpositionController extends BaseController
{
	@Resource
	private TransferpositionService transferpositionService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SysRoleService sysRoleService;
	
	/**
	 * 添加或更新部门内调岗。
	 * @param request
	 * @param response
	 * @param transferposition 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新部门内调岗")
	public void save(HttpServletRequest request, HttpServletResponse response,Transferposition transferposition) throws Exception
	{
		String resultMsg=null;		
		try{
			if(transferposition.getId()==null){
				transferpositionService.save(transferposition);
				resultMsg=getText("添加","部门内调岗");
			}else{
			    transferpositionService.save(transferposition);
				resultMsg=getText("更新","部门内调岗");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得部门内调岗分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看部门内调岗分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//List<Transferposition> list=transferpositionService.getAll(new QueryFilter(request,"transferpositionItem"));
		//判断当前用户是否有查看工资的权限
		Long userId = ContextUtil.getCurrentUserId();
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		boolean flag = false;
		for(SysRole sysRole : roleList){
			if("bpm_bmld".equals(sysRole.getAlias())||"gsld".equals(sysRole.getAlias())){
				flag = true;
			}
		}
		ModelAndView mv=this.getAutoView().addObject("flag",flag);
		return mv;
	}
	
	/**
	 * 删除部门内调岗
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除部门内调岗")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			transferpositionService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除部门内调岗成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑部门内调岗
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑部门内调岗")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		Transferposition transferposition=transferpositionService.getById(id);
		if(BeanUtils.isEmpty(transferposition)){
			transferposition=new Transferposition();
			String name_script="return scriptImpl.getCurrentName();";
			transferposition.setName(engine.executeObject(name_script, null).toString());
			String department_script="return scriptImpl.getCurrentPrimaryOrgName();";
			transferposition.setDepartment(engine.executeObject(department_script, null).toString());
			String userId_script="return scriptImpl.getCurrentUserId();";
			transferposition.setUserId(engine.executeObject(userId_script, null).toString());
			String beforePost_script="return scriptImpl.getCurrentPosName();";
			transferposition.setBeforePostion(engine.executeObject(beforePost_script, null).toString());
		}
		
		return getAutoView().addObject("transferposition",transferposition)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得部门内调岗明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看部门内调岗明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Transferposition transferposition=transferpositionService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("transferposition", transferposition).addObject("runId", runId);
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
		Transferposition transferposition = transferpositionService.getById(id);	
		return getAutoView().addObject("transferposition", transferposition);
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
		Transferposition transferposition = transferpositionService.getById(id);	
		return getAutoView().addObject("transferposition", transferposition);
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
	public void run(HttpServletRequest request, HttpServletResponse response,Transferposition transferposition) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	Transferposition transferpositionTemd = transferpositionService.getById(transferposition.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(transferpositionTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("transferPosition");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				transferposition=transferpositionService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				transferpositionService.save(transferposition);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				transferposition.setId(genId);
				processRunService.startProcess(processCmd);
				transferpositionService.save(transferposition);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看部门内调岗任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Transferposition> list=transferpositionService.getMyTodoTask(userId, new QueryFilter(request,"transferpositionItem"));
		ModelAndView mv=this.getAutoView().addObject("transferpositionList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看部门内调岗草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Transferposition> list=transferpositionService.getMyDraft(userId, new QueryFilter(request,"transferpositionItem"));
		ModelAndView mv=this.getAutoView().addObject("transferpositionList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的部门内调岗实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Transferposition> list=transferpositionService.getMyEnd(userId, new QueryFilter(request,"transferpositionItem"));
		ModelAndView mv=this.getAutoView().addObject("transferpositionList",list);
		return mv;
	}
}