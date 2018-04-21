package com.hotent.makshi.controller.autonum;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.service.autonum.AutoNumberService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/auto/number/")
public class AutoNumberController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private AutoNumberService autoNumberService;

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

	@RequestMapping("getNo")
	public void getNo(String name, String alias, String uninque_no, HttpServletRequest request, HttpServletResponse response) {
		String no = autoNumberService.getNo(name, alias, uninque_no);
		JSONObject json = new JSONObject();
		json.put("no", no);
		writeJsonAjaxResponse(response, json.toString());
	}

}
