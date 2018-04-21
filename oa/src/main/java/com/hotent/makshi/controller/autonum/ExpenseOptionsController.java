package com.hotent.makshi.controller.autonum;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.autonum.ExpenseOptions;
import com.hotent.makshi.service.autonum.ExpenseOptionsService;
import com.hotent.platform.annotion.Action;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/expense/options/")
public class ExpenseOptionsController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ExpenseOptionsService expenseOptionsService;

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

	@RequestMapping("list")
	@Action(description = "查看测试表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "expenseItem");
		List<ExpenseOptions> list = expenseOptionsService.getAll(queryFilter);
		ModelAndView mv = new ModelAndView("makshi/expense/expenseList.jsp").addObject("expenseList", list);
		return mv;
	}

	@RequestMapping("json")
	public void json(HttpServletRequest request, HttpServletResponse response) {
		List<ExpenseOptions> list = expenseOptionsService.getAll();
		JSONObject json = new JSONObject();
		json.put("result", false);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		JSONArray arr = new JSONArray();
		for (ExpenseOptions expenseOptions : list) {
			JSONObject e = new JSONObject();
			e.put("name", expenseOptions.getName());
			arr.add(e);
		}
		json.put("items", arr);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("edit")
	@Action(description = "编辑")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		ExpenseOptions expenseOptions = expenseOptionsService.getById(id);

		return new ModelAndView("makshi/expense/expenseEdit.jsp").addObject("expense", expenseOptions).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("save")
	@Action(description = "添加或更新")
	public void save(HttpServletRequest request, HttpServletResponse response, ExpenseOptions expenseOptions) throws Exception {
		String resultMsg = null;
		try {
			if (expenseOptions.getId() == null) {
				expenseOptionsService.save(expenseOptions);
				resultMsg = getText("添加", "选项");
			} else {
				expenseOptionsService.save(expenseOptions);
				resultMsg = getText("更新", "选项");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("del")
	@Action(description = "删除")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			expenseOptionsService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
