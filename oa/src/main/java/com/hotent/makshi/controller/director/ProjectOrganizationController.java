package com.hotent.makshi.controller.director;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.service.director.ProjectOrganizationService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/director/organization/")
public class ProjectOrganizationController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ProjectOrganizationService projectOrganizationService;

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

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("makshi/director/organization-list.jsp");
	}

	@RequestMapping("total")
	public void total(HttpServletRequest request, HttpServletResponse response) {
		int total = projectOrganizationService.count();
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("page")
	public ModelAndView page(@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("makshi/director/organization-page.jsp").addObject("list", projectOrganizationService.page(page, pageSize)).addObject("page", page).addObject("pageSize", pageSize);
	}

}
