

package com.hotent.makshi.controller.river;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.river.HdDocumentReview;
import com.hotent.makshi.service.river.HdDocumentReviewService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
/**
 * 对象功能:文件 控制器类
 */
@Controller
@RequestMapping("/makshi/river/hdDocumentReview/")
public class HdDocumentReviewController extends BaseController
{
	@Resource
	private HdDocumentReviewService hdDocumentReviewService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskOpinionService taskOpinionService;
	/**
	 * 添加或更新文件。
	 * @param request
	 * @param response
	 * @param hdDocumentReview 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新文件")
	public void save(HttpServletRequest request, HttpServletResponse response,HdDocumentReview hdDocumentReview) throws Exception
	{
		String resultMsg=null;		
		try{
			if(hdDocumentReview.getId()==null){
				hdDocumentReviewService.save(hdDocumentReview);
				resultMsg=getText("添加","文件");
			}else{
			    hdDocumentReviewService.save(hdDocumentReview);
				resultMsg=getText("更新","文件");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得文件分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看文件分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<HdDocumentReview> list=hdDocumentReviewService.getAll(new QueryFilter(request,"hdDocumentReviewItem"));
		ModelAndView mv=this.getAutoView().addObject("hdDocumentReviewList",list);
		return mv;
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除文件")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			hdDocumentReviewService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除文件成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑文件")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		HdDocumentReview hdDocumentReview=hdDocumentReviewService.getById(id);
		
		return getAutoView().addObject("hdDocumentReview",hdDocumentReview)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得文件明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看文件明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		HdDocumentReview hdDocumentReview=hdDocumentReviewService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("hdDocumentReview", hdDocumentReview).addObject("runId", runId);
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
		String preUrl = RequestUtil.getPrePage(request);
		Long id=RequestUtil.getLong(request,"id");
		HdDocumentReview hdDocumentReview = hdDocumentReviewService.getById(id);
		Long runId=0L;
		List<TaskOpinion> list = null;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		Map<String,TaskOpinion> rtnMap=new LinkedHashMap<String, TaskOpinion>();
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		rtnMap= getOptionByRunId(runId);
		return getAutoView().addObject("hdDocumentReview", hdDocumentReview)
				.addObject("taskOpinionList", rtnMap.get("taskOpinionList"))
				.addObject("rtnMap", rtnMap)
				.addObject("returnUrl", preUrl);
	}
	

	public Map<String,TaskOpinion> getOptionByRunId(@RequestParam(value="runId") Long runId){
		Map<String,TaskOpinion> rtnMap=new LinkedHashMap<String,TaskOpinion>();
		
		//取得关联的流程实例ID
		List<TaskOpinion> list = taskOpinionService.getByRunId(runId);
		
		for (TaskOpinion taskOpinion : list) {
			String taskName = taskOpinion.getTaskName();
			if(StringUtils.isNotEmpty(taskName)){
				if(!rtnMap.containsKey(taskName)){
					taskOpinion.setTaskToken(DateUtils.format(taskOpinion.getStartTime()));
					rtnMap.put(taskName, taskOpinion);
				}else{
					TaskOpinion taskOpinion2 = rtnMap.get(taskName);
					String exeFullname = taskOpinion2.getExeFullname();
					String opinion = taskOpinion2.getOpinion();
					String startTimeStr = DateUtils.format(taskOpinion2.getStartTime());
					taskOpinion2.setExeFullname(exeFullname+","+taskOpinion.getExeFullname());
					taskOpinion2.setOpinion(opinion+","+taskOpinion.getOpinion());
					taskOpinion2.setTaskToken(startTimeStr+","+DateUtils.format(taskOpinion.getStartTime()));
				}
			}
		}
		
		
		return rtnMap;
		
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
		HdDocumentReview hdDocumentReview = hdDocumentReviewService.getById(id);	
		return getAutoView().addObject("hdDocumentReview", hdDocumentReview);
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
	public void run(HttpServletRequest request, HttpServletResponse response,HdDocumentReview hdDocumentReview) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	HdDocumentReview hdDocumentReviewTemd = hdDocumentReviewService.getById(hdDocumentReview.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(hdDocumentReviewTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("wjsclc");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				hdDocumentReview=hdDocumentReviewService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				hdDocumentReviewService.save(hdDocumentReview);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				hdDocumentReview.setId(genId);
				processRunService.startProcess(processCmd);
				hdDocumentReviewService.save(hdDocumentReview);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看文件任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HdDocumentReview> list=hdDocumentReviewService.getMyTodoTask(userId, new QueryFilter(request,"hdDocumentReviewItem"));
		ModelAndView mv=this.getAutoView().addObject("hdDocumentReviewList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看文件草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HdDocumentReview> list=hdDocumentReviewService.getMyDraft(userId, new QueryFilter(request,"hdDocumentReviewItem"));
		ModelAndView mv=this.getAutoView().addObject("hdDocumentReviewList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的文件实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<HdDocumentReview> list=hdDocumentReviewService.getMyEnd(userId, new QueryFilter(request,"hdDocumentReviewItem"));
		ModelAndView mv=this.getAutoView().addObject("hdDocumentReviewList",list);
		return mv;
	}
}