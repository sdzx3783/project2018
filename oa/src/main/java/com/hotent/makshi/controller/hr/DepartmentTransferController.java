

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

import com.hotent.makshi.model.hr.DepartmentTransfer;
import com.hotent.makshi.service.hr.DepartmentTransferService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:部门调动表 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/departmentTransfer/")
public class DepartmentTransferController extends BaseController
{
	@Resource
	private DepartmentTransferService departmentTransferService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新部门调动表。
	 * @param request
	 * @param response
	 * @param departmentTransfer 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新部门调动表")
	public void save(HttpServletRequest request, HttpServletResponse response,DepartmentTransfer departmentTransfer) throws Exception
	{
		String resultMsg=null;		
		try{
			if(departmentTransfer.getId()==null){
				departmentTransferService.save(departmentTransfer);
				resultMsg=getText("添加","部门调动表");
			}else{
			    departmentTransferService.save(departmentTransfer);
				resultMsg=getText("更新","部门调动表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得部门调动表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看部门调动表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<DepartmentTransfer> list=departmentTransferService.getAll(new QueryFilter(request,"departmentTransferItem"));
		ModelAndView mv=this.getAutoView().addObject("departmentTransferList",list);
		return mv;
	}
	
	/**
	 * 删除部门调动表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除部门调动表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			departmentTransferService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除部门调动表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑部门调动表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑部门调动表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		DepartmentTransfer departmentTransfer=departmentTransferService.getById(id);
		if(BeanUtils.isEmpty(departmentTransfer)){
			departmentTransfer=new DepartmentTransfer();
			String user_name_script="return scriptImpl.getCurrentName();";
			departmentTransfer.setUser_name(engine.executeObject(user_name_script, null).toString());
			String out_department_script="return scriptImpl.getCurrentPrimaryOrgName();";
			departmentTransfer.setOut_department(engine.executeObject(out_department_script, null).toString());
			String user_id_script="return scriptImpl.getCurrentUserId();";
			departmentTransfer.setUser_id(Long.parseLong(engine.executeObject(user_id_script, null).toString()));
			String out_month_script="return scriptImpl.getCurrentDate();";
			departmentTransfer.setOut_month(engine.executeObject(out_month_script, null).toString());
		}
		
		return getAutoView().addObject("departmentTransfer",departmentTransfer)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得部门调动表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看部门调动表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		DepartmentTransfer departmentTransfer=departmentTransferService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("departmentTransfer", departmentTransfer).addObject("runId", runId);
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
		DepartmentTransfer departmentTransfer = departmentTransferService.getById(id);	
		return getAutoView().addObject("departmentTransfer", departmentTransfer);
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
		DepartmentTransfer departmentTransfer = departmentTransferService.getById(id);	
		return getAutoView().addObject("departmentTransfer", departmentTransfer);
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
	public void run(HttpServletRequest request, HttpServletResponse response,DepartmentTransfer departmentTransfer) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	DepartmentTransfer departmentTransferTemd = departmentTransferService.getById(departmentTransfer.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(departmentTransferTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("department_tranfer");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				departmentTransfer=departmentTransferService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				departmentTransferService.save(departmentTransfer);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				departmentTransfer.setId(genId);
				processRunService.startProcess(processCmd);
				departmentTransferService.save(departmentTransfer);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看部门调动表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<DepartmentTransfer> list=departmentTransferService.getMyTodoTask(userId, new QueryFilter(request,"departmentTransferItem"));
		ModelAndView mv=this.getAutoView().addObject("departmentTransferList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看部门调动表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<DepartmentTransfer> list=departmentTransferService.getMyDraft(userId, new QueryFilter(request,"departmentTransferItem"));
		ModelAndView mv=this.getAutoView().addObject("departmentTransferList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的部门调动表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<DepartmentTransfer> list=departmentTransferService.getMyEnd(userId, new QueryFilter(request,"departmentTransferItem"));
		ModelAndView mv=this.getAutoView().addObject("departmentTransferList",list);
		return mv;
	}
}