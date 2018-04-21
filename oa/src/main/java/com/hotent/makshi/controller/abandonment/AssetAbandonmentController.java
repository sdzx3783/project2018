

package com.hotent.makshi.controller.abandonment;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysUser;

import com.hotent.makshi.model.abandonment.AssetAbandonment;
import com.hotent.makshi.service.abandonment.AssetAbandonmentService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:资产报废表 控制器类
 */
@Controller
@RequestMapping("/makshi/abandonment/assetAbandonment/")
public class AssetAbandonmentController extends BaseController
{
	@Resource
	private AssetAbandonmentService assetAbandonmentService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新资产报废表。
	 * @param request
	 * @param response
	 * @param assetAbandonment 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新资产报废表")
	public void save(HttpServletRequest request, HttpServletResponse response,AssetAbandonment assetAbandonment) throws Exception
	{
		String resultMsg=null;		
		try{
			if(assetAbandonment.getId()==null){
				assetAbandonmentService.save(assetAbandonment);
				resultMsg=getText("添加","资产报废表");
			}else{
			    assetAbandonmentService.save(assetAbandonment);
				resultMsg=getText("更新","资产报废表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得资产报废表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看资产报废表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//List<AssetAbandonment> list=assetAbandonmentService.getAll(new QueryFilter(request,"assetAbandonmentItem"));
		ModelAndView mv=this.getAutoView().addObject("assetAbandonmentList");
		return mv;
	}
	
	/**
	 * 删除资产报废表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除资产报废表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			assetAbandonmentService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除资产报废表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑资产报废表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑资产报废表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		AssetAbandonment assetAbandonment=assetAbandonmentService.getById(id);
		if(BeanUtils.isEmpty(assetAbandonment)){
			assetAbandonment=new AssetAbandonment();
			String application_name_script="return scriptImpl.getCurrentName();";
			assetAbandonment.setApplication_name(engine.executeObject(application_name_script, null).toString());
		}
		
		return getAutoView().addObject("assetAbandonment",assetAbandonment)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得资产报废表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看资产报废表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		AssetAbandonment assetAbandonment=assetAbandonmentService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("assetAbandonment", assetAbandonment).addObject("runId", runId);
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
		AssetAbandonment assetAbandonment = assetAbandonmentService.getById(id);	
		return getAutoView().addObject("assetAbandonment", assetAbandonment);
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
		AssetAbandonment assetAbandonment = assetAbandonmentService.getById(id);	
		return getAutoView().addObject("assetAbandonment", assetAbandonment);
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
	public void run(HttpServletRequest request, HttpServletResponse response,AssetAbandonment assetAbandonment) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	AssetAbandonment assetAbandonmentTemd = assetAbandonmentService.getById(assetAbandonment.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(assetAbandonmentTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("asset_abandonment");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				assetAbandonment=assetAbandonmentService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				assetAbandonmentService.save(assetAbandonment);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				assetAbandonment.setId(genId);
				processRunService.startProcess(processCmd);
				assetAbandonmentService.save(assetAbandonment);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看资产报废表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<AssetAbandonment> list=assetAbandonmentService.getMyTodoTask(userId, new QueryFilter(request,"assetAbandonmentItem"));
		ModelAndView mv=this.getAutoView().addObject("assetAbandonmentList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看资产报废表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<AssetAbandonment> list=assetAbandonmentService.getMyDraft(userId, new QueryFilter(request,"assetAbandonmentItem"));
		ModelAndView mv=this.getAutoView().addObject("assetAbandonmentList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的资产报废表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<AssetAbandonment> list=assetAbandonmentService.getMyEnd(userId, new QueryFilter(request,"assetAbandonmentItem"));
		ModelAndView mv=this.getAutoView().addObject("assetAbandonmentList",list);
		return mv;
	}
}