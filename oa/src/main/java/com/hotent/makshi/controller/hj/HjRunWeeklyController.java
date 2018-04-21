

package com.hotent.makshi.controller.hj;

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

import com.hotent.makshi.model.hj.HjRunWeekly;
import com.hotent.makshi.service.hj.HjRunWeeklyService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:22 控制器类
 */
@Controller
@RequestMapping("/makshi/hj/hjRunWeekly/")
public class HjRunWeeklyController extends BaseController
{
	@Resource
	private HjRunWeeklyService hjRunWeeklyService;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新22。
	 * @param request
	 * @param response
	 * @param hjRunWeekly 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新22")
	public void save(HttpServletRequest request, HttpServletResponse response,HjRunWeekly hjRunWeekly) throws Exception
	{
		String resultMsg=null;		
		try{
			if(hjRunWeekly.getId()==null){
				hjRunWeeklyService.save(hjRunWeekly);
				resultMsg=getText("添加","22");
			}else{
			    hjRunWeeklyService.save(hjRunWeekly);
				resultMsg=getText("更新","22");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得22分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看22分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<HjRunWeekly> list=hjRunWeeklyService.getAll(new QueryFilter(request,"hjRunWeeklyItem"));
		ModelAndView mv=this.getAutoView().addObject("hjRunWeeklyList",list);
		return mv;
	}
	
	/**
	 * 删除22
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除22")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			hjRunWeeklyService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除22成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑22
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑22")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		HjRunWeekly hjRunWeekly=hjRunWeeklyService.getById(id);
		
		return getAutoView().addObject("hjRunWeekly",hjRunWeekly)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得22明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看22明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		HjRunWeekly hjRunWeekly=hjRunWeeklyService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("hjRunWeekly", hjRunWeekly).addObject("runId", runId);
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
		HjRunWeekly hjRunWeekly = hjRunWeeklyService.getById(id);	
		return getAutoView().addObject("hjRunWeekly", hjRunWeekly);
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
		HjRunWeekly hjRunWeekly = hjRunWeeklyService.getById(id);	
		return getAutoView().addObject("hjRunWeekly", hjRunWeekly);
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
	public void run(HttpServletRequest request, HttpServletResponse response,HjRunWeekly hjRunWeekly) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	HjRunWeekly hjRunWeeklyTemd = hjRunWeeklyService.getById(hjRunWeekly.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(hjRunWeeklyTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("qq123");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				hjRunWeekly=hjRunWeeklyService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				hjRunWeeklyService.save(hjRunWeekly);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				hjRunWeekly.setId(genId);
				processRunService.startProcess(processCmd);
				hjRunWeeklyService.save(hjRunWeekly);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看22任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjRunWeekly> list=hjRunWeeklyService.getMyTodoTask(userId, new QueryFilter(request,"hjRunWeeklyItem"));
		ModelAndView mv=this.getAutoView().addObject("hjRunWeeklyList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看22草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjRunWeekly> list=hjRunWeeklyService.getMyDraft(userId, new QueryFilter(request,"hjRunWeeklyItem"));
		ModelAndView mv=this.getAutoView().addObject("hjRunWeeklyList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的22实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjRunWeekly> list=hjRunWeeklyService.getMyEnd(userId, new QueryFilter(request,"hjRunWeeklyItem"));
		ModelAndView mv=this.getAutoView().addObject("hjRunWeeklyList",list);
		return mv;
	}
}