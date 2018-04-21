package com.hotent.platform.controller.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.util.ServiceUtil;

/**
 * 对象功能:流程任务审批意见控制器类 开发公司:广州宏天软件有限公司 开发人员:csx 创建时间:2012-01-12 09:59:09
 */
@Controller
@RequestMapping(value={"/platform/bpm/taskOpinion/","/weixin/bpm/taskOpinion/"})
public class TaskOpinionController extends BaseController {
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private ProcessRunService processRunService;

	/**
	 * 取得某个流程任务审批意见分页列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看流程任务审批意见分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String preUrl = RequestUtil.getPrePage(request);
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		if (runId.equals(0L)) {
			return ServiceUtil.getTipInfo("请输入流程运行ID");
		}
		
		Map<String,Object> rtnMap= getOptionByRunId(runId);

		ModelAndView mv = this.getAutoView()
				.addObject("taskOpinionList", rtnMap.get("taskOpinionList"))
				.addObject("processRun", rtnMap.get("processRun"))
				.addObject("returnUrl", preUrl);
		return mv;
	}
	
	@RequestMapping("getOpinionByRunid")
	@ResponseBody
	public Map<String,JSONArray> getOpinionByRunid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		if (runId.equals(0L)) {
			return null;
		}
		Map<String, Object> map=getOptionByRunId(runId);
		Map<String, JSONArray> formatData = formatData(map);
		return formatData;
	}
	
	private Map<String,JSONArray> formatData(Map<String, Object> map) {
		Map<String,JSONArray> result=new HashMap<String, JSONArray>();
		JSONArray object = (JSONArray) map.get("taskOpinionList");
		for(int i=0;i<object.size();i++){
			JSONObject jsonObject = object.getJSONObject(i);
			String taskName = (String) jsonObject.get("taskName");
			if(StringUtil.isNotEmpty(taskName)){
				JSONArray list = (JSONArray) jsonObject.get("list");
				for (Object object2 : list) {
					JSONArray jsonArray = result.get(taskName);
					if(jsonArray==null){
						result.put(taskName, new JSONArray());
					}
					jsonArray=result.get(taskName);
					jsonArray.add(object2);
				}
			}
		}
		return result;
	}


	/**
	 * 显示意见列表。
	 * @param runId
	 * @return
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public Map<String,Object> getOptionByRunId(@RequestParam(value="runId") Long runId){
		Map<String,Object> rtnMap=new HashMap<String,Object>();
		
		ProcessRun processRun = processRunService.getById(runId);
		//取得关联的流程实例ID
		List<TaskOpinion> list = taskOpinionService.getByRunId(runId);
		if(null!=list && list.size()>0){
			for(TaskOpinion taskOpinion:list){
				if(taskOpinion.getOpinion()!=null && !taskOpinion.getOpinion().equals("") 
						&& taskOpinion.getOpinion().indexOf("<a")>=0){
					String opinion=taskOpinion.getOpinion().replaceAll("</a>", "");
					String hrefStr=opinion.substring(opinion.indexOf("<a"),opinion.indexOf(">")+1);
					opinion=opinion.replace(hrefStr, "");
					taskOpinion.setOpinion(opinion);
				}
			}
		}
		//设置代码执行人
		list = taskOpinionService.setTaskOpinionExecutor(list);
		
		JSONArray jsonArray=formatTaskOpinionList(list);
		
		rtnMap.put("processRun", processRun);
		rtnMap.put("taskOpinionList", jsonArray);
		
		return rtnMap;
		
	}

	/**
	 * 取得某个流程任务审批意见分页列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getList")
	@Action(description = "查看流程任务审批意见分页列表")
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String runId = request.getParameter("runId");
		ProcessRun processRun = null;
		String actInstId = "";
		if (StringUtils.isNotEmpty(runId)) {
			processRun = processRunService.getById(new Long(runId));
			actInstId = processRun.getActInstId();
		}

		List<TaskOpinion> list = taskOpinionService.getByActInstId(actInstId);
		if (processRun == null) {
			processRun = processRunService.getByActInstanceId(new Long(actInstId));
		}
		ModelAndView mv = this.getAutoView().addObject("taskOpinionList", list).addObject("processRun", processRun);
		return mv;
	}

	/**
	 * 在表单中显示审批历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listform")
	@Action(description = "在表单中显示审批历史")
	public ModelAndView listform(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String actInstId = request.getParameter("actInstId");
		List<TaskOpinion> list = taskOpinionService.getByActInstId(actInstId);
		Map<String,Object> rtnMap= getOptionByRunId(Long.parseLong(actInstId));
		ModelAndView mv = this.getAutoView().addObject("taskOpinionList", rtnMap.get("taskOpinionList"));
		return mv;
	}

	@RequestMapping("dialog")
	@Action(description = "编辑流程抄送转发")
	public ModelAndView forward(HttpServletRequest request) throws Exception {
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();
		return getAutoView().addObject("handlersMap", handlersMap);

	}

	/**
	 * <pre>
	 * 把taskOpinion封装成jsonArray对象，组成结构: 
	 * 思路: 
	 * 按照时间排序，如果i上的taskName跟i+1上的一致就放在同一个json上 
	 * eg: 
	 * json:{taskName:name,list:[taskopinion1,taskopinion2,...]}
	 * </pre>
	 * @param list
	 * @return JSONArray
	 * @exception
	 * @since 1.0.0
	 */
	private JSONArray formatTaskOpinionList(List<TaskOpinion> list) {
		JSONArray jsonArray = new JSONArray();
		for (TaskOpinion to : list) {
			String tn = "";
			List<JSONObject> tos = null;
			JSONObject jsonObject = null;
			String taskName = to.getTaskName();
			if(StringUtil.isNotEmpty(to.getSuperExecution())){
				taskName=taskName+"【子流程】";
			}

			if (!jsonArray.isEmpty()) {
				jsonObject = jsonArray.getJSONObject(jsonArray.size() - 1);
				tn = jsonObject.getString("taskName");
				tos = (List<JSONObject>) jsonObject.get("list");
			}

			if (tn.equals(taskName)) {
				JSONObject jsonObj=convertOpinion(to);
				if(tos!=null){
					tos.add(jsonObj);
				}
			} else {
				jsonObject = new JSONObject();
				tos = new ArrayList<JSONObject>();
				JSONObject jsonObj=convertOpinion(to);
				tos.add(jsonObj);
				jsonObject.put("taskName", taskName);
				jsonObject.put("list", tos);
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}
	
	private JSONObject convertOpinion(TaskOpinion opinion){
		JSONObject jsonObj=new JSONObject();
		
		jsonObj.accumulate("opinionId", opinion.getOpinionId()) ;
		String taskName = opinion.getTaskName();
		if(StringUtil.isNotEmpty(opinion.getSuperExecution())){
			taskName=taskName+"【子流程】";
		}
		jsonObj.accumulate("taskName",taskName ) ;
		jsonObj.accumulate("exeFullname", opinion.getExeFullname()) ;
		jsonObj.accumulate("exeUserId", opinion.getExeUserId()) ;
		jsonObj.accumulate("status", opinion.getStatus()) ;
		jsonObj.accumulate("checkStatus", opinion.getCheckStatus()) ;
		jsonObj.accumulate("startTimeStr", opinion.getStartTimeStr()) ;
		jsonObj.accumulate("durTimeStr", opinion.getDurTimeStr()) ;
		jsonObj.accumulate("endTimeStr", opinion.getEndTimeStr()) ;
		jsonObj.accumulate("opinion", opinion.getOpinion()==null?"":opinion.getOpinion()) ;
		
		List<SysUser> users= opinion.getCandidateUsers();
		if(BeanUtils.isNotEmpty(users)){
			JSONArray ary=new JSONArray();
			for(SysUser user:users){
				JSONObject userJson=new JSONObject();
				userJson.accumulate("userId",user.getUserId());
				userJson.accumulate("fullname",user.getFullname());
				ary.add(userJson);
			}
			jsonObj.accumulate("candidateUsers", ary) ;
		}
		
		
		return jsonObj;
	}

}
