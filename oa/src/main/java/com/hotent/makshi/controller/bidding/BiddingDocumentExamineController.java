

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
import com.hotent.makshi.model.bidding.BiddingDocumentExamine;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.bidding.BiddingDocumentExamineService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
/**
 * 对象功能:招标文件审批 控制器类
 */
@Controller
@RequestMapping("/makshi/bidding/biddingDocumentExamine/")
public class BiddingDocumentExamineController extends BaseController
{
	@Resource
	private BiddingDocumentExamineService biddingDocumentExamineService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private ContractinfoService contractinfoService;
	/**
	 * 添加或更新招标文件审批。
	 * @param request
	 * @param response
	 * @param biddingDocumentExamine 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新招标文件审批")
	public void save(HttpServletRequest request, HttpServletResponse response,BiddingDocumentExamine biddingDocumentExamine) throws Exception
	{
		String resultMsg=null;		
		try{
			if(biddingDocumentExamine.getId()==null){
				biddingDocumentExamineService.save(biddingDocumentExamine);
				resultMsg=getText("添加","招标文件审批");
			}else{
			    biddingDocumentExamineService.save(biddingDocumentExamine);
				resultMsg=getText("更新","招标文件审批");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得招标文件审批分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看招标文件审批分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter filter = new QueryFilter(request,"biddingSchemeExamineItem");
		List<BiddingDocumentExamine> list=biddingDocumentExamineService.getAll(filter);
		ModelAndView mv=this.getAutoView().addObject("biddingDocumentExamineList",list);
		return mv;
	}
	
	/**
	 * 删除招标文件审批
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除招标文件审批")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			biddingDocumentExamineService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除招标文件审批成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑招标文件审批
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑招标文件审批")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		BiddingDocumentExamine biddingDocumentExamine=biddingDocumentExamineService.getById(id);
		if(BeanUtils.isEmpty(biddingDocumentExamine)){
			biddingDocumentExamine=new BiddingDocumentExamine();
			String document_applicant_script="return scriptImpl.getCurrentName();";
			biddingDocumentExamine.setDocument_applicant(engine.executeObject(document_applicant_script, null).toString());
		}
		
		return getAutoView().addObject("biddingDocumentExamine",biddingDocumentExamine)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得招标文件审批明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看招标文件审批明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		BiddingDocumentExamine biddingDocumentExamine=biddingDocumentExamineService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("biddingDocumentExamine", biddingDocumentExamine).addObject("runId", runId);
	}
	
	/**
	 * 流程url表单 绑定的表单明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		BiddingDocumentExamine biddingDocumentExamine = biddingDocumentExamineService.getById(id);	
		Long runId=0L;
		List<TaskOpinion> list = null;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
			 list = taskOpinionService.getByRunId(runId);
		}
		return getAutoView().addObject("biddingDocumentExamine", biddingDocumentExamine).addObject("taskOpinionList", list);
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
		BiddingDocumentExamine biddingDocumentExamine = biddingDocumentExamineService.getById(id);	
		return getAutoView().addObject("biddingDocumentExamine", biddingDocumentExamine);
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
	public void run(HttpServletRequest request, HttpServletResponse response,BiddingDocumentExamine biddingDocumentExamine) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	BiddingDocumentExamine biddingDocumentExamineTemd = biddingDocumentExamineService.getById(biddingDocumentExamine.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(biddingDocumentExamineTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("bidding_document");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				biddingDocumentExamine=biddingDocumentExamineService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				biddingDocumentExamineService.save(biddingDocumentExamine);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				biddingDocumentExamine.setId(genId);
				processRunService.startProcess(processCmd);
				biddingDocumentExamineService.save(biddingDocumentExamine);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看招标文件审批任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingDocumentExamine> list=biddingDocumentExamineService.getMyTodoTask(userId, new QueryFilter(request,"biddingDocumentExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingDocumentExamineList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看招标文件审批草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingDocumentExamine> list=biddingDocumentExamineService.getMyDraft(userId, new QueryFilter(request,"biddingDocumentExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingDocumentExamineList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的招标文件审批实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<BiddingDocumentExamine> list=biddingDocumentExamineService.getMyEnd(userId, new QueryFilter(request,"biddingDocumentExamineItem"));
		ModelAndView mv=this.getAutoView().addObject("biddingDocumentExamineList",list);
		return mv;
	}
}