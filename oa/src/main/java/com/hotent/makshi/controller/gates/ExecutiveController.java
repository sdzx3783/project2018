package com.hotent.makshi.controller.gates;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.gates.Executive;
import com.hotent.makshi.model.gates.ExecutiveAge;
import com.hotent.makshi.model.gates.ExecutiveAsset;
import com.hotent.makshi.model.gates.ExecutiveCertificate;
import com.hotent.makshi.model.gates.ExecutiveContract;
import com.hotent.makshi.model.gates.ExecutiveEducation;
import com.hotent.makshi.model.gates.ExecutiveHouseStastics;
import com.hotent.makshi.model.gates.ExecutiveHouseStasticsDetail;
import com.hotent.makshi.model.gates.ExecutiveOrg;
import com.hotent.makshi.model.gates.ExecutiveProfessional;
import com.hotent.makshi.model.gates.ExecutiveQualification;
import com.hotent.makshi.model.gates.ExecutiveStaffMonth;
import com.hotent.makshi.model.gates.ExecutiveStaffYear;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.utils.ArithmeticUtil;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.service.system.SysOrgService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/gates/executive")
@SuppressWarnings("unchecked")
public class ExecutiveController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(ExecutiveController.class);
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
			logger.error("错误信息",e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	@RequestMapping("contractNo")
	public void getContractNo(@RequestParam(required = true, value = "taskId") String taskId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String useno = executiveService.getContractNo(taskId);
		json.put("useno", useno);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("manager")
	public ModelAndView manager() throws Exception {
		return getAutoView();
	}

	/**
	 * 公司部门组织架构
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("organizational")
	public void organizational(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isEmpty(orgs)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		JSONArray arry = new JSONArray();
		for (ExecutiveOrg org : orgs) {
			JSONObject o = new JSONObject();
			o.put("orgId", org.getOrgId());
			o.put("orgName", org.getOrgName());
			arry.add(o);
		}
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 人员统计
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("people")
	public void people(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", true);
		List<Executive> ex = new ArrayList<Executive>();
		int total = 0, boys = 0, girls = 0;
		// 获取深水咨询公司领导
		String orgIdTop = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgIdTop = orgTop.get(0).getOrgId();
			Executive bigexe = executiveService.getDepartmentMaxPeople(Long.valueOf(orgIdTop));
			if (null != bigexe) {
				ex.add(bigexe);
				boys += bigexe.getBoy();
				girls += bigexe.getGirl();
				total += bigexe.getCount();
			}
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				Executive exe = executiveService.getDepartmentPeople(Long.valueOf(org.getOrgId()));
				if (null != exe) {
					boys += exe.getBoy();
					girls += exe.getGirl();
					total += exe.getCount();
					ex.add(exe);
				}
			}
		}
		if (CollectionUtils.isEmpty(ex)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		StringBuffer sb = new StringBuffer("<table class='my-order1 table table-striped table-bordered table-hover table-striped executive'><tr><th>部门</th><th>人数</th><th>男</th><th>女</th></tr>");
		JSONArray arry = new JSONArray();
		for (Executive e : ex) {
			JSONObject o = new JSONObject();
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			o.put("name", orgName);
			o.put("count", e.getCount());
			arry.add(o);
			int querygo = StringUtils.equals(orgIdTop, e.getOrgId()) ? 1 : 2; 
			sb.append("<tr>")
					.append("<td><a href=\"javascript:changeToTab('" + e.getOrgId() + "','people_people','" + orgName + "');\">").append(orgName).append("</a>").append("</td>")
					.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','');\">").append(e.getCount()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','1');\">").append(e.getBoy()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','0');\">").append(e.getGirl()).append("</a></td>")
			.append("</tr>");
		}
		sb.append("<tr><th>").append("合计").append("</th><th>").append(total).append("</th><th>").append(boys).append("</th><th>").append(girls).append("</th></tr>");
		sb.append("</table>");
		json.put("table", sb.toString());
		json.put("items", arry);
		json.put("boys", boys);
		json.put("girls", girls);
		json.put("total", total);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 年龄分布
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("peopleAges")
	public void peopleAges(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取深水咨询公司领导
		String orgId = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgId = orgTop.get(0).getOrgId();
		}
		List<ExecutiveAge> ex = new ArrayList<ExecutiveAge>();
		ExecutiveAge em = (ExecutiveAge) executiveService.getPeopleAgesMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				ExecutiveAge e = (ExecutiveAge) executiveService.getPeopleAgesByOrgId(String.valueOf(org.getOrgId()));
				if (null != e) {
					ex.add(e);
				}
			}
		}
		StringBuffer sb = new StringBuffer("<form class='form-inline' style='margin-bottom: 5px;' role='form'><div class='form-body'><div class='form-group' style='margin-right: 5px'>"
				+ "<label class='control-label'>年<label>&nbsp;</label>龄</label>"
				+ "<input type='text' name='agesMin'   id='agesMin'  onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"   class='form-control age' style='width: 68px !important;' />"
				+ "&lt;至&lt;="
				+ "<input type='text' name='agesMax'   id='agesMax'  onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"   class='form-control age' style='width: 67px !important;'/>"
				+ "</div><a class='oa-button oa-button--primary oa-button--blue' href='javascript:peopleBetAges();'>查询</a><span id='peopleBetAgesQuery' style='color:red;'></span></div></form><table class='my-order2 table-striped table table-bordered table-hover table-striped executive'><tr><th>部门</th><th>a&lt;=30</th><th>30&lt;a&lt;=40</th><th>40&lt;a&lt;=50</th><th>50&lt;a&lt;=60</th><th>60以上</th></tr>");
		int total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0;
		for (ExecutiveAge e : ex) {
			total1 += e.getTotals1();
			total2 += e.getTotals2();
			total3 += e.getTotals3();
			total4 += e.getTotals4();
			total5 += e.getTotals5();
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
					.append("<td><a href=\"javascript:changeToTab('" + e.getOrgId() + "','people_age','" + orgName + "');\">").append(orgName).append("</a>").append("</td>")
					.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','0','30');\">").append(e.getTotals1()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','30','40');\">").append(e.getTotals2()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','40','50');\">").append(e.getTotals3()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','50','60');\">").append(e.getTotals4()).append("</a></td>")
					.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','60','');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
		}
		sb.append("<tr><th>").append("合计").append("</th><th>").append(total1).append("</th><th>").append(total2).append("</th><th>").append(total3).append("</th><th>").append(total4)
				.append("</th><th>").append(total5).append("</th></tr>");
		sb.append("</table>");
		json.put("table", sb.toString());
		JSONArray arry = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("name", "[a]<=30");
		o.put("count", total1);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "30<[a]<=40");
		o.put("count", total2);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "40<[a]<=50");
		o.put("count", total3);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "50<[a]<=60");
		o.put("count", total4);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "60以上");
		o.put("count", total5);
		arry.add(o);
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 年龄分布查询使用的
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("peopleBetAges")
	public void peopleBetAges(@RequestParam(required = false, value = "agesMin") String agesMin, @RequestParam(required = false, value = "agesMax") String agesMax, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取深水咨询公司领导
		String orgId = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgId = orgTop.get(0).getOrgId();
		}
		List<ExecutiveAge> ex = new ArrayList<ExecutiveAge>();
		ExecutiveAge em = (ExecutiveAge) executiveService.getPeopleBetAgesMaxByOrgId(orgId, agesMin, agesMax);
		if (null != em) {
			ex.add(em);
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				ExecutiveAge e = (ExecutiveAge) executiveService.getPeopleBetAgesByOrgId(String.valueOf(org.getOrgId()), agesMin, agesMax);
				if (null != e) {
					ex.add(e);
				}
			}
		}
		String title = "", chartTitle = "";
		if (StringUtils.isNotEmpty(agesMin) && StringUtils.isNotEmpty(agesMax)) {
			title = agesMin + "&lt;a&lt;=" + agesMax;
			chartTitle = agesMin + "<[a]<=" + agesMax;
		} else if (StringUtils.isNotEmpty(agesMin)) {
			title = agesMin + "&lt;a";
			chartTitle = agesMin + "<[a]";
		} else if (StringUtils.isNotEmpty(agesMax)) {
			title = "a&lt;=" + agesMax;
			chartTitle = "[a]<=" + agesMax;
		}
		StringBuffer sb = new StringBuffer("<form class='form-inline' style='margin-bottom: 5px;' role='form'><div class='form-body'><div class='form-group' style='margin-right: 5px'>"
				+ "<label class='control-label'>年<label>&nbsp;</label>龄</label>" + "<input type='text' name='agesMin'  id='agesMin'  onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" value='"
				+ agesMin + "'  class='form-control age' style='width: 68px !important;' />" + "&lt;至&lt;="
				+ "<input type='text' name='agesMax'  id='agesMax'  onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" value='" + agesMax
				+ "'  class='form-control age' style='width: 67px !important;'/>"
				+ "</div><a class='oa-button oa-button--primary oa-button--blue' href='javascript:peopleBetAges();'>查询</a><span id='peopleBetAgesQuery' style='color:red;'></span></div></form><table class='my-order2 table table-bordered table-hover table-striped table-striped executive'><tr><th>部门</th>"
				+ "<th>" + title + "</th><th>其他</th></tr>");
		int total1 = 0, total2 = 0;
		for (ExecutiveAge e : ex) {
			total2 += e.getTotals2();
			e.setTotals1(e.getTotals() - e.getTotals2());
			total1 += e.getTotals1();
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
				.append("<td><a href=\"javascript:changeToTab('" + e.getOrgId() + "','people_age','" + orgName + "');\">").append(orgName).append("</a>").append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','"+agesMin+"','"+agesMax+"');\">").append(e.getTotals2()).append("</a></td>")
				.append("<td>").append(e.getTotals1()).append("</td>")
			.append("</tr>");
		}
		sb.append("<tr><th>").append("合计").append("</th><th>").append(total2).append("</th><th>").append(total1).append("</th></tr>");
		sb.append("</table>");
		json.put("table", sb.toString());
		JSONArray arry = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("name", chartTitle);
		o.put("count", total2);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "其他");
		o.put("count", total1);
		arry.add(o);
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 学历分布
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("peopleEducation")
	public void peopleEducation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取深水咨询公司领导
		String orgId = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgId = orgTop.get(0).getOrgId();
		}
		List<ExecutiveEducation> ex = new ArrayList<ExecutiveEducation>();
		ExecutiveEducation em = (ExecutiveEducation) executiveService.getPeopleEducationMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}

		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				ExecutiveEducation e = (ExecutiveEducation) executiveService.getPeopleEducationByOrgId(org.getOrgId() + "");
				if (null != e) {
					ex.add(e);
				}
			}
		}
		StringBuffer sb = new StringBuffer(
				"<table class='my-order2 table table-bordered table-hover table-striped table-striped executive'><tr><th>部门</th><th>研究生及以上</th><th>本科</th><th>大专</th><th>中专</th><th>其他</th></tr>");
		int total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0;
		for (ExecutiveEducation e : ex) {
			total1 += e.getTotals1();
			total2 += e.getTotals2();
			total3 += e.getTotals3();
			total4 += e.getTotals4();
			total5 += e.getTotals5();
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
			.append("<td><a href=\"javascript:changeToTab('" + e.getOrgId() + "','people_education','" + orgName + "');\">").append(orgName).append("</a>").append("</td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','硕士');\">").append(e.getTotals1()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','本科');\">").append(e.getTotals2()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','大专');\">").append(e.getTotals3()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','中专');\">").append(e.getTotals4()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','其他');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
		}
		sb.append("<tr><th>").append("合计").append("</th><th>").append(total1).append("</th><th>").append(total2).append("</th><th>").append(total3).append("</th><th>").append(total4)
				.append("</th><th>").append(total5).append("</th></tr>");
		sb.append("</table>");
		json.put("table", sb.toString());
		JSONArray arry = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("name", "研究生及以上");
		o.put("count", total1);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "本科");
		o.put("count", total2);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "大专");
		o.put("count", total3);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "中专");
		o.put("count", total4);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "其他");
		o.put("count", total5);
		arry.add(o);
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 职称分布
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("peopleProfessional")
	public void peopleProfessional(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取深水咨询公司领导
		String orgId = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgId = orgTop.get(0).getOrgId();
		}
		List<ExecutiveProfessional> ex = new ArrayList<ExecutiveProfessional>();
		ExecutiveProfessional em = (ExecutiveProfessional) executiveService.getPeopleProfessionalMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				ExecutiveProfessional e = (ExecutiveProfessional) executiveService.getPeopleProfessionalByOrgId(org.getOrgId());
				if (null != e) {
					ex.add(e);
				}
			}
		}

		StringBuffer sb = new StringBuffer(
				"<table class='my-order2 table table-bordered table-hover table-striped table-striped executive'><tr><th>部门</th><th>教高</th><th>高级</th><th>中级</th><th>初级</th><th>其他</th></tr>");
		int total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0;
		for (ExecutiveProfessional e : ex) {
			total1 += e.getTotals1();
			total2 += e.getTotals2();
			total3 += e.getTotals3();
			total4 += e.getTotals4();
			total5 += e.getTotals5();
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
				.append("<td><a href=\"javascript:changeToTab('" + e.getOrgId() + "','people_professional','" + orgName + "');\">").append(orgName).append("</a>").append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','教高');\">").append(e.getTotals1()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','高级');\">").append(e.getTotals2()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','中级');\">").append(e.getTotals3()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','初级');\">").append(e.getTotals4()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','其他');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
		}
		sb.append("<tr><th>").append("合计").append("</th><th>").append(total1).append("</th><th>").append(total2).append("</th><th>").append(total3).append("</th><th>").append(total4)
				.append("</th><th>").append(total5).append("</th></tr>");
		sb.append("</table>");
		json.put("table", sb.toString());
		JSONArray arry = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("name", "教高");
		o.put("count", total1);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "高级");
		o.put("count", total2);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "中级");
		o.put("count", total3);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "初级");
		o.put("count", total4);
		arry.add(o);
		o = new JSONObject();
		o.put("name", "其他");
		o.put("count", total5);
		arry.add(o);
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 合同签订
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("contract")
	public void contract(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", false);
		List<ExecutiveContract> list = executiveService.getContractTjByYears(null);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		// 最近五年的统计 timemoney1：2017 ;timemoney2：2016 ;....
		double timemoney1 = 0, timemoney2 = 0, timemoney3 = 0, timemoney4 = 0, timemoney5 = 0;
		JSONArray arry = new JSONArray();
		DecimalFormat numformat = new DecimalFormat("#,###.##");
		for (ExecutiveContract o : list) {
			JSONObject e = new JSONObject();
			e.put("orgid", o.getOrgId());
			e.put("orgname", o.getOrgName());
			e.put("money1", numformat.format(o.getF_contract_money1()));
			e.put("money2", numformat.format(o.getF_contract_money2()));
			e.put("money3", numformat.format(o.getF_contract_money3()));
			e.put("money4", numformat.format(o.getF_contract_money4()));
			e.put("money5", numformat.format(o.getF_contract_money5()));
			e.put("total", numformat.format(o.getF_total()));

			timemoney1 += o.getF_contract_money1();
			timemoney2 += o.getF_contract_money2();
			timemoney3 += o.getF_contract_money3();
			timemoney4 += o.getF_contract_money4();
			timemoney5 += o.getF_contract_money5();

			arry.add(e);
		}
		json.put("time1", DateUtils.getDefinedYear(0, "yyyy"));
		json.put("time2", DateUtils.getDefinedYear(-1, "yyyy"));
		json.put("time3", DateUtils.getDefinedYear(-2, "yyyy"));
		json.put("time4", DateUtils.getDefinedYear(-3, "yyyy"));
		json.put("time5", DateUtils.getDefinedYear(-4, "yyyy"));
		json.put("timemoney1", timemoney1);
		json.put("timemoney2", timemoney2);
		json.put("timemoney3", timemoney3);
		json.put("timemoney4", timemoney4);
		json.put("timemoney5", timemoney5);
		json.put("totalmoney1", numformat.format(timemoney1));
		json.put("totalmoney2", numformat.format(timemoney2));
		json.put("totalmoney3", numformat.format(timemoney3));
		json.put("totalmoney4", numformat.format(timemoney4));
		json.put("totalmoney5", numformat.format(timemoney5));
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 合同收款统计
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("contractBilling")
	public void contractBilling(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", false);
		List<ExecutiveContract> list = executiveService.getContractBillingByYears(null);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		// 最近五年的统计 timemoney1：2017 ;timemoney2：2016 ;....
		double timemoney1 = 0, timemoney2 = 0, timemoney3 = 0, timemoney4 = 0, timemoney5 = 0;
		JSONArray arry = new JSONArray();
		DecimalFormat numformat = new DecimalFormat("#,###.##");
		for (ExecutiveContract o : list) {
			JSONObject e = new JSONObject();
			e.put("orgid", o.getOrgId());
			e.put("orgname", o.getOrgName());
			e.put("money1", numformat.format(ArithmeticUtil.div(o.getF_contract_money1(), 10000, 2)));
			e.put("money2", numformat.format(ArithmeticUtil.div(o.getF_contract_money2(), 10000, 2)));
			e.put("money3", numformat.format(ArithmeticUtil.div(o.getF_contract_money3(), 10000, 2)));
			e.put("money4", numformat.format(ArithmeticUtil.div(o.getF_contract_money4(), 10000, 2)));
			e.put("money5", numformat.format(ArithmeticUtil.div(o.getF_contract_money5(), 10000, 2)));
			e.put("total", numformat.format(ArithmeticUtil.div(o.getF_total(), 10000, 2)));

			timemoney1 += ArithmeticUtil.div(o.getF_contract_money1(), 10000, 2);
			timemoney2 += ArithmeticUtil.div(o.getF_contract_money2(), 10000, 2);
			timemoney3 += ArithmeticUtil.div(o.getF_contract_money3(), 10000, 2);
			timemoney4 += ArithmeticUtil.div(o.getF_contract_money4(), 10000, 2);
			timemoney5 += ArithmeticUtil.div(o.getF_contract_money5(), 10000, 2);

			arry.add(e);
		}
		json.put("time1", DateUtils.getDefinedYear(0, "yyyy"));
		json.put("time2", DateUtils.getDefinedYear(-1, "yyyy"));
		json.put("time3", DateUtils.getDefinedYear(-2, "yyyy"));
		json.put("time4", DateUtils.getDefinedYear(-3, "yyyy"));
		json.put("time5", DateUtils.getDefinedYear(-4, "yyyy"));
		json.put("timemoney1", ArithmeticUtil.round(timemoney1, 2));
		json.put("timemoney2", ArithmeticUtil.round(timemoney2, 2));
		json.put("timemoney3", ArithmeticUtil.round(timemoney3, 2));
		json.put("timemoney4", ArithmeticUtil.round(timemoney4, 2));
		json.put("timemoney5", ArithmeticUtil.round(timemoney5, 2));
		json.put("totalmoney1", numformat.format(timemoney1));
		json.put("totalmoney2", numformat.format(timemoney2));
		json.put("totalmoney3", numformat.format(timemoney3));
		json.put("totalmoney4", numformat.format(timemoney4));
		json.put("totalmoney5", numformat.format(timemoney5));
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 资产分布情况
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("asset")
	public void asset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isEmpty(orgs)) {
			orgs = new ArrayList<ExecutiveOrg>();
		}
		// 获取深水咨询公司领导
		String orgId = null;
		List<ExecutiveOrg> orgTop = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orgTop)) {
			orgs.addAll(orgTop);
			orgId = orgTop.get(0).getOrgId();
		}
		// 获取所有的资产类型
		List<String> codes = executiveService.getAssetCodes();
		if (CollectionUtils.isEmpty(codes))
			codes = new ArrayList<>();
		codes.add("租房");
		codes.add("车辆");
		// 组装列表的每行信息
		StringBuffer lineHead = new StringBuffer("<tr><th width='20%;'><a href=\"javascript:changeToTab('[部门ID]','asset_asset','[部门]');\">[部门]</a></th>");
		for (String code : codes) {
			if (code.contains("车辆") || code.contains("打印机") || code.contains("台式") || code.contains("笔记本") || code.contains("租房"))
				lineHead.append("<th>[").append(code).append("]</th>");
		}
		lineHead.append("</tr>");

		Map<String, Integer> mmp = new HashMap<>();
		int taotalHouse = 0;
		StringBuffer sb = new StringBuffer();
		for (ExecutiveOrg org : orgs) {
			boolean flag = false;
			String line = lineHead.toString().replaceAll("th", "td");
			List<ExecutiveAsset> assets = new ArrayList<ExecutiveAsset>();
			ExecutiveAsset house = null;
			if (StringUtils.equalsIgnoreCase(org.getOrgId(), orgId)) {
				assets = executiveService.getAssetMaxByOrgId(org.getOrgId() + "");
				house = (ExecutiveAsset) executiveService.getRentHouseMaxByOrgId(org.getOrgId() + "");
			} else {
				assets = executiveService.getAssetByOrgId(org.getOrgId() + "");
				house = (ExecutiveAsset) executiveService.getRentHouseByOrgId(org.getOrgId() + "");
			}
			if (null != house && house.getAsset_total() > 0) {
				taotalHouse += house.getAsset_total();
			}
			if (CollectionUtils.isEmpty(assets)) {
				if (null != house && house.getAsset_total() > 0) {
					flag  = true;
					taotalHouse += house.getAsset_total();
					line = line.replace("[部门ID]", StringUtils.isEmpty(house.getDepartment_id()) ? "" : house.getDepartment_id())
							.replace("[部门]", StringUtils.isEmpty(house.getDepartment()) ? "" : house.getDepartment()).replace("[租房]", house.getAsset_total() + "");
					sb.append(line);
				}
				continue;
			}
			for (ExecutiveAsset as : assets) {
				if (as.getAsset_code().contains("车辆") || as.getAsset_code().contains("打印机") || as.getAsset_code().contains("台式") || as.getAsset_code().contains("笔记本")
						|| as.getAsset_code().contains("租房")) {
					flag  = true;
					if (mmp.containsKey(as.getAsset_code())) {
						int s = mmp.get(as.getAsset_code()) + as.getAsset_total();
						mmp.put(as.getAsset_code(), s);
					} else {
						mmp.put(as.getAsset_code(), as.getAsset_total());
					}
					line = line.replace("[部门ID]", StringUtils.isEmpty(as.getDepartment_id()) ? "" : as.getDepartment_id())
							.replace("[部门]", StringUtils.isEmpty(as.getDepartment()) ? "" : as.getDepartment()).replace("[租房]", (null != house ? house.getAsset_total() : 0) + "");
					
					line = line.replace("[" + as.getAsset_code() + "]",
							"<a href=\"javascript:changeToPeopleAssetTab('" + org.getOrgId() + "','" + as.getAsset_code() + "','资产分布明细表');\">" + as.getAsset_total() + "</a>");
					
				}
			}
			if(flag){
				sb.append(line);
			}
		}
		String headTable = lineHead.toString().replace("<a href=\"javascript:changeToTab('[部门ID]','asset_asset','[部门]');\">", "").replaceAll("\\[", "")
				.replaceAll("\\]", "").replace("</a>", "").replace("台式主机", "台式电脑");
		String footTotal = lineHead.toString();
		JSONArray arry = new JSONArray();
		if (!mmp.containsKey("车辆")) {
			mmp.put("车辆", 0);
		}
		for (Map.Entry<String, Integer> mp : mmp.entrySet()) {
			footTotal = footTotal.replace("[" + mp.getKey() + "]", mp.getValue() + "");
			JSONObject e = new JSONObject();
			if (mp.getKey().contains("台式主机")) {
				e.put("asset_code", "台式电脑");
			} else
				e.put("asset_code", mp.getKey());
			e.put("asset_total", mp.getValue());
			arry.add(e);
		}
		footTotal = footTotal.replace("[部门]", "合计").replace("[租房]", taotalHouse + "").replace("<a href='/makshi/gates/executive/depart/manager.ht?tab=asset_asset&orgId=[部门ID]'target='_blank'>", "")
				.replaceAll("\\[", "").replaceAll("\\]", "").replace("</a>", "");
		String finalTable = "<table class=\"table table-bordered  table-hover table-striped executive\">" + headTable + sb.toString() + footTotal + "</table>";
		json.put("table", finalTable.replaceAll("\\[.[\\u2E80-\\u9FFF]*\\]", "0").replaceAll("null", "0"));

		// 饼状图
		JSONObject e = new JSONObject();
		e.put("asset_code", "租房");
		e.put("asset_total", taotalHouse);
		arry.add(e);
		json.put("pies", arry);

		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 证书资质
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("certificate")
	public void certificate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ExecutiveCertificate> list = executiveService.getCertificateStatistics();
		JSONObject json = new JSONObject();
		json.put("result", true);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		JSONArray arry = new JSONArray();
		for (ExecutiveCertificate c : list) {
			JSONObject e = new JSONObject();
			e.put("type", c.getType());
			e.put("typeDesc", c.getTypeDesc());
			e.put("total", c.getCertificate_total());
			arry.add(e);
		}
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping({ "peopleMonthJson" })
	public void peopleMonthJson(@RequestParam(required = false, value = "orgIds") String orgIds, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "month") String month, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			year = DateUtils.getDefinedYear(0, "yyyy");
			month = DateUtils.getDefinedYear(0, "MM");
		}

		// model.addObject("year", year).addObject("month", month);

		if (month.length() == 1)
			month = "0" + month;

		String yearMonth = year + "-" + month;
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (StringUtils.isEmpty(orgIds)) {
			if (CollectionUtils.isEmpty(orgs)) {
				return;
			}
			orgIds = "";
			for (ExecutiveOrg org : orgs) {
				orgIds += "," + org.getOrgId();
			}
			orgIds = orgIds.substring(1);
		}
		List<ExecutiveStaffMonth> list = new ArrayList<ExecutiveStaffMonth>();
		ExecutiveStaffMonth to = new ExecutiveStaffMonth();
		for (String orgId : orgIds.split(",")) {
			ExecutiveStaffMonth e = (ExecutiveStaffMonth) executiveService.getStaffByMonth(orgId, yearMonth);
			list.add(e);

			to.setPerMonthTotal(e.getPerMonthTotal() + to.getPerMonthTotal());
			to.setCutMonthTotal(e.getCutMonthTotal() + to.getCutMonthTotal());
			to.setGoMonthNum(e.getGoMonthNum() + to.getGoMonthNum());
			to.setInMonthNum(e.getInMonthNum() + to.getInMonthNum());
			to.setFoMonthNum(e.getFoMonthNum() + to.getFoMonthNum());
			to.setTransaction(e.getTransaction() + to.getTransaction());
			to.setSalaryMonthNum(e.getSalaryMonthNum() + to.getSalaryMonthNum());
		}
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		for (ExecutiveStaffMonth s : list) {
			arr.add(JSONObject.fromObject(s));
		}
		to.setOrgName("合计");
		arr.add(JSONObject.fromObject(to));
		json.put("items", arr);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 员工分析报表（月度）
	 * 
	 * @param orgIds
	 * @param year
	 * @param month
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "peopleMonth", "peopleMonthLimit" })
	public ModelAndView peopleMonth(@RequestParam(required = false, value = "orgIds") String orgIds, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "month") String month, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("makshi/gates/executiveStaffMonth.jsp").addObject("orgIds", orgIds);
		if (StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			year = DateUtils.getDefinedYear(0, "yyyy");
			month = DateUtils.getDefinedYear(0, "MM");
		}
		model.addObject("year", year).addObject("month", month);
		if (month.length() == 1)
			month = "0" + month;
		// String yearMonth = year + "-" + month;
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		// if (StringUtils.isEmpty(orgIds)) {
		// if (CollectionUtils.isEmpty(orgs)) {
		// return model;
		// }
		// orgIds = "";
		// for (ExecutiveOrg org : orgs) {
		// orgIds += "," + org.getOrgId();
		// }
		// orgIds = orgIds.substring(1);
		// }
		// List<ExecutiveStaffMonth> list = new ArrayList<ExecutiveStaffMonth>();
		// ExecutiveStaffMonth to = new ExecutiveStaffMonth();
		// for (String orgId : orgIds.split(",")) {
		// ExecutiveStaffMonth e = (ExecutiveStaffMonth) executiveService.getStaffByMonth(orgId, yearMonth);
		// list.add(e);
		//
		// to.setPerMonthTotal(e.getPerMonthTotal() + to.getPerMonthTotal());
		// to.setCutMonthTotal(e.getCutMonthTotal() + to.getCutMonthTotal());
		// to.setGoMonthNum(e.getGoMonthNum() + to.getGoMonthNum());
		// to.setInMonthNum(e.getInMonthNum() + to.getInMonthNum());
		// to.setFoMonthNum(e.getFoMonthNum() + to.getFoMonthNum());
		// to.setTransaction(e.getTransaction() + to.getTransaction());
		// to.setSalaryMonthNum(e.getSalaryMonthNum() + to.getSalaryMonthNum());
		// }
		return model
				// .addObject("list", list).addObject("to", to)
				.addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy")).addObject("orgs", orgs);
	}

	@RequestMapping({ "peopleYearJson" })
	public void peopleYearJson(@RequestParam(required = false, value = "orgIds") String orgIds, @RequestParam(required = false, value = "year") String year, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtils.getDefinedYear(0, "yyyy");
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (StringUtils.isEmpty(orgIds)) {
			if (CollectionUtils.isEmpty(orgs)) {
				return;
			}
			orgIds = "";
			for (ExecutiveOrg org : orgs) {
				orgIds += "," + org.getOrgId();
			}
			orgIds = orgIds.substring(1);
		}
		List<ExecutiveStaffYear> list = new ArrayList<ExecutiveStaffYear>();
		ExecutiveStaffYear to = new ExecutiveStaffYear();
		for (String orgId : orgIds.split(",")) {
			ExecutiveStaffYear e = (ExecutiveStaffYear) executiveService.getStaffByYear(orgId, year);
			list.add(e);

			to.setPerYearTotal(e.getPerYearTotal() + to.getPerYearTotal());
			to.setCutYearTotal(e.getCutYearTotal() + to.getCutYearTotal());
			to.setGoYearNum(e.getGoYearNum() + to.getGoYearNum());
			to.setInYearNum(e.getInYearNum() + to.getInYearNum());
			to.setFoYearNum(e.getFoYearNum() + to.getFoYearNum());
			to.setTransaction(e.getTransaction() + to.getTransaction());
			to.setSalaryYearNum(e.getSalaryYearNum() + to.getSalaryYearNum());
		}
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		for (ExecutiveStaffYear s : list) {
			arr.add(JSONObject.fromObject(s));
		}
		to.setOrgName("合计");
		arr.add(JSONObject.fromObject(to));
		json.put("items", arr);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 员工分析报表（年度）
	 * 
	 * @param orgIds
	 * @param year
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "peopleYear", "peopleYearLimit" })
	public ModelAndView peopleYear(@RequestParam(required = false, value = "orgIds") String orgIds, @RequestParam(required = false, value = "year") String year, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("makshi/gates/executiveStaffYear.jsp").addObject("orgIds", orgIds);
		if (StringUtils.isEmpty(year)) {
			year = DateUtils.getDefinedYear(0, "yyyy");
		}
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		// if (StringUtils.isEmpty(orgIds)) {
		// if (CollectionUtils.isEmpty(orgs)) {
		// return model;
		// }
		// orgIds = "";
		// for (ExecutiveOrg org : orgs) {
		// orgIds += "," + org.getOrgId();
		// }
		// orgIds = orgIds.substring(1);
		// }
		// List<ExecutiveStaffYear> list = new ArrayList<ExecutiveStaffYear>();
		// ExecutiveStaffYear to = new ExecutiveStaffYear();
		// for (String orgId : orgIds.split(",")) {
		// ExecutiveStaffYear e = (ExecutiveStaffYear) executiveService.getStaffByYear(orgId, year);
		// list.add(e);
		//
		// to.setPerYearTotal(e.getPerYearTotal() + to.getPerYearTotal());
		// to.setCutYearTotal(e.getCutYearTotal() + to.getCutYearTotal());
		// to.setGoYearNum(e.getGoYearNum() + to.getGoYearNum());
		// to.setInYearNum(e.getInYearNum() + to.getInYearNum());
		// to.setFoYearNum(e.getFoYearNum() + to.getFoYearNum());
		// to.setTransaction(e.getTransaction() + to.getTransaction());
		// to.setSalaryYearNum(e.getSalaryYearNum() + to.getSalaryYearNum());
		// }
		return model
				// .addObject("list", list).addObject("to", to)
				.addObject("year", year).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy")).addObject("orgs", orgs);
	}

	/**
	 * 租房统计表
	 * 
	 * @param start
	 * @param end
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "getRentHouseStastics", "getRentHouseStasticsLimit" })
	public ModelAndView getRentHouseStastics(@RequestParam(required = false, value = "start") String start, @RequestParam(required = false, value = "end") String end, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(start))
			start = DateUtils.getDefinedYear(0, "yyyy-MM");
		if (StringUtils.isEmpty(end))
			end = DateUtils.getDefinedYear(0, "yyyy-MM");
		ModelAndView model = new ModelAndView("makshi/gates/executiveRentHouseStastics.jsp");
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isEmpty(orgs))
			return model;
		List<ExecutiveHouseStastics> es = new ArrayList<ExecutiveHouseStastics>();
		for (ExecutiveOrg org : orgs) {
			es.add(executiveService.getRentHouseStastics(org.getOrgId(), start, end));
		}
		return model.addObject("list", es).addObject("orgs", orgs).addObject("start", start).addObject("end", end);
	}

	/**
	 * 租房详情统计表
	 * 
	 * @param start
	 * @param end
	 * @param orgId
	 * @param houseId
	 * @param rentPerson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getRentHouseStasticsDetailMain")
	public ModelAndView getRentHouseStasticsDetailMain(@RequestParam(required = false, value = "start") String start, @RequestParam(required = false, value = "end") String end,
			@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "houseId") String houseId,
			@RequestParam(required = false, value = "rentPerson") String rentPerson, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(start))
			start = DateUtils.getDefinedYear(0, "yyyy-MM");
		if (StringUtils.isEmpty(end))
			end = DateUtils.getDefinedYear(0, "yyyy-MM");
		ModelAndView model = new ModelAndView("makshi/gates/executiveRentHouseStasticsDetailMain.jsp");
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		return model.addObject("orgs", orgs).addObject("rentPerson", rentPerson).addObject("start", start).addObject("end", end).addObject("houseId", houseId).addObject("orgId", orgId);
	}

	@RequestMapping("getRentHouseStasticsDetailCount")
	public void getRentHouseStasticsDetailCount(@RequestParam(required = false, value = "start") String start, @RequestParam(required = false, value = "end") String end,
			@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "houseId") String houseId,
			@RequestParam(required = false, value = "rentPerson") String rentPerson, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("total", executiveService.getRentHouseStasticsDetailCount(orgId, start, end, houseId, rentPerson));
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("getRentHouseStasticsDetailPage")
	public ModelAndView getRentHouseStasticsDetailPage(@RequestParam(required = false, value = "start") String start, @RequestParam(required = false, value = "end") String end,
			@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "houseId") String houseId,
			@RequestParam(required = false, value = "rentPerson") String rentPerson, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(start))
			start = DateUtils.getDefinedYear(0, "yyyy-MM");
		if (StringUtils.isEmpty(end))
			end = DateUtils.getDefinedYear(0, "yyyy-MM");
		ModelAndView model = new ModelAndView("makshi/gates/executiveRentHouseStasticsDetailPage.jsp");
		List<ExecutiveHouseStasticsDetail> es = (List<ExecutiveHouseStasticsDetail>) executiveService.getRentHouseStasticsDetailPage(orgId, start, end, houseId, rentPerson, page, pageSize);
		return model.addObject("list", es);
	}

	/**
	 * 注册人员汇总表
	 * 
	 * @param other
	 * @param type
	 * @param qualification
	 * @param year
	 * @param userNo
	 * @param userName
	 * @param certificateNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("qualificationPeopleDetail")
	public ModelAndView qualificationPeopleDetail(@RequestParam(required = false, value = "other") Integer other, @RequestParam(required = false, value = "type") Integer type,
			@RequestParam(required = false, value = "qualification") String qualification, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "userNo") String userNo, @RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "certificateNo") String certificateNo, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("makshi/gates/executiveQualificationPeopleDetailMain.jsp");
		return model.addObject("qualification", qualification).addObject("year", year).addObject("userName", userName).addObject("certificateNo", certificateNo).addObject("userNo", userNo)
				.addObject("type", type).addObject("other", other).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
	}

	@RequestMapping("qualificationPeopleDetailCount")
	public void qualificationPeopleDetailCount(@RequestParam(required = false, value = "other") Integer other, @RequestParam(required = false, value = "type") Integer type,
			@RequestParam(required = false, value = "qualification") String qualification, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "userNo") String userNo, @RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "certificateNo") String certificateNo, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("total", getQualificationPeopleDetailCount(other, type, userNo, userName, certificateNo, qualification, year));
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("qualificationPeopleDetailPage")
	public ModelAndView qualificationPeopleDetailPage(@RequestParam(required = false, value = "other") Integer other, @RequestParam(required = false, value = "type") Integer type,
			@RequestParam(required = false, value = "qualification") String qualification, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "userNo") String userNo, @RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "certificateNo") String certificateNo, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("makshi/gates/executiveQualificationPeopleDetailPage.jsp");
		List<ExecutiveQualification> qualifications = getQualificationPeopleDetailPage(other, type, userNo, userName, certificateNo, qualification, year, page, pageSize);
		model.addObject("list", qualifications);
		return model.addObject("userName", userName).addObject("certificateNo", certificateNo).addObject("userNo", userNo).addObject("type", type).addObject("page", page).addObject("pageSize",
				pageSize);
	}

	private int getQualificationPeopleDetailCount(Integer other, Integer type, String userNo, String userName, String certificateNo, String qualification, String year) {
		if (null == other || other == 0) {
			switch (type) {
			case 1:
				return executiveService.getQualificationPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 2:
				return executiveService.getQualificationInitPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 3:
				return executiveService.getQualificationIntoPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 4:
				return executiveService.getQualificationOuttoPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 5:
				return 0;
			default:
				return 0;
			}
		} else {
			switch (type) {
			case 1:
				return executiveService.getQualificationPractitionersPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 2:
				return 0;
			case 3:
				return executiveService.getQualificationPractitionersIntoPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 4:
				return executiveService.getQualificationPractitionersOuttoPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			case 5:
				return executiveService.getQualificationPractitionersTrainingPeopleDetailCount(userNo, userName, certificateNo, qualification, year);
			default:
				return 0;
			}
		}
	}

	private List<ExecutiveQualification> getQualificationPeopleDetailPage(Integer other, Integer type, String userNo, String userName, String certificateNo, String qualification, String year,
			Integer page, Integer pageSize) {
		if (null == other || other == 0) {
			switch (type) {
			case 1:
				return executiveService.getQualificationPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 2:
				return executiveService.getQualificationInitPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 3:
				return executiveService.getQualificationIntoPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 4:
				return executiveService.getQualificationOuttoPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 5:
				return null;
			default:
				return null;
			}
		} else {
			switch (type) {
			case 1:
				return executiveService.getQualificationPractitionersPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 2:
				return null;
			case 3:
				return executiveService.getQualificationPractitionersIntoPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 4:
				return executiveService.getQualificationPractitionersOuttoPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			case 5:
				return executiveService.getQualificationPractitionersTrainingPeopleDetailPage(userNo, userName, certificateNo, qualification, year, page, pageSize);
			default:
				return null;
			}
		}
	}

}
