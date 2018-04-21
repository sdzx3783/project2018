/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-21，cp创建。 
 */
package com.hotent.makshi.controller.gates;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.gates.ExecutiveAssetDetail;
import com.hotent.makshi.model.gates.ExecutiveContractinfoDetail;
import com.hotent.makshi.model.gates.ExecutivePeopleDetail;
import com.hotent.makshi.model.gates.ExecutiveQualificationsDetail;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.platform.service.system.SysOrgService;

@Controller
@RequestMapping("/makshi/gates/executive/detail")
@SuppressWarnings("unchecked")
public class ExecutiveDetailController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ExecutiveService executiveService;
	@Resource
	private SysOrgService sysOrgService;

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

	@RequestMapping("people")
	public ModelAndView people(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "sex") Integer sex,
			@RequestParam(required = false, value = "jobno") String jobno, @RequestParam(required = false, value = "name") String name,
			@RequestParam(required = false, value = "education") String education, @RequestParam(required = false, value = "posname") String posname,
			@RequestParam(required = false, value = "positional") String positional, @RequestParam(required = false, value = "posid") String posid,
			@RequestParam(required = false, value = "agesMin") String agesMin, @RequestParam(required = false, value = "agesMax") String agesMax,
			@RequestParam(required = false, value = "entrydateStart") String entrydateStart, @RequestParam(required = false, value = "entrydateEnd") String entrydateEnd,
			@RequestParam(required = false, value = "school") String school, @RequestParam(required = false, value = "positionalMajor") String positionalMajor,
			@RequestParam(required = false, value = "querytype") String querytype, @RequestParam(required = false, value = "querygo") String querygo, HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("makshi/gates/executiveDetailPeopleMain.jsp").addObject("orgId", orgId).addObject("sex", sex).addObject("jobno", jobno).addObject("name", name)
				.addObject("education", education).addObject("positional", positional).addObject("posid", posid).addObject("agesMin", agesMin).addObject("agesMax", agesMax)
				.addObject("entrydateStart", entrydateStart).addObject("entrydateEnd", entrydateEnd).addObject("school", school).addObject("positionalMajor", positionalMajor)
				.addObject("querytype", querytype).addObject("querygo", querygo).addObject("posname", posname);
	}

	@RequestMapping("peopleTotal")
	public void peopleTotal(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "sex") Integer sex,
			@RequestParam(required = false, value = "jobno") String jobno, @RequestParam(required = false, value = "name") String name,
			@RequestParam(required = false, value = "education") String education, @RequestParam(required = false, value = "posname") String posname,
			@RequestParam(required = false, value = "positional") String positional, @RequestParam(required = false, value = "posid") String posid,
			@RequestParam(required = false, value = "agesMin") String agesMin, @RequestParam(required = false, value = "agesMax") String agesMax,
			@RequestParam(required = false, value = "entrydateStart") String entrydateStart, @RequestParam(required = false, value = "entrydateEnd") String entrydateEnd,
			@RequestParam(required = false, value = "school") String school, @RequestParam(required = false, value = "positionalMajor") String positionalMajor,
			@RequestParam(required = false, value = "querytype") String querytype, @RequestParam(required = false, value = "querygo") String querygo, HttpServletRequest request,
			HttpServletResponse response) {

		int total = executiveService.getDepartmentDetailPeopleCount(orgId, sex, jobno, name, education, posname, positional, posid, agesMin, agesMax, entrydateStart, entrydateEnd,
				school, positionalMajor, querygo);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("peoplePage")
	public ModelAndView peoplePage(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "sex") Integer sex,
			@RequestParam(required = false, value = "jobno") String jobno, @RequestParam(required = false, value = "name") String name,
			@RequestParam(required = false, value = "education") String education, @RequestParam(required = false, value = "posname") String posname,
			@RequestParam(required = false, value = "positional") String positional, @RequestParam(required = false, value = "posid") String posid,
			@RequestParam(required = false, value = "agesMin") String agesMin, @RequestParam(required = false, value = "agesMax") String agesMax,
			@RequestParam(required = false, value = "entrydateStart") String entrydateStart, @RequestParam(required = false, value = "entrydateEnd") String entrydateEnd,
			@RequestParam(required = false, value = "school") String school, @RequestParam(required = false, value = "positionalMajor") String positionalMajor,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(required = false, value = "querytype") String querytype,
			@RequestParam(required = false, value = "querygo") String querygo, HttpServletRequest request, HttpServletResponse response) {
		List<ExecutivePeopleDetail> peoples = executiveService.getDepartmentDetailPeoplePage(orgId, sex, jobno, name, education, posname, positional, posid, agesMin, agesMax,
				entrydateStart, entrydateEnd, school, positionalMajor, querygo, page, pageSize);
		return new ModelAndView("makshi/gates/executiveDetailPeoplePage.jsp").addObject("list", peoples).addObject("querytype", querytype).addObject("page", page)
				.addObject("pageSize", pageSize);
	}

	// =================合同签订明细表==============================
	@RequestMapping("contract")
	public ModelAndView contract(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "contracttype") String contracttype,
			@RequestParam(required = false, value = "contracttype1") String contracttype1, @RequestParam(required = false, value = "contract_num") String contract_num,
			@RequestParam(required = false, value = "contract_name") String contract_name, @RequestParam(required = false, value = "contract_status") String contract_status,
			@RequestParam(required = false, value = "first_party") String first_party, @RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("makshi/gates/executiveDetailContractMain.jsp").addObject("contracttype", contracttype).addObject("contracttype1", contracttype1)
				.addObject("contract_num", contract_num).addObject("contract_name", contract_name).addObject("contract_status", contract_status)
				.addObject("first_party", first_party).addObject("orgId", orgId).addObject("project_department", project_department)
				.addObject("contract_handler", contract_handler).addObject("contract_handler", contract_handler).addObject("singingStart", singingStart)
				.addObject("singingEnd", singingEnd).addObject("moneyMin", moneyMin).addObject("moneyMax", moneyMax).addObject("parts", executiveService.getOrgsBySplitLevel(3));
	}

	@RequestMapping("contractTotal")
	public void contractTotal(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "contracttype") String contracttype,
			@RequestParam(required = false, value = "contracttype1") String contracttype1, @RequestParam(required = false, value = "contract_num") String contract_num,
			@RequestParam(required = false, value = "contract_name") String contract_name, @RequestParam(required = false, value = "contract_status") String contract_status,
			@RequestParam(required = false, value = "first_party") String first_party, @RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, HttpServletRequest request, HttpServletResponse response) {
		int total = executiveService.getDetailContractCount(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party, project_department,
				contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("contractPage")
	public ModelAndView contractPage(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "contracttype") String contracttype,
			@RequestParam(required = false, value = "contracttype1") String contracttype1, @RequestParam(required = false, value = "contract_num") String contract_num,
			@RequestParam(required = false, value = "contract_name") String contract_name, @RequestParam(required = false, value = "contract_status") String contract_status,
			@RequestParam(required = false, value = "first_party") String first_party, @RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		List<ExecutiveContractinfoDetail> peoples = executiveService.getDetailContractPage(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status,
				first_party, project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax, page, pageSize);
		return new ModelAndView("makshi/gates/executiveDetailContractPage.jsp").addObject("list", peoples).addObject("page", page).addObject("pageSize", pageSize);
	}

	// =================合同签订明细表==============================

	// =================合同收款明细表 start==============================
	@RequestMapping("contractBilling")
	public ModelAndView contractBilling(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "contracttype") String contracttype,
			@RequestParam(required = false, value = "contracttype1") String contracttype1, @RequestParam(required = false, value = "contract_num") String contract_num,
			@RequestParam(required = false, value = "contract_name") String contract_name, @RequestParam(required = false, value = "contract_status") String contract_status,
			@RequestParam(required = false, value = "first_party") String first_party, @RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("makshi/gates/executiveDetailContractBillingMain.jsp").addObject("contracttype", contracttype).addObject("contracttype1", contracttype1)
				.addObject("contract_num", contract_num).addObject("contract_name", contract_name).addObject("contract_status", contract_status)
				.addObject("first_party", first_party).addObject("orgId", orgId).addObject("project_department", project_department)
				.addObject("contract_handler", contract_handler).addObject("contract_handler", contract_handler).addObject("singingStart", singingStart)
				.addObject("singingEnd", singingEnd).addObject("moneyMin", moneyMin).addObject("moneyMax", moneyMax).addObject("parts", executiveService.getOrgsBySplitLevel(3));
	}

	@RequestMapping("contractBillingTotal")
	public void contractBillingTotal(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "contracttype") String contracttype,
			@RequestParam(required = false, value = "contracttype1") String contracttype1, @RequestParam(required = false, value = "contract_num") String contract_num,
			@RequestParam(required = false, value = "contract_name") String contract_name, @RequestParam(required = false, value = "contract_status") String contract_status,
			@RequestParam(required = false, value = "first_party") String first_party, @RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, HttpServletRequest request, HttpServletResponse response) {
		int total = executiveService.getDetailContractBillingCount(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party,
				project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("contractBillingPage")
	public ModelAndView contractBillingPage(@RequestParam(required = false, value = "orgId") String orgId,
			@RequestParam(required = false, value = "contracttype") String contracttype, @RequestParam(required = false, value = "contracttype1") String contracttype1,
			@RequestParam(required = false, value = "contract_num") String contract_num, @RequestParam(required = false, value = "contract_name") String contract_name,
			@RequestParam(required = false, value = "contract_status") String contract_status, @RequestParam(required = false, value = "first_party") String first_party,
			@RequestParam(required = false, value = "project_department") String project_department,
			@RequestParam(required = false, value = "contract_handler") String contract_handler, @RequestParam(required = false, value = "singingStart") String singingStart,
			@RequestParam(required = false, value = "singingEnd") String singingEnd, @RequestParam(required = false, value = "moneyMin") String moneyMin,
			@RequestParam(required = false, value = "moneyMax") String moneyMax, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		List<ExecutiveContractinfoDetail> peoples = executiveService.getDetailContractBillingPage(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status,
				first_party, project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax, page, pageSize);
		return new ModelAndView("makshi/gates/executiveDetailContractBillingPage.jsp").addObject("list", peoples).addObject("page", page).addObject("pageSize", pageSize);
	}

	// =================合同收款明细表 end==============================

	// =================资产分布详情表 start==============================
	@RequestMapping("asset")
	public ModelAndView asset(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "assetCode") String assetCode,
			@RequestParam(required = false, value = "assetId") String assetId, @RequestParam(required = false, value = "assetName") String assetName,
			@RequestParam(required = false, value = "carePerson") String carePerson, @RequestParam(required = false, value = "fixed") Boolean fixed, HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("makshi/gates/executiveDetailAssetMain.jsp").addObject("orgId", orgId).addObject("assetCode", assetCode).addObject("assetId", assetId)
				.addObject("assetName", assetName).addObject("carePerson", carePerson).addObject("fixed", fixed).addObject("parts", executiveService.getOrgsBySplitLevel(3));
	}

	@RequestMapping("assetTotal")
	public void assetTotal(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "assetCode") String assetCode,
			@RequestParam(required = false, value = "assetId") String assetId, @RequestParam(required = false, value = "assetName") String assetName,
			@RequestParam(required = false, value = "carePerson") String carePerson, HttpServletRequest request, HttpServletResponse response) {
		int total = executiveService.getAssetDetailCount(orgId, assetCode, assetId, assetName, carePerson);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("assetPage")
	public ModelAndView assetPage(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "assetCode") String assetCode,
			@RequestParam(required = false, value = "assetId") String assetId, @RequestParam(required = false, value = "assetName") String assetName,
			@RequestParam(required = false, value = "carePerson") String carePerson, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		List<ExecutiveAssetDetail> list = executiveService.getAssetDetailPage(orgId, assetCode, assetId, assetName, carePerson, page, pageSize);
		return new ModelAndView("makshi/gates/executiveDetailAssetPage.jsp").addObject("list", list).addObject("page", page).addObject("pageSize", pageSize);
	}

	// =================资产分布详情表 start==============================

	// =================公司资质明细表 start==============================
	@RequestMapping("qualifications")
	public ModelAndView qualifications(@RequestParam(required = false, value = "type") String type, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("makshi/gates/executiveDetailQualificationsMain.jsp").addObject("type", type);
	}

	@RequestMapping("qualificationsTotal")
	public void qualificationsTotal(@RequestParam(required = false, value = "type") String type, HttpServletRequest request, HttpServletResponse response) {
		int total = executiveService.getQualificationsDetailCount(type);
		JSONObject json = new JSONObject();
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("qualificationsPage")
	public ModelAndView qualificationsPage(@RequestParam(required = false, value = "type") String type,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		List<ExecutiveQualificationsDetail> list = executiveService.getQualificationsDetailPage(type, page, pageSize);
		return new ModelAndView("makshi/gates/executiveDetailQualificationsPage.jsp").addObject("list", list).addObject("page", page).addObject("pageSize", pageSize);
	}
	// =================公司资质明细表 start==============================
}
