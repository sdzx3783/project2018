

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
import com.hotent.makshi.model.hj.HjEquipmentPurchase;
import com.hotent.makshi.service.hj.HjEquipmentPurchaseService;
import com.hotent.makshi.model.hj.HjpurchaseList;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:设备采购 控制器类
 */
@Controller
@RequestMapping("/makshi/hj/hjEquipmentPurchase/")
public class HjEquipmentPurchaseController extends BaseController
{
	@Resource
	private HjEquipmentPurchaseService hjEquipmentPurchaseService;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新设备采购。
	 * @param request
	 * @param response
	 * @param hjEquipmentPurchase 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新设备采购")
	public void save(HttpServletRequest request, HttpServletResponse response,HjEquipmentPurchase hjEquipmentPurchase,HjpurchaseList hjPurchase) throws Exception
	{
		String resultMsg=null;		
		try{
			if(hjEquipmentPurchase.getId()==null){
				hjEquipmentPurchaseService.save(hjEquipmentPurchase);			
				resultMsg=getText("添加","设备采购");
			}else{
				long id1 = RequestUtil.getLong(request, "id1");
				hjPurchase.setId(id1);
				hjEquipmentPurchase.setHjPurchase(hjPurchase);;
			    hjEquipmentPurchaseService.save(hjEquipmentPurchase);
			    
				resultMsg=getText("更新","设备采购");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得设备采购分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看设备采购分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//List<HjEquipmentPurchase> list=hjEquipmentPurchaseService.getAll(new QueryFilter(request,"hjEquipmentPurchaseItem"));
		QueryFilter queryFilter = new QueryFilter(request, "hjEquipmentPurchaseItem");
		Long currentUserId = ContextUtil.getCurrentUserId();
		queryFilter.getFilters().put("showdraft", currentUserId);
		List<HjEquipmentPurchase> list=hjEquipmentPurchaseService.getSelectByRecord(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("hjEquipmentPurchaseList",list).addObject("pageBeandocFile", queryFilter.getPageBean());// 此两项用于分页标签;
		return mv;
	}
	
	/**
	 * 删除设备采购
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除设备采购")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			hjEquipmentPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除设备采购及其从表成功!");
			hjEquipmentPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除设备采购成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑设备采购
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑设备采购")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		HjEquipmentPurchase hjEquipmentPurchase=hjEquipmentPurchaseService.getSelectByWPID(id);
		//List<HjpurchaseList> hjpurchaseListList=hjEquipmentPurchaseService.getHjpurchaseListList(id);
		
		return getAutoView().addObject("hjEquipmentPurchase",hjEquipmentPurchase)
							//.addObject("hjpurchaseListList",hjpurchaseListList)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得设备采购明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看设备采购明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		HjEquipmentPurchase hjEquipmentPurchase=hjEquipmentPurchaseService.getSelectByWPID(id);
		id=hjEquipmentPurchase.getId();
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		//List<HjpurchaseList> hjpurchaseListList=hjEquipmentPurchaseService.getHjpurchaseListList(id);
		return getAutoView().addObject("hjEquipmentPurchase",hjEquipmentPurchase)
							.addObject("runId", runId);
							//.addObject("hjpurchaseListList",hjpurchaseListList);
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
		HjEquipmentPurchase hjEquipmentPurchase = hjEquipmentPurchaseService.getById(id);	
		List<HjpurchaseList> hjpurchaseListList=hjEquipmentPurchaseService.getHjpurchaseListList(id);
		return getAutoView().addObject("hjEquipmentPurchase",hjEquipmentPurchase)
							.addObject("hjpurchaseListList",hjpurchaseListList);
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
		HjEquipmentPurchase hjEquipmentPurchase = hjEquipmentPurchaseService.getById(id);	
		List<HjpurchaseList> hjpurchaseListList=hjEquipmentPurchaseService.getHjpurchaseListList(id);
		return getAutoView().addObject("hjEquipmentPurchase",hjEquipmentPurchase)
							.addObject("hjpurchaseListList",hjpurchaseListList);
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
	public void run(HttpServletRequest request, HttpServletResponse response,HjEquipmentPurchase hjEquipmentPurchase) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	HjEquipmentPurchase hjEquipmentPurchaseTemd = hjEquipmentPurchaseService.getById(hjEquipmentPurchase.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(hjEquipmentPurchaseTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("sbcgsq");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				hjEquipmentPurchase=hjEquipmentPurchaseService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				hjEquipmentPurchaseService.save(hjEquipmentPurchase);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				hjEquipmentPurchase.setId(genId);
				processRunService.startProcess(processCmd);
				hjEquipmentPurchaseService.save(hjEquipmentPurchase);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看设备采购任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjEquipmentPurchase> list=hjEquipmentPurchaseService.getMyTodoTask(userId, new QueryFilter(request,"hjEquipmentPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("hjEquipmentPurchaseList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看设备采购草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjEquipmentPurchase> list=hjEquipmentPurchaseService.getMyDraft(userId, new QueryFilter(request,"hjEquipmentPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("hjEquipmentPurchaseList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的设备采购实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HjEquipmentPurchase> list=hjEquipmentPurchaseService.getMyEnd(userId, new QueryFilter(request,"hjEquipmentPurchaseItem"));
		ModelAndView mv=this.getAutoView().addObject("hjEquipmentPurchaseList",list);
		return mv;
	}
}