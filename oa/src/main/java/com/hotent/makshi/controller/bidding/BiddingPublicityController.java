

package com.hotent.makshi.controller.bidding;

import java.util.List;
import java.util.Map;

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
import com.hotent.makshi.common.SetOpinion;
import com.hotent.makshi.model.bidding.BiddingOption;
import com.hotent.makshi.model.bidding.BiddingPublicity;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.bidding.BiddingPublicityService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
/**
 * 对象功能:补充通知/标底公示 控制器类
 */
@Controller
@RequestMapping("/makshi/bidding/biddingPublicity/")
public class BiddingPublicityController extends BaseController
{
	@Resource
	private BiddingPublicityService biddingPublicityService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private ContractinfoService contractinfoService;
	
	/**
	 * 添加或更新补充通知/标底公示。
	 * @param request
	 * @param response
	 * @param biddingPublicity 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新补充通知/标底公示")
	public void save(HttpServletRequest request, HttpServletResponse response,BiddingPublicity biddingPublicity) throws Exception
	{
		String resultMsg=null;		
		try{
			if(biddingPublicity.getId()==null){
				biddingPublicityService.save(biddingPublicity);
				resultMsg=getText("添加","补充通知/标底公示");
			}else{
			    biddingPublicityService.save(biddingPublicity);
				resultMsg=getText("更新","补充通知/标底公示");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得补充通知/标底公示分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看补充通知/标底公示分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter filter = new QueryFilter(request,"biddingPublicityItem");
		List<BiddingPublicity> list=biddingPublicityService.getAll(filter);
		ModelAndView mv=this.getAutoView().addObject("biddingPublicityList",list);
		return mv;
	}
	
	/**
	 * 删除补充通知/标底公示
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除补充通知/标底公示")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			biddingPublicityService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除补充通知/标底公示成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑补充通知/标底公示
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑补充通知/标底公示")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		BiddingPublicity biddingPublicity=biddingPublicityService.getById(id);
		if(BeanUtils.isEmpty(biddingPublicity)){
			biddingPublicity=new BiddingPublicity();
			String publicity_applicant_script="return scriptImpl.getCurrentName();";
			biddingPublicity.setPublicity_applicant(engine.executeObject(publicity_applicant_script, null).toString());
		}
		
		return getAutoView().addObject("biddingPublicity",biddingPublicity)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得补充通知/标底公示明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看补充通知/标底公示明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		BiddingPublicity biddingPublicity=biddingPublicityService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("biddingPublicity", biddingPublicity).addObject("runId", runId);
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
		BiddingPublicity biddingPublicity = biddingPublicityService.getById(id);
		Long runId=0L;
		List<TaskOpinion> list = null;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
			 list = taskOpinionService.getByRunId(runId);
			    BiddingOption  biddingOption = new BiddingOption();
			    TaskOpinion taskOpinion = list.get(0);
			    SetOpinion.setBiddingOpinion(biddingOption,taskOpinion);
		}
		return getAutoView().addObject("biddingPublicity", biddingPublicity).addObject("taskOpinionList", list);
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
		BiddingPublicity biddingPublicity = biddingPublicityService.getById(id);	
		return getAutoView().addObject("biddingPublicity", biddingPublicity);
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
	public void run(HttpServletRequest request, HttpServletResponse response,BiddingPublicity biddingPublicity) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	BiddingPublicity biddingPublicityTemd = biddingPublicityService.getById(biddingPublicity.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(biddingPublicityTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("bidding_publicity");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				biddingPublicity=biddingPublicityService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				biddingPublicityService.save(biddingPublicity);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				biddingPublicity.setId(genId);
				processRunService.startProcess(processCmd);
				biddingPublicityService.save(biddingPublicity);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看补充通知/标底公示任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingPublicity> list=biddingPublicityService.getMyTodoTask(userId, new QueryFilter(request,"biddingPublicityItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingPublicityList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看补充通知/标底公示草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingPublicity> list=biddingPublicityService.getMyDraft(userId, new QueryFilter(request,"biddingPublicityItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingPublicityList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的补充通知/标底公示实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingPublicity> list=biddingPublicityService.getMyEnd(userId, new QueryFilter(request,"biddingPublicityItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingPublicityList",list);
		return mv;
	}
}