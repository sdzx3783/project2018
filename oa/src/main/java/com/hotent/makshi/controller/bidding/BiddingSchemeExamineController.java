

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
import com.hotent.makshi.model.bidding.BiddingSchemeExamine;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.bidding.BiddingSchemeExamineService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
/**
 * 对象功能:招标方案审批 控制器类
 */
@Controller
@RequestMapping("/makshi/bidding/biddingSchemeExamine/")
public class BiddingSchemeExamineController extends BaseController
{
	@Resource
	private BiddingSchemeExamineService biddingSchemeExamineService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private ContractinfoService contractinfoService;
	/**
	 * 添加或更新招标方案审批。
	 * @param request
	 * @param response
	 * @param biddingSchemeExamine 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新招标方案审批")
	public void save(HttpServletRequest request, HttpServletResponse response,BiddingSchemeExamine biddingSchemeExamine) throws Exception
	{
		String resultMsg=null;		
		try{
			if(biddingSchemeExamine.getId()==null){
				biddingSchemeExamineService.save(biddingSchemeExamine);
				resultMsg=getText("添加","招标方案审批");
			}else{
			    biddingSchemeExamineService.save(biddingSchemeExamine);
				resultMsg=getText("更新","招标方案审批");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得招标方案审批分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看招标方案审批分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter filter = new QueryFilter(request,"biddingSchemeExamineItem");
		List<BiddingSchemeExamine> list=biddingSchemeExamineService.getAll(filter);
		
		ModelAndView mv=this.getAutoView().addObject("biddingSchemeExamineList",list);
		return mv;
	}
	
	/**
	 * 删除招标方案审批
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除招标方案审批")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			biddingSchemeExamineService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除招标方案审批成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑招标方案审批
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑招标方案审批")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		BiddingSchemeExamine biddingSchemeExamine=biddingSchemeExamineService.getById(id);
		if(BeanUtils.isEmpty(biddingSchemeExamine)){
			biddingSchemeExamine=new BiddingSchemeExamine();
			String scheme_applicant_script="return scriptImpl.getCurrentName();";
			biddingSchemeExamine.setScheme_applicant(engine.executeObject(scheme_applicant_script, null).toString());
		}
		
		return getAutoView().addObject("biddingSchemeExamine",biddingSchemeExamine)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得招标方案审批明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看招标方案审批明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		BiddingSchemeExamine biddingSchemeExamine=biddingSchemeExamineService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("biddingSchemeExamine", biddingSchemeExamine).addObject("runId", runId);
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
		BiddingSchemeExamine biddingSchemeExamine = biddingSchemeExamineService.getById(id);	
		Long runId=0L;
		List<TaskOpinion> list = null;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		    list = taskOpinionService.getByRunId(runId);
		}
		return getAutoView().addObject("biddingSchemeExamine", biddingSchemeExamine).addObject("taskOpinionList", list);
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
		BiddingSchemeExamine biddingSchemeExamine = biddingSchemeExamineService.getById(id);	
		return getAutoView().addObject("biddingSchemeExamine", biddingSchemeExamine);
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
	public void run(HttpServletRequest request, HttpServletResponse response,BiddingSchemeExamine biddingSchemeExamine) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	BiddingSchemeExamine biddingSchemeExamineTemd = biddingSchemeExamineService.getById(biddingSchemeExamine.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(biddingSchemeExamineTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("biddings_cheme");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				biddingSchemeExamine=biddingSchemeExamineService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				biddingSchemeExamineService.save(biddingSchemeExamine);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				biddingSchemeExamine.setId(genId);
				processRunService.startProcess(processCmd);
				biddingSchemeExamineService.save(biddingSchemeExamine);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看招标方案审批任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingSchemeExamine> list=biddingSchemeExamineService.getMyTodoTask(userId, new QueryFilter(request,"biddingSchemeExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingSchemeExamineList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看招标方案审批草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingSchemeExamine> list=biddingSchemeExamineService.getMyDraft(userId, new QueryFilter(request,"biddingSchemeExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingSchemeExamineList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的招标方案审批实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingSchemeExamine> list=biddingSchemeExamineService.getMyEnd(userId, new QueryFilter(request,"biddingSchemeExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingSchemeExamineList",list);
		return mv;
	}
}