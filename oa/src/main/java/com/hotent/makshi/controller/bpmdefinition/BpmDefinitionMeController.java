/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-14，cp创建。 
 */
package com.hotent.makshi.controller.bpmdefinition;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.service.bpm.BpmDefinitionService;

@Controller
@RequestMapping("/bpm/bpmDefinition/me/")
public class BpmDefinitionMeController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private BpmDefinitionService bpmDefinitionService;

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
			log.error("错误信息",e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	@RequestMapping("forward")
	public void forward(String key, HttpServletRequest request, HttpServletResponse response) {
		try {
			BpmDefinition bpmDefinition = bpmDefinitionService.getMainDefByActDefKey(key);
			if (null != bpmDefinition)
				response.sendRedirect("/platform/bpm/task/startFlowForm.ht?defId=" + bpmDefinition.getDefId());
			else {
				JSONObject json = new JSONObject();
				json.put("msg", "没有流程actDefKey为：" + key);
				writeJsonAjaxResponse(response, json.toString());
			}
		} catch (IOException e) {
			log.error("错误信息",e);
		}
	}

	@RequestMapping("getDefId")
	public void getDefId(String key, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		BpmDefinition bpmDefinition = bpmDefinitionService.getMainDefByActDefKey(key);
		if (null == bpmDefinition) {
			json.put("result", false);
			writeJsonAjaxResponse(response, json.toString());
			return;
		} else {
			json.put("result", true);
			json.put("defId", bpmDefinition.getDefId());
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
	}

}
