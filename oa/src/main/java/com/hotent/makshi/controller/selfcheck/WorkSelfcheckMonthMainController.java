/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-11，cp创建。 
 */
package com.hotent.makshi.controller.selfcheck;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.selfcheck.WorkSelfcheckMonthItem;
import com.hotent.makshi.model.selfcheck.WorkSelfcheckMonthMain;
import com.hotent.makshi.service.selfcheck.WorkSelfcheckMonthItemService;
import com.hotent.makshi.service.selfcheck.WorkSelfcheckMonthMainService;

@Controller
@RequestMapping("/makshi/selfcheck/monthmain/")
public class WorkSelfcheckMonthMainController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WorkSelfcheckMonthMainService workSelfcheckMonthMainService;
	@Autowired
	private WorkSelfcheckMonthItemService workSelfcheckMonthItemService;

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

	@RequestMapping("main")
	public ModelAndView main(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status, HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("makshi/selfcheck/monthmain-main.jsp").addObject("submitAt", submitAt).addObject("status", status);
	}

	@RequestMapping("total")
	public void total(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status, HttpServletRequest request,
			HttpServletResponse response) {
		int total = workSelfcheckMonthMainService.count(ContextUtil.getCurrentUserId() + "", submitAt, status);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("page")
	public ModelAndView page(@RequestParam(required = false, value = "submitAt") String submitAt, @RequestParam(required = false, value = "status") Integer status,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page, @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		List<WorkSelfcheckMonthMain> list = workSelfcheckMonthMainService.page(ContextUtil.getCurrentUserId() + "", submitAt, status, page, pageSize);
		return new ModelAndView("makshi/selfcheck/monthmain-page.jsp").addObject("list", list).addObject("page", page).addObject("pageSize", pageSize);
	}

	@RequestMapping("edit")
	public ModelAndView edit(@RequestParam(required = false, value = "id") Long id) {
		ModelAndView model = new ModelAndView("makshi/selfcheck/monthmain-edit.jsp");
		if (null != id && id != 0) {
			model.addObject("dto", workSelfcheckMonthMainService.getById(id));
			model.addObject("items", workSelfcheckMonthItemService.getAllByMainId(id));
		} else {
			id = workSelfcheckMonthMainService.getLastId(ContextUtil.getCurrentUserId());
			if (null != id && id != 0)
				model.addObject("items", workSelfcheckMonthItemService.getAllByMainId(id));
		}
		return model;
	}

	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(required = true, value = "id") Long id) {
		ModelAndView model = new ModelAndView("makshi/selfcheck/monthmain-detail.jsp");
		model.addObject("dto", workSelfcheckMonthMainService.getById(id));
		model.addObject("items", workSelfcheckMonthItemService.getAllByMainId(id));
		return model;
	}

	@RequestMapping("save")
	public void save(WorkSelfcheckMonthMain main, @RequestParam(required = false, value = "items") String items, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		ISysUser user = ContextUtil.getCurrentUser();
		int count = workSelfcheckMonthMainService.getBySubmitAtNotExculetId(main.getId(), user.getUserId(), main.getSubmitAt());
		if (count > 0) {
			json.put("result", false);
			json.put("msg", "月份为【" + main.getSubmitAt() + "】已有数据,不能重复添加");
			writeJsonAjaxResponse(response, json.toString());
			return;
		}

		if (main.getId() != null && main.getId() != 0) {
			workSelfcheckMonthMainService.update(main);
		} else {
			main.setId(UniqueIdUtil.genId());
			main.setUserId(user.getUserId() + "");
			main.setUsername(user.getFullname());
			workSelfcheckMonthMainService.add(main);
		}

		workSelfcheckMonthItemService.delAllByMainId(main.getId());

		if (StringUtils.isNotEmpty(items)) {
			JSONArray arry = JSONArray.fromObject(items);
			for (Object object : arry) {
				JSONObject o = JSONObject.fromObject(object);
				WorkSelfcheckMonthItem item = new WorkSelfcheckMonthItem();
				item.setId(UniqueIdUtil.genId());
				item.setContent(o.containsKey("content") ? o.getString("content") : null);
				item.setRequires(o.containsKey("require") ? o.getString("require") : null);
				item.setChecks(o.containsKey("checks") ? o.getString("checks") : null);
				item.setMainId(main.getId());
				workSelfcheckMonthItemService.add(item);
			}
		}
		json.put("result", true);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("submit")
	public void submit(Long id, HttpServletRequest request, HttpServletResponse response) {
		workSelfcheckMonthMainService.submit(id);
		JSONObject json = new JSONObject();
		json.put("result", true);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("del")
	public void del(Long id, HttpServletRequest request, HttpServletResponse response) {
		workSelfcheckMonthMainService.delById(id);
		JSONObject json = new JSONObject();
		json.put("result", true);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("json")
	public void json(HttpServletRequest request, HttpServletResponse response) {
		List<WorkSelfcheckMonthMain> list = workSelfcheckMonthMainService.page(ContextUtil.getCurrentUserId() + "", null, null, 1, 3);
		JSONObject json = new JSONObject();
		if (CollectionUtils.isEmpty(list)) {
			json.put("result", false);
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		JSONArray arry = new JSONArray();
		for (WorkSelfcheckMonthMain work : list) {
			JSONObject e = new JSONObject();
			e.put("id", work.getId());
			e.put("username", work.getUsername());
			e.put("submitAt", work.getSubmitAt());
			e.put("status", work.getStatus());
			arry.add(e);
		}
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}
}
