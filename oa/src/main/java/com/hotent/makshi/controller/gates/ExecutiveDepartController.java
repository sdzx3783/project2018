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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.gates.Executive;
import com.hotent.makshi.model.gates.ExecutiveAge;
import com.hotent.makshi.model.gates.ExecutiveAsset;
import com.hotent.makshi.model.gates.ExecutiveCertificate;
import com.hotent.makshi.model.gates.ExecutiveContract;
import com.hotent.makshi.model.gates.ExecutiveEducation;
import com.hotent.makshi.model.gates.ExecutiveOrg;
import com.hotent.makshi.model.gates.ExecutiveProfessional;
import com.hotent.makshi.model.gates.ExecutiveStaffMonth;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.utils.ArithmeticUtil;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;

@Controller
@RequestMapping("/makshi/gates/executive/depart")
@SuppressWarnings("unchecked")
public class ExecutiveDepartController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(ExecutiveDepartController.class);
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

	@RequestMapping({ "manager", "statistics" })
	public ModelAndView manager(@RequestParam(required = false, value = "orgId") String orgId) throws Exception {
		ISysOrg cutOrg = ContextUtil.getCurrentOrg();
		if (StringUtils.isEmpty(orgId)) {
			if (null == cutOrg) {
				List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(2);
				if (CollectionUtils.isEmpty(orgs))
					return null;
				orgId = orgs.get(0).getOrgId() + "";
			} else {
				String[] orgidArry = cutOrg.getPath().split("\\.");
				if (orgidArry.length >= 3)
					orgId = orgidArry[2];
				else
					orgId = orgidArry[1];
			}
		}
		return new ModelAndView("makshi/gates/executiveDepartManager.jsp").addObject("executiveOrgId", orgId);
	}

	@RequestMapping("people")
	public void people(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		// 获取所有的部门信息
		// List<Executive> orgIds = executiveService.getOrgsBySplitLevel(3);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		int total = 0, boys = 0, girls = 0;
		List<Executive> ex = new ArrayList<Executive>();
		Executive bigexe = executiveService.getDepartmentMaxPeople(Long.valueOf(orgId));
		if (null != bigexe) {
			ex.add(bigexe);
			boys += bigexe.getBoy();
			girls += bigexe.getGirl();
			total += bigexe.getCount();
		}
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
		if (CollectionUtils.isNotEmpty(orgIds)) {
			for (SysOrg org : orgIds) {
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
		StringBuffer sb = new StringBuffer("<table class='my-order1 table table-bordered table-hover table-striped executive'><tr><th>部门</th><th>人数</th><th>男</th><th>女</th></tr>");
		JSONArray arry = new JSONArray();
		for (Executive e : ex) {
			JSONObject o = new JSONObject();
			o.put("name", e.getOrgName());
			o.put("count", e.getCount());
			arry.add(o);
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
				.append("<td>").append(orgName).append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','');\">").append(e.getCount()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','1');\">").append(e.getBoy()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleDetailTab('" + e.getOrgId() + "','" + querygo + "','people','人员统计明细表','0');\">").append(e.getGirl()).append("</a></td>")
			.append("</tr>");
			
//			sb.append("<tr><td>").append(orgName).append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=people&querygo=").append(querygo).append("&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getCount()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=people&querygo=").append(querygo)
//					.append("&sex=1&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getBoy()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=people&querygo=").append(querygo).append("&sex=0&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getGirl()).append("</a>").append("</td></tr>");
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

	@RequestMapping("peopleAges")
	public void peopleAges(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
		List<ExecutiveAge> ex = new ArrayList<ExecutiveAge>();
		ExecutiveAge em = (ExecutiveAge) executiveService.getPeopleAgesMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}
		if (CollectionUtils.isNotEmpty(orgIds)) {
			for (SysOrg org : orgIds) {
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
				+ "</div><a class='oa-button oa-button--primary oa-button--blue' href='javascript:peopleBetAges();'>查询</a><span id='peopleBetAgesQuery' style='color:red;'></span></div></form><table class='my-order2 table table-bordered table-hover table-striped executive'><tr><th>部门</th><th>a&lt;=30</th><th>30&lt;a&lt;=40</th><th>40&lt;a&lt;=50</th><th>50&lt;a&lt;=60</th><th>60以上</th></tr>");
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
				.append("<td>").append(orgName).append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','0','30');\">").append(e.getTotals1()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','30','40');\">").append(e.getTotals2()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','40','50');\">").append(e.getTotals3()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','50','60');\">").append(e.getTotals4()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','60','');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
			
//			sb.append("<tr><td>").append(orgName).append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=").append(querygo).append("&agesMax=30&orgId=")
//					.append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals1()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=")
//					.append(querygo).append("&agesMin=30&agesMax=40&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals2()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=").append(querygo).append("&agesMin=40&agesMax=50&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals3()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=").append(querygo)
//					.append("&agesMin=50&agesMax=60&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals4()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=").append(querygo).append("&agesMin=60&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals5()).append("</a>").append("</td></tr>");
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

	@RequestMapping("peopleBetAges")
	public void peopleBetAges(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "agesMin") String agesMin,
			@RequestParam(required = false, value = "agesMax") String agesMax, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));

		List<ExecutiveAge> ex = new ArrayList<ExecutiveAge>();
		ExecutiveAge em = (ExecutiveAge) executiveService.getPeopleBetAgesMaxByOrgId(orgId, agesMin, agesMax);
		if (null != em) {
			ex.add(em);
		}
		if (CollectionUtils.isNotEmpty(orgIds)) {
			for (SysOrg org : orgIds) {
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
				+ "</div><a class='oa-button oa-button--primary oa-button--blue' href='javascript:peopleBetAges();'>查询</a><span id='peopleBetAgesQuery' style='color:red;'></span></div></form><table class='my-order2 table table-bordered table-hover table-striped executive'><tr><th>部门</th>"
				+ "<th>" + title + "</th><th>其他</th></tr>");
		int total1 = 0, total2 = 0;
		for (ExecutiveAge e : ex) {
			total2 += e.getTotals2();
			e.setTotals1(e.getTotals() - e.getTotals2());
			total1 += e.getTotals1();
			int querygo = StringUtils.equals(orgId, e.getOrgId()) ? 1 : 2;
			String orgName = StringUtils.equals(e.getOrgName(), "深水咨询") ? "公司领导" : e.getOrgName();
			sb.append("<tr>")
				.append("<td>").append(orgName).append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','0','30');\">").append(e.getTotals1()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','30','40');\">").append(e.getTotals2()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','40','50');\">").append(e.getTotals3()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','50','60');\">").append(e.getTotals4()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleAgeTab('" + e.getOrgId() + "','" + querygo + "','age','人员年龄分布明细表','60','');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
//			sb.append("<tr><td>").append(orgName).append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=age&querygo=").append(querygo).append("&agesMin=").append(agesMin)
//					.append("&agesMax=").append(agesMax).append("&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals2()).append("</a>").append("</td><td>")
//					.append(e.getTotals1()).append("</td></tr>");
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

	@RequestMapping("peopleEducation")
	public void peopleEducation(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
		List<ExecutiveEducation> ex = new ArrayList<ExecutiveEducation>();
		ExecutiveEducation em = (ExecutiveEducation) executiveService.getPeopleEducationMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}
		if (CollectionUtils.isNotEmpty(orgIds)) {
			for (SysOrg org : orgIds) {
				ExecutiveEducation e = (ExecutiveEducation) executiveService.getPeopleEducationByOrgId(org.getOrgId() + "");
				if (null != e) {
					ex.add(e);
				}
			}
		}

		StringBuffer sb = new StringBuffer(
				"<table class='my-order2 table table-bordered table-hover table-striped executive'><tr><th>部门</th><th>研究生及以上</th><th>本科</th><th>大专</th><th>中专</th><th>其他</th></tr>");
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
			.append("<td>").append(orgName).append("</td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','硕士');\">").append(e.getTotals1()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','本科');\">").append(e.getTotals2()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','大专');\">").append(e.getTotals3()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','中专');\">").append(e.getTotals4()).append("</a></td>")
			.append("<td><a href=\"javascript:changeToPeopleEducationTab('" + e.getOrgId() + "','" + querygo + "','education','人员学历分布明细表','其他');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
//			
//			sb.append("<tr><td>").append(orgName).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=education&querygo=").append(querygo)
//					.append("&education=硕士&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals1()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=education&querygo=").append(querygo).append("&education=本科&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals2()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=education&querygo=")
//					.append(querygo).append("&education=大专&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals3()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=education&querygo=").append(querygo).append("&education=中专&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals4()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=education&querygo=")
//					.append(querygo).append("&education=其他&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals5()).append("</a>").append("</td></tr>");
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

	@RequestMapping("peopleProfessional")
	public void peopleProfessional(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
		List<ExecutiveProfessional> ex = new ArrayList<ExecutiveProfessional>();
		ExecutiveProfessional em = (ExecutiveProfessional) executiveService.getPeopleProfessionalMaxByOrgId(orgId);
		if (null != em) {
			ex.add(em);
		}
		if (CollectionUtils.isNotEmpty(orgIds)) {
			for (SysOrg org : orgIds) {
				ExecutiveProfessional e = (ExecutiveProfessional) executiveService.getPeopleProfessionalByOrgId(org.getOrgId() + "");
				if (null != e) {
					ex.add(e);
				}
			}
		}

		StringBuffer sb = new StringBuffer(
				"<table class='my-order2 table table-bordered table-hover table-striped executive'><tr><th>部门</th><th>教高</th><th>高级</th><th>中级</th><th>初级</th><th>其他</th></tr>");
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
				.append("<td>").append(orgName).append("</td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','教高');\">").append(e.getTotals1()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','高级');\">").append(e.getTotals2()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','中级');\">").append(e.getTotals3()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','初级');\">").append(e.getTotals4()).append("</a></td>")
				.append("<td><a href=\"javascript:changeToPeopleProfessionalTab('" + e.getOrgId() + "','" + querygo + "','positional','人员职称分布明细表','其他');\">").append(e.getTotals5()).append("</a></td>")
			.append("</tr>");
			
//			sb.append("<tr><td>").append(orgName).append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=positional&querygo=").append(querygo).append("&positional=教高&orgId=")
//					.append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals1()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=positional&querygo=").append(querygo).append("&positional=高级&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals2()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=positional&querygo=")
//					.append(querygo).append("&positional=中级&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals3()).append("</a>")
//					.append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=positional&querygo=").append(querygo).append("&positional=初级&orgId=").append(e.getOrgId())
//					.append("' target='_blank'>").append(e.getTotals4()).append("</a>").append("</td><td><a href='/makshi/gates/executive/detail/people.ht?querytype=positional&querygo=")
//					.append(querygo).append("&positional=其他&orgId=").append(e.getOrgId()).append("' target='_blank'>").append(e.getTotals5()).append("</a>").append("</td></tr>");
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

	@RequestMapping("contract")
	public void contract(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", false);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<ExecutiveContract> list = executiveService.getContractTjByYears(orgId);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		// 最近五年的统计 timemoney1：2017 ;timemoney2：2016 ;....
		DecimalFormat numformat = new DecimalFormat("#,###.######");
		double timemoney1 = 0, timemoney2 = 0, timemoney3 = 0, timemoney4 = 0, timemoney5 = 0;
		JSONArray arry = new JSONArray();
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
	public void contractBilling(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", false);
		List<ExecutiveContract> list = executiveService.getContractBillingByYears(orgId);
		if (CollectionUtils.isEmpty(list)) {
			list.add(new ExecutiveContract());
			// writeJsonAjaxResponse(response, json.toString());
			// return;
		}
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
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

	@RequestMapping("asset")
	public void asset(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", true);
		SysOrg sysorg = sysOrgService.getById(Long.valueOf(orgId));
		if (null == sysorg) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("title", sysorg.getOrgName());
		List<SysOrg> orgIds = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
		if (CollectionUtils.isEmpty(orgIds)) {
			orgIds = new ArrayList<SysOrg>();
		}
		// 增加当前部门的数据
		orgIds.add(sysorg);

		// 获取所有的资产类型
		List<String> codes = executiveService.getAssetCodes();
		if (CollectionUtils.isEmpty(codes))
			codes = new ArrayList<>();
		codes.add("租房");
		codes.add("车辆");

		// 组装列表的每行信息
		StringBuffer lineHead = new StringBuffer("<tr><th><span data-partid='[部门ID]'>[部门]</span></th>");
		for (String code : codes) {
			if (code.contains("车辆") || code.contains("打印机") || code.contains("台式") || code.contains("笔记本") || code.contains("租房"))
				lineHead.append("<th>[").append(code).append("]</th>");
		}
		lineHead.append("</tr>");

		Map<String, Integer> mmp = new HashMap<>();
		int taotalHouse = 0;
		StringBuffer sb = new StringBuffer();
		for (SysOrg executive : orgIds) {
			String line = lineHead.toString().replaceAll("th", "td");
			List<ExecutiveAsset> assets = new ArrayList<ExecutiveAsset>();
			ExecutiveAsset house = null;
			if (executive.getOrgId().longValue() == Long.valueOf(orgId).longValue()) {
				assets = executiveService.getAssetMaxByOrgId(executive.getOrgId() + "");
				house = (ExecutiveAsset) executiveService.getRentHouseMaxByOrgId(executive.getOrgId() + "");
			} else {
				assets = executiveService.getAssetByOrgId(executive.getOrgId() + "");
				house = (ExecutiveAsset) executiveService.getRentHouseByOrgId(executive.getOrgId() + "");
			}
			if (null != house && house.getAsset_total() > 0) {
				taotalHouse += house.getAsset_total();
			}
			if (CollectionUtils.isEmpty(assets)) {
				if (null != house && house.getAsset_total() > 0) {
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
					if (mmp.containsKey(as.getAsset_code())) {
						int s = mmp.get(as.getAsset_code()) + as.getAsset_total();
						mmp.put(as.getAsset_code(), s);
					} else {
						mmp.put(as.getAsset_code(), as.getAsset_total());
					}
					line = line.replace("[部门ID]", StringUtils.isEmpty(as.getDepartment_id()) ? "" : as.getDepartment_id())
							.replace("[部门]", StringUtils.isEmpty(as.getDepartment()) ? "" : as.getDepartment()).replace("[租房]", (null != house ? house.getAsset_total() : 0) + "");
					line = line.replace("[" + as.getAsset_code() + "]", "<a href='/makshi/gates/executive/detail/asset.ht?fixed=true&orgId=" + executive.getOrgId() + "&assetCode=" + as.getAsset_code()
							+ "'  target='_blank' >" + as.getAsset_total() + "</a>");
				}
			}
			sb.append(line);
		}
		String headTable = lineHead.toString().replaceAll("\\[", "").replaceAll("\\]", "").replace("台式主机", "台式电脑");
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
		footTotal = footTotal.replace("[部门]", "合计").replace("[租房]", taotalHouse + "");
		String finalTable = "<table class=\"table table-bordered table-hover table-striped executive\">" + headTable + sb.toString() + footTotal + "</table>";
		json.put("table", finalTable.replaceAll("\\[.[\\u2E80-\\u9FFF]*\\]", "0").replaceAll("null", "0"));

		// 饼状图
		JSONObject e = new JSONObject();
		e.put("asset_code", "租房");
		e.put("asset_total", taotalHouse);
		arry.add(e);

		json.put("pies", arry);

		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("certificate")
	public void certificate(@RequestParam(required = false, value = "orgId") String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			e.put("type", c.getTypeDesc());
			e.put("total", c.getCertificate_total());
			arry.add(e);
		}
		json.put("items", arry);
		writeJsonAjaxResponse(response, json.toString());
	}

	@RequestMapping("peopleMonth")
	public ModelAndView peopleMonth(@RequestParam(required = false, value = "orgIds") String orgIds, @RequestParam(required = false, value = "year") String year,
			@RequestParam(required = false, value = "month") String month, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("makshi/gates/executiveStaffMonth.jsp").addObject("orgIds", orgIds);
		if (StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			year = DateUtils.getDefinedYear(0, "yyyy");
			month = DateUtils.getDefinedYear(0, "MM");
		}
		String yearMonth = year + "-" + month;
		// 获取所有的部门信息
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (StringUtils.isEmpty(orgIds)) {
			if (CollectionUtils.isEmpty(orgs)) {
				return model;
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
		return model.addObject("list", list).addObject("to", to).addObject("year", year).addObject("month", month).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy")).addObject("orgs", orgs);
	}
}
