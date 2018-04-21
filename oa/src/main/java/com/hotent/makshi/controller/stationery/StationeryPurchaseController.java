

package com.hotent.makshi.controller.stationery;

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

import com.hotent.makshi.model.stationery.StationeryPurchase;
import com.hotent.makshi.service.stationery.StationeryPurchaseService;
import com.hotent.makshi.model.stock.StationeryStock;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:办公用品申购表 控制器类
 */
@Controller
@RequestMapping("/makshi/stationery/stationeryPurchase/")
public class StationeryPurchaseController extends BaseController
{
	@Resource
	private StationeryPurchaseService stationeryPurchaseService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新办公用品申购表。
	 * @param request
	 * @param response
	 * @param stationeryPurchase 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新办公用品申购表")
	public void save(HttpServletRequest request, HttpServletResponse response,StationeryPurchase stationeryPurchase) throws Exception
	{
		String resultMsg=null;		
		try{
			if(stationeryPurchase.getId()==null){
				stationeryPurchaseService.save(stationeryPurchase);			
				resultMsg=getText("添加","办公用品申购表");
			}else{
			    stationeryPurchaseService.save(stationeryPurchase);
				resultMsg=getText("更新","办公用品申购表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得办公用品申购表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看办公用品申购表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
//		List<StationeryPurchase> list=stationeryPurchaseService.getAll(new QueryFilter(request,"stationeryPurchaseItem"));
		ModelAndView mv=this.getAutoView();
		return mv;
	}
	
	/**
	 * 删除办公用品申购表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除办公用品申购表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			stationeryPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除办公用品申购表及其从表成功!");
			stationeryPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除办公用品申购表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑办公用品申购表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑办公用品申购表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		StationeryPurchase stationeryPurchase=stationeryPurchaseService.getById(id);
		if(BeanUtils.isEmpty(stationeryPurchase)){
			stationeryPurchase=new StationeryPurchase();
			String user_name_script="return scriptImpl.getCurrentName();";
			stationeryPurchase.setUser_name(engine.executeObject(user_name_script, null).toString());
			String user_id_script="return scriptImpl.getCurrentUserId();";
			stationeryPurchase.setUser_id(engine.executeObject(user_id_script, null).toString());
		}
		List<StationeryStock> stationeryStockList=stationeryPurchaseService.getStationeryStockList(id);
		
		return getAutoView().addObject("stationeryPurchase",stationeryPurchase)
							.addObject("stationeryStockList",stationeryStockList)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得办公用品申购表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看办公用品申购表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		StationeryPurchase stationeryPurchase=stationeryPurchaseService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		List<StationeryStock> stationeryStockList=stationeryPurchaseService.getStationeryStockList(id);
		return getAutoView().addObject("stationeryPurchase",stationeryPurchase)
							.addObject("runId", runId)
							.addObject("stationeryStockList",stationeryStockList);
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
		StationeryPurchase stationeryPurchase = stationeryPurchaseService.getById(id);	
		List<StationeryStock> stationeryStockList=stationeryPurchaseService.getStationeryStockList(id);
		return getAutoView().addObject("stationeryPurchase",stationeryPurchase)
							.addObject("stationeryStockList",stationeryStockList);
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
		StationeryPurchase stationeryPurchase = stationeryPurchaseService.getById(id);	
		List<StationeryStock> stationeryStockList=stationeryPurchaseService.getStationeryStockList(id);
		return getAutoView().addObject("stationeryPurchase",stationeryPurchase)
							.addObject("stationeryStockList",stationeryStockList);
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
	public void run(HttpServletRequest request, HttpServletResponse response,StationeryPurchase stationeryPurchase) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	StationeryPurchase stationeryPurchaseTemd = stationeryPurchaseService.getById(stationeryPurchase.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(stationeryPurchaseTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("stationery_purchase");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				stationeryPurchase=stationeryPurchaseService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				stationeryPurchaseService.save(stationeryPurchase);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				stationeryPurchase.setId(genId);
				processRunService.startProcess(processCmd);
				stationeryPurchaseService.save(stationeryPurchase);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看办公用品申购表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<StationeryPurchase> list=stationeryPurchaseService.getMyTodoTask(userId, new QueryFilter(request,"stationeryPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("stationeryPurchaseList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看办公用品申购表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<StationeryPurchase> list=stationeryPurchaseService.getMyDraft(userId, new QueryFilter(request,"stationeryPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("stationeryPurchaseList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的办公用品申购表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<StationeryPurchase> list=stationeryPurchaseService.getMyEnd(userId, new QueryFilter(request,"stationeryPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("stationeryPurchaseList",list);
		return mv;
	}
}