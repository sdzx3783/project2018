

package com.hotent.makshi.controller.river;

import java.util.Date;
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
import com.hotent.makshi.model.operation.OverAndAdjust;
import com.hotent.makshi.model.operation.OverTime;
import com.hotent.makshi.model.river.HdOvertimeApplications;
import com.hotent.makshi.model.river.HdPaidLeave;
import com.hotent.makshi.service.river.HdOvertimeApplicationsService;
import com.hotent.makshi.service.river.HdPaidLeaveService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:加班申请表单 控制器类
 */
@Controller
@RequestMapping("/makshi/river/hdOvertimeApplications/")
public class HdOvertimeApplicationsController extends BaseController
{
	@Resource
	private HdOvertimeApplicationsService hdOvertimeApplicationsService;
	@Resource
	private HdPaidLeaveService hdPaidLeaveService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SysRoleService sysRoleService;
	
	
	/**
	 * 取得加班申请表单分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看加班申请表单分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		cleanInfo();
		QueryFilter fiter = new QueryFilter(request,"hdOvertimeApplicationsItem");
		String alias = "hdOvertimeApplicationsManager";
		Boolean flag = false;
		Long userId = ContextUtil.getCurrentUserId();
		if(userId==1){
			flag = true;
		}
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		for (SysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				flag = true;
			}
		}
		if(!flag){
			fiter.addFilter("userId",userId);
			
		}
		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getUser(fiter);
		ModelAndView mv=this.getAutoView().addObject("hdOvertimeApplicationsList",list);
		return mv;
		
		
		
//		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getAll(new QueryFilter(request,"hdOvertimeApplicationsItem"));
//		ModelAndView mv=this.getAutoView().addObject("hdOvertimeApplicationsList",list);
//		return mv;
	}
	
	/**
	 * 清空加班调休信息
	 */
	public void cleanInfo(){
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int month = date.getMonth();
		@SuppressWarnings("deprecation")
		int day = date.getDay();
		if(month==3&&day==1){
			hdOvertimeApplicationsService.cleanInfo();
			hdPaidLeaveService.cleanInfo();
		}
		
	}
	
	/**
	 * 历史数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("past")
	public ModelAndView past(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getPast(id);
		return getAutoView().addObject("hdOvertimeApplications",list);
	}
	
	/**
	 * 取得加班申请表单明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看加班申请表单明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		QueryFilter fiter =  new QueryFilter(request);
		fiter.addFilter("userId", id);
		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getAll(fiter);
		List<HdPaidLeave>  overList=hdPaidLeaveService.getOverListByUsrId(id);
		List<HdPaidLeave>  adjustList=hdPaidLeaveService.getAdjustListByUsrId(id);
		
		
		return getAutoView().addObject("overList", overList)
				            .addObject("adjustList", adjustList)
				            .addObject("hdOvertimeApplications",list);
	}
	
//	/**
//	 * 删除加班申请表单
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("del")
//	@Action(description="删除加班申请表单")
//	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		String preUrl= RequestUtil.getPrePage(request);
//		ResultMessage message=null;
//		try{
//			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
//			hdOvertimeApplicationsService.delByIds(lAryId);
//			message=new ResultMessage(ResultMessage.Success, "删除加班申请表单成功!");
//		}catch(Exception ex){
//			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
//		}
//		addMessage(message, request);
//		response.sendRedirect(preUrl);
//	}
//	
//	/**
//	 * 添加或更新加班申请表单。
//	 * @param request
//	 * @param response
//	 * @param hdOvertimeApplications 添加或更新的实体
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("save")
//	@Action(description="添加或更新加班申请表单")
//	public void save(HttpServletRequest request, HttpServletResponse response,HdOvertimeApplications hdOvertimeApplications) throws Exception
//	{
//		String resultMsg=null;		
//		try{
//			if(hdOvertimeApplications.getId()==null){
//				hdOvertimeApplicationsService.save(hdOvertimeApplications);
//				resultMsg=getText("添加","加班申请表单");
//			}else{
//			    hdOvertimeApplicationsService.save(hdOvertimeApplications);
//				resultMsg=getText("更新","加班申请表单");
//			}
//			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
//		}catch(Exception e){
//			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
//		}
//	}
	/**
	 * 	编辑加班申请表单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	@RequestMapping("edit")
//	@Action(description="编辑加班申请表单")
//	public ModelAndView edit(HttpServletRequest request) throws Exception
//	{
//		Long id=RequestUtil.getLong(request,"id");
//		
//		Long runId=0L;
//		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
//		if(BeanUtils.isNotEmpty(processRun)){
//			runId=processRun.getRunId();
//		}
//		String returnUrl=RequestUtil.getPrePage(request);
//		HdOvertimeApplications hdOvertimeApplications=hdOvertimeApplicationsService.getById(id);
//		
//		return getAutoView().addObject("hdOvertimeApplications",hdOvertimeApplications)
//							.addObject("runId", runId)
//							.addObject("returnUrl",returnUrl);
//	}
//
	
//	
//	/**
//	 * 流程url表单 绑定的表单明细
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("detail")
//	@Action(description="表单明细")
//	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		Long id=RequestUtil.getLong(request,"id");
//		HdOvertimeApplications hdOvertimeApplications = hdOvertimeApplicationsService.getById(id);	
//		return getAutoView().addObject("hdOvertimeApplications", hdOvertimeApplications);
//	}
//	
//	/**
//	 * 流程url表单 绑定的表单编辑页面
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("modify")
//	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		Long id=RequestUtil.getLong(request,"id");
//		HdOvertimeApplications hdOvertimeApplications = hdOvertimeApplicationsService.getById(id);	
//		return getAutoView().addObject("hdOvertimeApplications", hdOvertimeApplications);
//	}
//	 
	
	
	/**
	 * 启动流程
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 由于修改了流程启动方式，不需要这个方法，因此先注释
	@RequestMapping("run")
	@Action(description="启动流程")
	public void run(HttpServletRequest request, HttpServletResponse response,HdOvertimeApplications hdOvertimeApplications) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	HdOvertimeApplications hdOvertimeApplicationsTemd = hdOvertimeApplicationsService.getById(hdOvertimeApplications.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(hdOvertimeApplicationsTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("zbsq");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				hdOvertimeApplications=hdOvertimeApplicationsService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				hdOvertimeApplicationsService.save(hdOvertimeApplications);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				hdOvertimeApplications.setId(genId);
				processRunService.startProcess(processCmd);
				hdOvertimeApplicationsService.save(hdOvertimeApplications);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


//    @RequestMapping("getMyTodoTask")
//	@Action(description="查看加班申请表单任务分页列表")
//	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{	
//		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
//		Long userId=sysUser.getUserId();
//		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getMyTodoTask(userId, new QueryFilter(request,"hdOvertimeApplicationsItem"));
//		ModelAndView mv=this.getAutoView().addObject("hdOvertimeApplicationsList",list);
//		return mv;
//	}
//	
//	@RequestMapping("getMyDraft")
//	@Action(description="查看加班申请表单草稿")
//	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{	
//		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
//		Long userId=sysUser.getUserId();
//		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getMyDraft(userId, new QueryFilter(request,"hdOvertimeApplicationsItem"));
//		ModelAndView mv=this.getAutoView().addObject("hdOvertimeApplicationsList",list);
//		return mv;
//	}
//	
//	@RequestMapping("getMyEnd")
//	@Action(description="查看我结束的加班申请表单实例")
//	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{	
//		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
//		Long userId=sysUser.getUserId();
//		List<HdOvertimeApplications> list=hdOvertimeApplicationsService.getMyEnd(userId, new QueryFilter(request,"hdOvertimeApplicationsItem"));
//		ModelAndView mv=this.getAutoView().addObject("hdOvertimeApplicationsList",list);
//		return mv;
//	}
	
	
}