package com.hotent.platform.webservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceException;

import org.activiti.engine.TaskService;

import com.alibaba.fastjson.JSONObject;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.api.FormService;

public class FormServiceImpl implements FormService {
	@Resource
	BpmFormHandlerService formHandlerService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	BpmService bpmService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmFormRunService bpmFormRunService;
	@Resource
	BpmFormTableService formTableService;
	@Resource
	private TaskService taskService;
	@Resource
	SysUserService sysUserService; 
	@Resource
	BpmNodeSetService bpmNodeSetService; 
	
	/***{instancId:\"10000006750003\",actDefId:\"\",ctxPath:\"/bpmx\",account:\"admin\"} **/
	@Override
	public String getFormHtml(String formInfo) {
	    JSONObject formParam = JSONObject.parseObject(formInfo);
		String instanceId = formParam.getString("instanceId");
		String actDefId = formParam.getString("actDefId");
		String ctxPath = formParam.getString("ctxPath");
		String account = formParam.getString("account");
		JSONObject result = new JSONObject();
		
		try {
			//校验参数
			if(StringUtil.isEmpty("ctxPath") || (StringUtil.isEmpty(instanceId) && StringUtil.isEmpty(actDefId)) || StringUtil.isEmpty(account)){
				throw new RuntimeException("调用参数不合法，请检查！"+formInfo);
			}
			
			//设置线程中当前用户
			SysUser user =sysUserService.getByAccount(account);
			if(user == null) throw new RuntimeException("通过账号："+account+"尚未查到用户！");
			ContextUtil.setCurrentUser((ISysUser) user); 
			
			String html = "";
			//流程实例获取表单
			if(StringUtil.isNotEmpty(instanceId)) 
				html = getFromHtmlByInstance(Long.parseLong(instanceId),ctxPath,user.getUserId());
			//Act流程定义获取表单
			else
				html = getFormHtmlByActDefId(actDefId,"",ctxPath,user.getUserId());
			
			result.put("result", "true");
			result.put("html", html); 
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "false");
			result.put("message", "获取表单出错  :"+ e.getMessage()); 
		}
		return result.toJSONString();
	}

	/**
	 * 通过流程实例获取流程在线表单
	 */
	private String getFromHtmlByInstance(Long instanceId, String ctxPath, Long userId) throws Exception {
		ProcessRun processRun =  processRunService.getById(instanceId);
		String nodeId = "-1";
		Map<String, Object> variables = new HashMap<String, Object>();
		
		List<ProcessTask> tasks = processRunService.getTasksByRunId(instanceId);
		if(BeanUtils.isNotEmpty(tasks)) {
			nodeId = tasks.get(0).getTaskDefinitionKey();
			variables =taskService.getVariables(tasks.get(0).getId());
		}
		
		FormModel form = bpmFormDefService.getNodeForm(processRun, nodeId, userId, ctxPath, variables,true);
		return form.getFormHtml();
	}
	
	
	/***通过ACT流程定义ID 获取流程在线表单**/
	private String getFormHtmlByActDefId(String actDefId,String businessKey,String ctxPath, Long userId) throws Exception  {
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getStartBpmNodeSet(actDefId, false);
		
		if(bpmNodeSet== null) throw new WebServiceException("尚未配置表单！");
		if(bpmNodeSet.getFormType() != BpmConst.OnLineForm) throw new WebServiceException("仅支持在线表单");
		
		String formKey = bpmNodeSet.getFormKey();
		
		if (StringUtil.isEmpty(formKey)) {
			throw new WebServiceException("尚未配置表单！");
		}
		
		BpmFormDef bpmFormDef = bpmFormDefService.getDefaultPublishedByFormKey(formKey);
		BpmFormTable bpmFormTable = formTableService.getById(bpmFormDef.getTableId());
		bpmFormDef.setTableName(bpmFormTable.getTableName());

		return  formHandlerService.obtainHtml(bpmFormDef,businessKey, "", actDefId, 
									bpmNodeSet.getNodeId(), ctxPath, "",false,false,false);
		
		
	}
}
