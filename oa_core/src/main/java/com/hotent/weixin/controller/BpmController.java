package com.hotent.weixin.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.page.PageList;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.bpm.BpmTaskExeDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmTaskExe;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.BpmMobileFormDef;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmDefAuthorizeService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.CommuReceiverService;
import com.hotent.platform.service.bpm.ITaskService;
import com.hotent.platform.service.bpm.impl.BpmActService;
import com.hotent.platform.service.form.BpmFormRightsService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.form.BpmMobileFormDefService;
import com.hotent.platform.service.form.TaskReadService;
import com.hotent.weixin.model.ListModel;
import com.hotent.weixin.service.IWeixinFormService;
import com.hotent.weixin.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/weixin/bpm/")
public class BpmController extends GenericController {
	
	@Resource 
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmDefAuthorizeService bpmDefAuthorizeService; 
	@Resource
	private IWeixinFormService iWeixinFormService; 
	@Resource
	private BpmMobileFormDefService bpmMobileFormDefService;
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private BpmFormRightsService bpmFormRightsService;
	@Resource
	private ITaskService taskServiceImpl;
	@Resource
	private BpmService bpmService;
	@Resource 
	private TaskReadService taskReadService;
	@Resource
	private CommuReceiverService commuReceiverService;
	@Resource 
	private TaskService taskService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private BpmTaskExeDao bpmTaskExeDao;
	@Resource
	private BpmActService bpmActService;
	
	
	/**
	 * 返回我的流程定义列表。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyDef")
	@ResponseBody
	public List<BpmDefinition> getMyDef(HttpServletRequest request, HttpServletResponse response){
		QueryFilter filter = new QueryFilter(request, false);
		Long typeId = RequestUtil.getLong(request, "typeId", 0);
		// 增加流程分管授权的启动权限分配查询判断
		Long userId = ContextUtil.getCurrentUserId();
		String isNeedRight = "";
		if (!ContextUtil.isSuperAdmin()) {
			isNeedRight = "yes";
			// 获得流程分管授权与用户相关的信息
			Map<String, Object> actRightMap = bpmDefAuthorizeService.getActRightByUserMap(userId,
							BPMDEFAUTHORIZE_RIGHT_TYPE.START, false, false);
			// 获得流程分管授权与用户相关的信息集合的流程KEY
			String actRights = (String) actRightMap.get("authorizeIds");
			filter.addFilter("actRights", actRights);
		}
		filter.addFilter("isNeedRight", isNeedRight);
		filter.addFilter("allowMobile", 1);
		List<BpmDefinition> list = bpmDefinitionService.getMyDefList(filter,typeId);
		return list;
	}
	
	/**
	 * 获取发起流程表单模版。
	 * <pre>
	 * 返回数据如下：
	 * {result:true,get:true,formKey:"",version:1,template:"",data:""};
	 * result:获取表单数据结果
	 * get：表示是否获取表单。
	 * 	true：表示获取表单。
	 *  false：表示客户端的表单和服务端一致。
	 * 		那么不需要之后的json数据。
	 * 		formKey:表单key
	 * 		version:表单版本
	 * 		template:angularjs表单模版。
	 * data:表单数据
	 * permission:表单权限
	 * 
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getStartForm")
	@ResponseBody
	public JSONObject getStartForm(HttpServletRequest request, HttpServletResponse response) {
		Long defId=RequestUtil.getLong(request, "defId",0L);
		String formKey=RequestUtil.getString(request, "formKey", "");
		String pk=RequestUtil.getString(request, "pk", "");
		int version=RequestUtil.getInt(request, "version",0);
		JSONObject json=new JSONObject();
		try{
			BpmDefinition bpmDefinition=bpmDefinitionService.getById(defId);
			
			BpmMobileFormDef formDef= iWeixinFormService.getByDefId(defId, formKey, version);
			if(formDef==null){
				json.accumulate("result", false);
				json.accumulate("msg", "没有找到表单");
				return json;
			}
			Long tableId=formDef.getTableId();
			BpmFormTable formTable=bpmFormTableService.getByTableId(tableId, 1);
			
			BpmNodeSet nodeSet=formDef.getBpmNodeSet();
			String nodeId="";
			if(StringUtil.isNotEmpty(nodeSet.getNodeId())){
				nodeId=nodeSet.getNodeId();
			}
			
			//计算表单权限。
			JSONObject permission= bpmFormRightsService.getByFormKeyAndUserId(formDef.getPcFormKey(), formTable, bpmDefinition.getActDefId(), nodeId, "", false);
			//获取数据
			JSONObject data=null;
			
			if(StringUtil.isEmpty(pk)){
				data=iWeixinFormService.getByFormTable(formTable, true);
			}
			else{
				data=iWeixinFormService.getDraftData(formTable, pk, true);
			}
			//计算返回数据。
			getRtnJson(json, formDef, permission, data);
		}
		catch(Exception ex){
			json.accumulate("result", false);
			json.accumulate("msg", ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}

	/**
	 * 计算返回数据，返回数据包括表单，权限和实际的数据。
	 * @param rtnJson
	 * @param formDef		表单定义
	 * @param permission	权限
	 * @param data			数据
	 */
	private void getRtnJson(JSONObject rtnJson, BpmMobileFormDef formDef, JSONObject permission, JSONObject data) {
		if(StringUtil.isEmpty(formDef.getFormHtml())){
			rtnJson.accumulate("get", false);
		}
		else{
			rtnJson.accumulate("get", true);
			rtnJson.accumulate("formKey", formDef.getFormKey());
			rtnJson.accumulate("version", formDef.getVersion());
			String template=formDef.getFormHtml();
			rtnJson.accumulate("template", template);
		}
		//将数据进行权限计算。
		calcData(data,permission);
		//数据
		rtnJson.accumulate("data", data);
		//权限
		rtnJson.accumulate("permission", permission);
		
		rtnJson.accumulate("result", true);
	}
	
	/**
	 * 根据表单权限和数据计算，有权限的数据，排除没有权限的数据。
	 * 1.主表数据。
	 * 2.子表不显示删除子表的数据。
	 * 3.子表字段数据。
	 * @param data
	 * @param permission
	 */
	private void calcData(JSONObject data,JSONObject permission){
		//主表计算
		JSONObject jsonMainData= data.getJSONObject("main");
		JSONObject jsonMainPerm= permission.getJSONObject("main");
		Iterator itMainPerm= jsonMainPerm.keys();
		while(itMainPerm.hasNext()){
			String key=(String) itMainPerm.next();
			String right=(String) jsonMainPerm.get(key);
			if(!right.equals("n")) continue;
			//删除没有权限的数据
			jsonMainData.remove(key);
			jsonMainData.remove(key+"id");
		}
		
		//子表计算
		JSONObject jsonSubTableData= data.getJSONObject("sub");
		
		JSONObject jsonSubTablePermission= permission.getJSONObject("table");
		
		//子表为空则返回。
		if(BeanUtils.isEmpty(jsonSubTablePermission)) return;
		
		//子表字段权限
		JSONObject fieldsTablePerm=permission.getJSONObject("fields");

		Iterator itSubTablePermission= jsonSubTablePermission.keys();
		//遍历子表。
		while(itSubTablePermission.hasNext()){
			String tbName=(String) itSubTablePermission.next();
			JSONObject rightSubTable=(JSONObject) jsonSubTablePermission.get(tbName);
			//如果子表为隐藏，则不再计算行数据，直接移除子表的数据。
			if(rightSubTable.getBoolean("hidden")){
				jsonSubTableData.remove(tbName);
				continue;
			}
			//处理子表字段
			JSONObject fieldsPerm=fieldsTablePerm.getJSONObject(tbName);
			//获取子表数据
			JSONObject subTableData= (JSONObject) jsonSubTableData.get(tbName);
			JSONArray rows=subTableData.getJSONArray("rows");
			//遍历子表字段。
			Iterator itFieldKeys= fieldsPerm.keys();
			while(itFieldKeys.hasNext()){
				String field=(String) itFieldKeys.next();
				String fieldPerm=fieldsPerm.getString(field);
				if(!fieldPerm.equals("n")) continue;
				//删除子表列数据。
				for(Object obj:rows){
					JSONObject row=(JSONObject) obj;
					row.remove(field);
					jsonMainData.remove(field+"id");
				}
			}
		}
		
	}
	
	/**
	 * 获取预览表单的permission/data。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getFormPreviewData")
	@ResponseBody
	public JSONObject getFormPreviewData(HttpServletRequest request, HttpServletResponse response) {
		Long tableId = RequestUtil.getLong(request, "tableId");
		BpmFormTable formTable=bpmFormTableService.getByTableId(tableId, 1);

		JSONObject permission = bpmFormRightsService.getByFormKeyAndUserId("", formTable, "", "", "", false);
		JSONObject data= iWeixinFormService.getByFormTable(formTable, true);
		
		JSONObject returnData = new JSONObject();
		returnData.put("permission", permission);
		returnData.put("data", data);
		return returnData;
	}
	
	/**
	 * 获取我的待办任务。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyTask")
	@ResponseBody
	public ListModel getMyTask(HttpServletRequest request, HttpServletResponse response) {
		QueryFilter queryFilter=new QueryFilter(request, true);
		List list= taskServiceImpl.getMyMobileTasks(queryFilter);
		ListModel listModel=CommonUtil.getListModel((PageList) list);
		return listModel;
	}
	
	/**
	 * 获取手机表单。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getTaskForm")
	@ResponseBody
	public JSONObject getTaskForm(HttpServletRequest request, HttpServletResponse response) {
 		String taskId = RequestUtil.getString(request, "taskId");
		String formKey=RequestUtil.getString(request, "formKey", "");
		int version=RequestUtil.getInt(request, "version",0);
		
		SysUser sysUser=(SysUser) ContextUtil.getCurrentUser();
		TaskEntity taskEntity = bpmService.getTask(taskId);
		
		String instanceId=taskEntity.getProcessInstanceId();
		
		String actDefId=taskEntity.getProcessDefinitionId();
		String nodeId=taskEntity.getTaskDefinitionKey();
		// 更新任务为已读。
		taskReadService.saveReadRecord(Long.parseLong(instanceId), Long.parseLong(taskId));
		// 设置沟通人员或流转人员查看状态。
		commuReceiverService.setCommuReceiverStatus(taskEntity, sysUser);
		
		ProcessRun processRun= processRunDao.getByActInstanceId(new Long( instanceId));
		
		Map<String, Object> variables = taskService.getVariables(taskId);
		
		String parentActDefId="";
		if (variables.containsKey(BpmConst.FLOW_PARENT_ACTDEFID)) {// 判断当前是否属于子流程任务
			parentActDefId = variables.get(BpmConst.FLOW_PARENT_ACTDEFID).toString();
		}
		//主键
		String businessKey= (String) variables.get("businessKey");
		
		JSONObject json=new JSONObject(); 
		try{
			
			BpmMobileFormDef formDef= iWeixinFormService.getByNodeId(actDefId, parentActDefId, nodeId, formKey, version);
			
			if(formDef==null){
				json.accumulate("result", false);
				json.accumulate("msg", "没有找到表单");
				return json;
			}
			
			Long tableId=formDef.getTableId();
			BpmFormTable formTable=bpmFormTableService.getByTableId(tableId, 1);
			
			JSONObject permission= bpmFormRightsService.getByFormKeyAndUserId(formDef.getPcFormKey(), formTable, actDefId,nodeId, parentActDefId, false);
			
			JSONObject data=iWeixinFormService.getApproveData(formTable, actDefId, nodeId, businessKey);
			
			getRtnJson(json, formDef, permission, data);
			
			//设置任务主题。
			json.accumulate("subject", processRun.getSubject());
			//获取是否会签。
			boolean isSignTask = bpmService.isSignTask(taskEntity);
			boolean isCanBack = bpmActService.isTaskAllowBack(taskId);
			boolean isFirstNode = NodeCache.isFirstNode(processRun.getActDefId(), nodeId);
			
			Map<String,Object> flowParam = new HashMap<String, Object>();
			flowParam.put("isSignTask",isSignTask);
			flowParam.put("isFirstNode", isFirstNode);
			flowParam.put("isCanBack", isCanBack);
			json.element("flowParam", flowParam); 
		}
		catch(Exception ex){
			json.accumulate("result", false);
			json.accumulate("msg", ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping("getInstForm")
	@Action(description = "获取实例表单")
	@ResponseBody
	public JSONObject getInstForm(HttpServletRequest request, HttpServletResponse response) {
		Long runId = RequestUtil.getLong(request,  "runId");
		String formKey=RequestUtil.getString(request, "formKey", "");
		int version=RequestUtil.getInt(request, "version",0);
		
		ProcessRun processRun=processRunDao.getById(runId);
		String actDefId=processRun.getActDefId();
		String nodeId="";
		String parentActDefId="";
		if(processRun.getStatus()==ProcessRun.STATUS_RUNNING){
			Map<String, Object> variables = runtimeService.getVariables(processRun.getActInstId());
			if (variables.containsKey(BpmConst.FLOW_PARENT_ACTDEFID)) {// 判断当前是否属于子流程任务
				parentActDefId = variables.get(BpmConst.FLOW_PARENT_ACTDEFID).toString();
			}
		}
		
		String businessKey= (String) processRun.getBusinessKey();
		
		JSONObject json=new JSONObject(); 
		try{
			BpmMobileFormDef formDef= iWeixinFormService.getInstByDefId(processRun, actDefId, parentActDefId, formKey, version);
			if(formDef==null){
				json.accumulate("result", false);
				json.accumulate("msg", "没有获取到实例表单");
			}
			else{
				//获取nodeId
				BpmNodeSet bpmNodeSet = formDef.getBpmNodeSet();
				if(bpmNodeSet!=null){
					nodeId=bpmNodeSet.getNodeId();
				}
				//end
				Long tableId=formDef.getTableId();
				BpmFormTable formTable=bpmFormTableService.getByTableId(tableId, 1);
				
				JSONObject permission= bpmFormRightsService.getByFormKeyAndUserId(formDef.getPcFormKey(), formTable, actDefId,nodeId, parentActDefId, true);
				
				JSONObject data=iWeixinFormService.getApproveData(formTable, actDefId, nodeId, businessKey);
				
				getRtnJson(json, formDef, permission, data);
				
				json.accumulate("subject", processRun.getSubject());
			}
		}
		catch(Exception ex){
			json.accumulate("result", false);
			json.accumulate("msg", ex.getMessage());
		}
		return json;
	} 
	
	 
	@RequestMapping("myRequestListJson")
	@Action(description = "查看我的请求还未结束列表")
	@ResponseBody
	public ListModel myRequestListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, true);
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		filter.addFilter("startFromMobile", 1);
		List<ProcessRun> list = processRunDao.getMyRequestList(filter);
		ListModel model=CommonUtil. getListModel((PageList)list);
		return model;
	
	}
	
	@RequestMapping("getMyCompletedListJson")
	@Action(description = "查看我的发起流程已经结束列表")
	@ResponseBody
	public ListModel getMyCompletedListJson(HttpServletRequest request, HttpServletResponse response){
		QueryFilter filter = new QueryFilter(request, true);
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		filter.addFilter("allowMobile", 1);
		List<ProcessRun> list = processRunDao.getMyCompletedList(filter);
		ListModel model=CommonUtil. getListModel((PageList)list);
		return model;
	}
	
	
	@RequestMapping("getAlreadyMattersList")
	@Action(description = "查看我的审批的事务(未结束)")
	@ResponseBody
	public ListModel getAlreadyMattersList(HttpServletRequest request, HttpServletResponse response){
		QueryFilter filter = new QueryFilter(request, true);
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		filter.addFilter("fromMobile", 1);
		List<ProcessRun> list = processRunDao.getAlreadyMattersList(filter);
		
		ListModel model=CommonUtil. getListModel((PageList)list);
		return model;
	}
	
	@RequestMapping("getCompletedMattersList")
	@Action(description = "查看我的审批的事务(已经结束)")
	@ResponseBody
	public ListModel getCompletedMattersList(HttpServletRequest request, HttpServletResponse response){
		QueryFilter filter = new QueryFilter(request, true);
		filter.addFilter("fromMobile", 1);
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());
		List<ProcessRun> list = processRunDao.getCompletedMattersList(filter);
		ListModel model=CommonUtil. getListModel((PageList)list);
		return model;
	}
	
	
	@RequestMapping("getMyTurnOutJson")
	@Action(description = "查看我转出去的事情")
	@ResponseBody
	public ListModel getMyTurnOutJson(HttpServletRequest request, HttpServletResponse response){
		QueryFilter filter = new QueryFilter(request, true);
		
		filter.addFilter("ownerId", ContextUtil.getCurrentUserId().toString());
		List<BpmTaskExe> list = bpmTaskExeDao.getMobileAccordingMattersList(filter);
		ListModel model=CommonUtil. getListModel((PageList)list);
		return model;
	}
	
	
	
	
	
	
	

}
