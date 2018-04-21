/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-13，cp创建。 
 */
package com.hotent.makshi.controller.assess;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.IPosition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.assess.EmployeeAssess;
import com.hotent.makshi.service.assess.EmployeeAssessService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.MessageUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

@Controller
@RequestMapping("/makshi/assess/employee/")
public class EmployeeAssessController extends BaseController {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private EmployeeAssessService employeeAssessService;
	@Autowired
	private ProcessRunService processRunService;
	@Autowired
	private BpmDefinitionService bpmDefinitionService;

	private static Logger logger=Logger.getLogger(EmployeeAssessController.class);
	/**
	 * 向请求响应写异步的json格式消息
	 * 
	 * @param response
	 * @param jsonString
	 */
	protected void writeJsonAjaxResponse(HttpServletResponse response, String jsonString) {
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType("application/json; charset=UTF-8");
			writer = response.getWriter();
			writer.write(jsonString);
			writer.flush();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	@RequestMapping("main")
	public ModelAndView main(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status, HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("makshi/assess/employee-main.jsp").addObject("submitAt", submitAt).addObject("status", status);
	}

	@RequestMapping("total")
	public void total(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status, HttpServletRequest request,
			HttpServletResponse response) {
		int total = employeeAssessService.count(ContextUtil.getCurrentUserId() + "", submitAt, status);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("page")
	public ModelAndView page(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page, @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		List<EmployeeAssess> list = employeeAssessService.page(ContextUtil.getCurrentUserId() + "", submitAt, status, page, pageSize);
		return new ModelAndView("makshi/assess/employee-page.jsp").addObject("list", list).addObject("page", page).addObject("pageSize", pageSize);
	}

	@RequestMapping("edit")
	public ModelAndView edit(@RequestParam(required = false, value = "id") Long id) {
		ModelAndView model = new ModelAndView("makshi/assess/employee-edit.jsp");
		if (null != id && id != 0) {
			model.addObject("dto", employeeAssessService.getById(id));
		} else {
			ISysUser user = ContextUtil.getCurrentUser();
			EmployeeAssess employeeAssess = new EmployeeAssess();
			employeeAssess.setEmployeeID(user.getUserId() + "");
			employeeAssess.setEmployee(user.getFullname());
			ISysOrg org = ContextUtil.getCurrentOrg();
			if (null != org) {
				employeeAssess.setDepartment(org.getOrgPathname().replace("/深水咨询/", ""));
				employeeAssess.setDepartmentID(org.getOrgId() + "");
			}
			IPosition po = ContextUtil.getCurrentPos();
			if (null != po)
				employeeAssess.setPost_name(po.getPosName());
			employeeAssess.setEvaluation_at(new Date());
			model.addObject("dto", employeeAssess);
		}
		return model;
	}

	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(required = true, value = "id") Long id) {
		ModelAndView model = new ModelAndView("makshi/assess/employee-detail.jsp");
		model.addObject("dto", employeeAssessService.getById(id));
		return model;
	}

	@RequestMapping("save")
	public void save(EmployeeAssess employeeAssess, HttpServletRequest request, HttpServletResponse response) {
		String resultMsg = null;
		Date date = DateUtils.toDateFromStr(employeeAssess.getEvalAt().replace("年", "-").replace("月", "-").concat("01 00:00:00"));
		ISysUser user = ContextUtil.getCurrentUser();
		int count = employeeAssessService.getBySubmitAtNotExculetId(employeeAssess.getId(), user.getUserId(), date);
		if (count > 0) {
			resultMsg = getText("考评日期为【" + employeeAssess.getEvalAt() + "】已有数据,不能重复添加", "");
			try {
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			} catch (IOException e) {
				log.error("错误信息",e);
			}
			return;
		}
		if (employeeAssess.getId() != null && employeeAssess.getId() != 0) {
			employeeAssess.setEvaluation_at(date);
			employeeAssessService.update(employeeAssess);
			resultMsg = getText("更新", "员工考核");
		} else {
			employeeAssess.setStatus(1);
			employeeAssess.setId(UniqueIdUtil.genId());
			employeeAssess.setEvaluation_at(date);

			employeeAssess.setEmployeeID(user.getUserId() + "");
			employeeAssess.setEmployee(user.getFullname());
			employeeAssessService.add(employeeAssess);
			resultMsg = getText("添加", "员工考核");
		}
		try {
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (IOException e) {
			log.error("错误信息",e);
		}
	}

	@RequestMapping("del")
	public void del(Long id, HttpServletRequest request, HttpServletResponse response) {
		employeeAssessService.delById(id);
		JSONObject json = new JSONObject();
		json.put("result", true);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("submit")
	public void submit(Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			out.print(runProcess(id));
		} catch (IOException e) {
			log.error("错误信息",e);
		}
	}

	private ResultMessage runProcess(Long id) {
		try {
			ISysUser user = ContextUtil.getCurrentUser();
			Long userId = user.getUserId();
			if (userId == 1l) {
				return new ResultMessage(ResultMessage.Fail, "超级管理员不能发起流程");
			}
			String formData = getFormData(id);
			if (formData == null) {
				return new ResultMessage(ResultMessage.Fail, "该员工考核不存在");
			}
			if ("error".equals(formData)) {
				return new ResultMessage(ResultMessage.Fail, "该员工考核已被提交审批");
			}
			BpmDefinition bpmDefinition = bpmDefinitionService.getMainDefByActDefKey("bgsygkh");
			if (null == bpmDefinition) {
				return new ResultMessage(ResultMessage.Fail, "办公室员工考核流程未定义");
			}
			Map<String, Object> formDataMap = new HashMap<>();
			formDataMap.put("curUserId", userId + "");
			formDataMap.put("curUserName", user.getFullname());
			formDataMap.put("actDefId", bpmDefinition.getActDefId());
			formDataMap.put("defId", bpmDefinition.getDefId() + "");
			formDataMap.put("businessKey", "");
			formDataMap.put("startNode", "");
			formDataMap.put("formKey", "employee_assess");
			formDataMap.put("formData", formData);

			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setFormData(formData);
			processCmd.setFormDataMap(formDataMap);
			processCmd.setCurrentUserId(userId.toString());
			processCmd.setActDefId(bpmDefinition.getActDefId());
			processCmd.addTransientVar("bpm_definition", bpmDefinition);
			processCmd.setInformType(bpmDefinition.getInformType());
			processCmd.setVoteContent("");
			processCmd.setRelRunId(0L);
			// processCmd.setBusinessKey(temp);
			// processCmd.setStartNode(temp);
			processRunService.startProcess(processCmd);
			return new ResultMessage(ResultMessage.Success, "启动流程成功!");
		} catch (Exception ex) {
			logger.debug("startFlow:" + ex.getMessage());
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				return new ResultMessage(ResultMessage.Fail, "启动流程失败:\r\n" + str);
			} else {
				String message = ex.getMessage();
				if (StringUtil.isEmpty(message)) {
					message = ex.getCause().getMessage();
				}
				return new ResultMessage(ResultMessage.Fail, message);
			}
		}
	}

	private String getFormData(Long id) {
		EmployeeAssess employeeAssess = employeeAssessService.getById(id);
		if (employeeAssess == null) {
			return null;
		}
		employeeAssess.setBussinessDataId(id + "");
		employeeAssess.setStatus(2);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "id", "evalAt", "total" });
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
				if (value instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format(value);
				}
				return value;
			}

			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return arg0;
			}
		});

		JSONObject fromObject = JSONObject.fromObject(employeeAssess, jsonConfig);
		JSONObject jsonData = new JSONObject();
		JSONObject main = new JSONObject();
		main.put("fields", fromObject.toString());
		jsonData.put("main", main.toString());
		jsonData.put("sub", "[]");
		jsonData.put("opinion", "[]");
		return jsonData.toString();
	}
}
