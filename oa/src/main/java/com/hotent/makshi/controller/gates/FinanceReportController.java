/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-1，cp创建。 
 */
package com.hotent.makshi.controller.gates;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/gates/financereport")
public class FinanceReportController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ContractinfoService contractinfoService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private SysOrgService sysOrgService;
	private ExecutorService processServices = new ThreadPoolExecutor(0, 40, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

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

	// "http://s.holdfun.cn/ticket/files/2017121/557d792a555c4eb2a383c5c32a21a784.xls"
	@RequestMapping("analysisExcel")
	public void analysisExcel(@RequestParam(required = true, value = "fileurl") String fileurl, HttpServletRequest request, HttpServletResponse response) {
		if (!StringUtils.startsWith(fileurl, "http")) {
			fileurl = request.getSession().getServletContext().getRealPath("") + "/files/" + fileurl;
		}
		String table = analysisExcelProcess(fileurl);
		JSONObject json = new JSONObject();
		json.put("table", table);
		writeJsonAjaxResponse(response, json.toString());
	}

	private String analysisExcelProcess(String fileurl) {
		if (StringUtils.isEmpty(fileurl))
			return null;
		try {
			fileurl = URLDecoder.decode(fileurl, "utf-8");
			InputStream stream = null;
			if (!StringUtils.startsWith(fileurl, "http")) {
				stream = FileUtils.openInputStream(new File(fileurl));
			} else {
				URL url = new URL(fileurl);
				stream = url.openStream();
			}
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			// 只取第一个工作表
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			StringBuffer table = new StringBuffer("<table   class=\"table table-bordered table-hover executive financetable\">");
			// 循环行Row
			for (int rowNum = 0; rowNum <= hssfSheet.getPhysicalNumberOfRows(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				String lineCell = "";
				int cells = hssfRow.getLastCellNum();
				if (rowNum == 0) {
					HSSFCell cell = hssfRow.getCell(0);
					lineCell += "<tr><th colspan=\"" + (cells) + "\" style=\"text-align: center;\">" + getCell(cell) + "</th></tr>";
				} else {
					lineCell = "<tr>";
					for (int i = 0; i < cells; i++) {
						// 循环列Cell
						HSSFCell cell = hssfRow.getCell(i);
						if (rowNum < 4) {
							if (rowNum == 0 || rowNum == 3)
								lineCell += "<th style=\"text-align: center;\">" + getCell(cell) + "</th>";
							else
								lineCell += "<th >" + getCell(cell) + "</th>";
						} else
							lineCell += "<td>" + getCell(cell) + "</td>";
					}
					lineCell += "</tr>";
				}
				table.append(lineCell);
			}
			table.append("</table>");
			return table.toString();
		} catch (Exception e) {
			log.error("错误信息",e);
		}
		return null;
	}

	@RequestMapping("importContract")
	public void importContract(@RequestParam(required = true, value = "fileurl") String fileurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(fileurl))
				return;
			fileurl = URLDecoder.decode(fileurl, "utf-8");
			final List<String[]> noContractList = new ArrayList<String[]>();
			URL url = new URL(fileurl);
			InputStream stream = url.openStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			// 只取第一个工作表
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			List<Future<String[]>> futureList = new ArrayList<Future<String[]>>();
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getPhysicalNumberOfRows(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				final String contractNo = getCell(hssfRow.getCell(0)).replaceAll("－", "-");// 合同编号
				final String billing_date = getCell(hssfRow.getCell(1));// 开票时间
				final String invoice_money = getCell(hssfRow.getCell(2));// 发票金额（元）
				final String bearer = getCell(hssfRow.getCell(3));// 申请人
				final String payment_date = getCell(hssfRow.getCell(4));// 到账时间
				final String arrival_money = getCell(hssfRow.getCell(5));// 到账金额（元）
				Future<String[]> e = processServices.submit(new Callable<String[]>() {
					@Override
					public String[] call() throws Exception {
						Contractinfo contractinfo = contractinfoService.getSimpleinfoByNum(contractNo);
						if (null != contractinfo) {
							try {
								ContractBillingRecord r = new ContractBillingRecord();
								r.setId(UniqueIdUtil.genId());
								r.setRefId(contractinfo.getId());
								if (StringUtils.isNotEmpty(billing_date))
									r.setBilling_date(DateUtils.toDateFromStr(billing_date + " 00:00:00"));
								r.setInvoice_money(invoice_money);
								r.setBearer(bearer);
								if (StringUtils.isNotEmpty(payment_date))
									r.setPayment_date(DateUtils.toDateFromStr(payment_date + " 00:00:00"));
								r.setArrival_money(arrival_money);
								// r.setStatus("领取发票");
								r.setLinkId(0L);
								double invoice_money_doubel = 0;
								double arrival_money_doubel = 0;
								if (StringUtils.isNotEmpty(invoice_money))
									invoice_money_doubel = Double.valueOf(invoice_money);
								if (StringUtils.isNotEmpty(arrival_money))
									arrival_money_doubel = Double.valueOf(arrival_money);
								contractinfoService.addBillingRecord(r);
								contractinfoService.incrAmount(contractinfo.getId(), invoice_money_doubel, arrival_money_doubel);
								return null;
							} catch (Exception e) {
								log.error("错误信息",e);
								return new String[] { contractNo, billing_date, invoice_money, bearer, payment_date, arrival_money };
							}
						} else {
							return new String[] { contractNo, billing_date, invoice_money, bearer, payment_date, arrival_money };
						}
					}
				});
				if (null != e && null != e.get())
					futureList.add(e);
			}
			for (Future<String[]> future : futureList) {
				noContractList.add((String[]) future.get());
			}
			String fileName = "无合同收款记录导出（1204）";
			String[] titles = { "合同编号", "开票时间", "发票金额（元）", "申请人", "到账时间", "到账金额（元）" };
			// 导出没有合同的数据
			if (CollectionUtils.isNotEmpty(noContractList))
				exportRecords(fileName, titles, noContractList, request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}

	/**
	 * 导入作废合同状态信息
	 * 
	 * @param fileurl
	 * @param request
	 * @param response
	 */
	@RequestMapping("importContractStatus")
	public void importContractStatus(@RequestParam(required = true, value = "fileurl") String fileurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(fileurl))
				return;
			fileurl = URLDecoder.decode(fileurl, "utf-8");
			final List<String[]> noContractList = new ArrayList<String[]>();
			URL url = new URL(fileurl);
			InputStream stream = url.openStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			// 只取第一个工作表
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			List<Future<String[]>> futureList = new ArrayList<Future<String[]>>();
			// 循环行Row
			for (int rowNum = 2; rowNum <= hssfSheet.getPhysicalNumberOfRows(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				final String contractNo = getCell(hssfRow.getCell(0)).replaceAll("－", "-");// 合同编号
				final String contract_status = getCell(hssfRow.getCell(1));// 合同状态
				final String project_status = getCell(hssfRow.getCell(2));// 项目状态
				Future<String[]> e = processServices.submit(new Callable<String[]>() {
					@Override
					public String[] call() throws Exception {
						Contractinfo contractinfo = contractinfoService.getSimpleinfoByNum(contractNo);
						if (null != contractinfo) {
							contractinfoService.updateContractStatus(contractNo, project_status);
							return null;
						} else {
							return new String[] { contractNo, contract_status, project_status };
						}
					}
				});
				if (null != e && null != e.get())
					futureList.add(e);
			}
			for (Future<String[]> future : futureList) {
				noContractList.add((String[]) future.get());
			}
			String fileName = "无合同记录导出";
			String[] titles = { "合同编号", "合同状态", "项目状态 " };
			// 导出没有合同的数据
			if (CollectionUtils.isNotEmpty(noContractList))
				exportRecords(fileName, titles, noContractList, request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}

	/**
	 * 导入作废合同信息
	 * 
	 * @param fileurl
	 * @param request
	 * @param response
	 */
	@RequestMapping("importContractNoavail")
	public void importContractNoavail(@RequestParam(required = true, value = "fileurl") String fileurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(fileurl))
				return;
			fileurl = URLDecoder.decode(fileurl, "utf-8");
			final List<String[]> noContractList = new ArrayList<String[]>();
			URL url = new URL(fileurl);
			InputStream stream = url.openStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			// 只取第一个工作表
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			List<Future<String[]>> futureList = new ArrayList<Future<String[]>>();
			// 循环行Row
			for (int rowNum = 2; rowNum <= hssfSheet.getPhysicalNumberOfRows(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				// 合同编号 合同名称 甲方 合同金额(万元) 到账金额（万元） 合同经手人 合同归属部门 项目部 合同签批人 合同状态 合同审核人 总投资(万元)
				// 结算金额(万元) 项目负责人 项目总监/项目经理 项目状态 工程地址 项目内容 签约时间 开工时间 费率 备注
				final String contractNo = getCell(hssfRow.getCell(0)).replaceAll("－", "-").toUpperCase();
				final String contractName = getCell(hssfRow.getCell(1));
				final String jiafang = getCell(hssfRow.getCell(2));
				final String hejine = getCell(hssfRow.getCell(3));
				final String daozhangjine = getCell(hssfRow.getCell(4));
				final String htjsr = getCell(hssfRow.getCell(5));
				final String htgsbm = getCell(hssfRow.getCell(6));
				final String xmb = getCell(hssfRow.getCell(7));
				final String htqpr = getCell(hssfRow.getCell(8));
				final String htzt = getCell(hssfRow.getCell(9));
				final String htspr = getCell(hssfRow.getCell(10));
				final String ztz = getCell(hssfRow.getCell(11));
				final String jsje = getCell(hssfRow.getCell(12));
				final String xmfzr = getCell(hssfRow.getCell(13));
				final String xmzjjl = getCell(hssfRow.getCell(14));
				final String xmzt = getCell(hssfRow.getCell(15));
				final String gcdz = getCell(hssfRow.getCell(16));
				final String xmnr = getCell(hssfRow.getCell(17));
				final String qysj = getCell(hssfRow.getCell(18));
				final String kgsj = getCell(hssfRow.getCell(19));
				final String fl = getCell(hssfRow.getCell(20));
				final String bz = getCell(hssfRow.getCell(21));
				Future<String[]> e = processServices.submit(new Callable<String[]>() {
					@Override
					public String[] call() throws Exception {
						Contractinfo contractinfo = contractinfoService.getSimpleinfoByNum(contractNo);
						if (null != contractinfo) {
							return new String[] { contractNo, contractName, jiafang, hejine, daozhangjine, htjsr, htgsbm, xmb, htqpr, htzt, htspr, ztz, jsje, xmfzr, xmzjjl, xmzt, gcdz, xmnr, qysj,
									kgsj, fl, bz };
						} else {
							// 合同编号 合同名称 甲方 合同金额(万元) 到账金额（万元） 合同经手人 合同归属部门 项目部 合同签批人 合同状态 合同审核人 总投资(万元)
							// 结算金额(万元) 项目负责人 项目总监/项目经理 项目状态 工程地址 项目内容 签约时间 开工时间 费率 备注

							// 合同编号 合同名称 甲方 合同金额(万元) 到账金额（万元） 合同经手人 合同归属部门 项目部 合同签批人
							Contractinfo f = new Contractinfo();
							f.setId(UniqueIdUtil.genId());
							f.setContract_num(contractNo);
							f.setContract_name(contractName);
							f.setFirst_party(jiafang);
							f.setContract_money(StringUtils.isEmpty(hejine) ? null : Double.valueOf(hejine));
							f.setArrival_amount(StringUtils.isEmpty(daozhangjine) ? null : Double.valueOf(daozhangjine));

							f.setContract_handler(htjsr);
							if (StringUtils.isNotEmpty(htjsr)) {
								List<SysUser> us = sysUserService.getByUserName(htjsr);
								if (CollectionUtils.isNotEmpty(us))
									f.setContract_handlerID(us.get(0).getUserId() + "");
							}

							f.setContract_department(htgsbm);
							if (StringUtils.isNotEmpty(htqpr)) {
								List<SysOrg> orgs = sysOrgService.getByOrgName(htgsbm);
								if (CollectionUtils.isNotEmpty(orgs))
									f.setContract_departmentID(orgs.get(0).getOrgId() + "");
							}

							f.setProject_department(xmb);

							f.setContract_authorized_person(htqpr);
							if (StringUtils.isNotEmpty(htqpr)) {
								List<SysUser> us = sysUserService.getByUserName(htqpr);
								if (CollectionUtils.isNotEmpty(us))
									f.setContract_authorized_personID(us.get(0).getUserId() + "");
							}

							// 合同状态 合同审核人 总投资(万元)
							// 结算金额(万元) 项目负责人 项目总监/项目经理 项目状态 工程地址 项目内容 签约时间 开工时间 费率 备注
							f.setContract_status(StringUtils.equals(htzt, "作废") ? "1" : "0");
							f.setContract_reviewer(htspr);
							f.setTotal_investment(StringUtils.isEmpty(ztz) ? null : Double.valueOf(ztz));
							f.setSettlement_money(StringUtils.isEmpty(jsje) ? null : Double.valueOf(jsje));

							f.setProject_leader(xmfzr);
							f.setProject_director(xmzjjl);
							f.setProject_status(xmzt);
							f.setProject_place(gcdz);
							f.setProject_content(xmnr);

							f.setSinging_time(StringUtils.isEmpty(qysj) ? null : DateUtils.toDateFromStr(qysj + " 00:00:00"));
							f.setStart_time(StringUtils.isEmpty(kgsj) ? null : DateUtils.toDateFromStr(kgsj + " 00:00:00"));
							f.setRate(fl);
							f.setRemark(bz);
							contractinfoService.add(f);
							return null;
						}
					}
				});
				if (null != e && null != e.get())
					futureList.add(e);
			}
			for (Future<String[]> future : futureList) {
				noContractList.add((String[]) future.get());
			}
			String fileName = "作废合同已存在导出";
			String[] titles = { "合同编号", "合同名称", "甲方", "合同金额(万元)", "到账金额（万元）", "合同经手人", "合同归属部门", "项目部", "合同签批人", "合同状态", "合同审核人", "总投资(万元)", "结算金额(万元)", "项目负责人", "项目总监/项目经理", "项目状态", "工程地址", "项目内容",
					"签约时间", "开工时间", "费率", "备注 " };
			// 导出没有合同的数据
			if (CollectionUtils.isNotEmpty(noContractList))
				exportRecords(fileName, titles, noContractList, request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}

	/**
	 * 导入人员信息
	 * 
	 * @param fileurl
	 * @param request
	 * @param response
	 */
	@RequestMapping("importUserinfos")
	public void importUserinfos(@RequestParam(required = true, value = "fileurl") String fileurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(fileurl))
				return;
			fileurl = URLDecoder.decode(fileurl, "utf-8");
			final List<String[]> noList = new ArrayList<String[]>();
			URL url = new URL(fileurl);
			InputStream stream = url.openStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			// 只取第一个工作表
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			List<Future<String[]>> futureList = new ArrayList<Future<String[]>>();
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getPhysicalNumberOfRows(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				// 员工编号、 姓名、 曾用名 、性别 、婚姻状况 、民族、 籍贯、 文化程度、 政治面貌
				final String ACCOUNT = getCell(hssfRow.getCell(0)).replaceAll("－", "-").toUpperCase();
				final String FULLNAME = getCell(hssfRow.getCell(1));
				final String used_name = getCell(hssfRow.getCell(2));// F_used_name
				final String SEX = getCell(hssfRow.getCell(3));// 1=男 0=女
				final String marriage_state = getCell(hssfRow.getCell(4));
				final String nation = getCell(hssfRow.getCell(5));
				final String address = getCell(hssfRow.getCell(6));
				final String education = getCell(hssfRow.getCell(7));
				final String political_status = getCell(hssfRow.getCell(8));
				// 毕业时间 、 毕业院校、 毕业专业、 职称、 职称专业、 邮箱地址 、QQ号码 、微 信 、手 机 、手机短号
				final String graduate_time = getCell(hssfRow.getCell(9));
				final String graduate_school = getCell(hssfRow.getCell(10));
				final String major = getCell(hssfRow.getCell(11));
				final String positional = getCell(hssfRow.getCell(12));
				final String positional_major = getCell(hssfRow.getCell(13));
				final String EMAIL = getCell(hssfRow.getCell(14));
				final String qq = getCell(hssfRow.getCell(15));
				final String wechart = getCell(hssfRow.getCell(16));
				final String MOBILE = getCell(hssfRow.getCell(17));
				final String sjdh = getCell(hssfRow.getCell(18));
				// 办公电话 、 户籍类型 、户籍所在地 、通讯地址、 社会保险电脑号、 是否有传染病史、 是否有遗传病史、 特长爱好、 配偶姓名、 配偶身份证号码、 配偶居住地 、父母居住地 、紧急联系人、 紧急联系人电话 、工资卡号 、工行卡号
				final String PHONE = getCell(hssfRow.getCell(19));
				final String address_type = getCell(hssfRow.getCell(20));
				final String home_address = getCell(hssfRow.getCell(21));
				final String link_address = getCell(hssfRow.getCell(22));
				final String social_security_num = getCell(hssfRow.getCell(23));
				final String infection_history = getCell(hssfRow.getCell(24));
				final String disorders_history = getCell(hssfRow.getCell(25));
				final String hobby = getCell(hssfRow.getCell(26));
				final String spouse_name = getCell(hssfRow.getCell(27));
				final String spouse_identification_id = getCell(hssfRow.getCell(28));
				final String spouse_address = getCell(hssfRow.getCell(29));
				final String parents = getCell(hssfRow.getCell(30));
				final String emergency_link_person = getCell(hssfRow.getCell(31));
				final String emergency_link_person_phone = getCell(hssfRow.getCell(32));
				final String ICBC_id = getCell(hssfRow.getCell(33));
				final String BOC_id = getCell(hssfRow.getCell(34));
				Future<String[]> e = processServices.submit(new Callable<String[]>() {
					@Override
					public String[] call() throws Exception {
						SysUser user = sysUserService.getByAccount(ACCOUNT);
						if (user == null) {
							return new String[] { ACCOUNT, FULLNAME, used_name, SEX, marriage_state, nation, address, education, political_status, graduate_time, graduate_school, major, positional,
									positional_major, EMAIL, qq, wechart, MOBILE, sjdh, PHONE, address_type, home_address, link_address, social_security_num, infection_history, disorders_history,
									hobby, spouse_name, spouse_identification_id, spouse_address, parents, emergency_link_person, emergency_link_person_phone, ICBC_id, BOC_id };
						} else {
							user.setAccount(ACCOUNT);
							user.setFullname(FULLNAME);
							user.setEmail(EMAIL);
							user.setMobile(MOBILE);
							user.setSex(SEX == "男" ? "1" : "0");
							user.setPhone(PHONE);
							////
							sysUserService.update(user);

							UserInfomation info = userInfomationService.getUserInfomationByUid(user.getUserId());
							if (null == info) {
								info = new UserInfomation();
								info.setId(UniqueIdUtil.genId());
							}
							info.setUserId(user.getUserId());
							info.setAccount(ACCOUNT);
							info.setUsed_name(used_name);
							info.setMarriage_state(marriage_state);
							info.setNation(nation);
							info.setAddress(address);
							info.setEducation(education);
							info.setPolitical_status(political_status);
							info.setGraduate_time(converTime(graduate_time));
							info.setGraduate_school(graduate_school);
							info.setMajor(major);
							info.setPositional(positional);
							info.setPositional_major(positional_major);
							info.setQQ(qq);
							info.setWechart(wechart);
							info.setSjdh(sjdh);
							info.setAddress_type(address_type);
							info.setHome_address(home_address);
							info.setLink_address(link_address);
							info.setSocial_security_num(social_security_num);
							info.setInfection_history(infection_history == "是" ? "1" : "0");
							info.setDisorders_history(disorders_history == "是" ? "1" : "0");
							info.setHobby(hobby);
							info.setSpouse_name(spouse_name);
							info.setSpouse_identification_id(spouse_identification_id);
							info.setSpouse_address(spouse_address);
							info.setParents(parents);
							info.setEmergency_link_person(emergency_link_person);
							info.setEmergency_link_person_phone(emergency_link_person_phone);
							info.setICBC_id(ICBC_id);
							info.setBOC_id(BOC_id);
							userInfomationService.update(info);
							return null;
						}
					}
				});
				if (null != e && null != e.get())
					futureList.add(e);
			}
			for (Future<String[]> future : futureList) {
				noList.add((String[]) future.get());
			}
			String fileName = "没有对应的用户信息";
			String[] titles = { "员工编号", "姓名", "曾用名", "性别", "婚姻状况", "民族", "籍贯", "文化程度", "政治面貌", "毕业时间", "毕业院校", "毕业专业", "职称", "职称专业", "邮箱地址", "QQ号码", "微 信", "手 机", "手机短号", "办公电话", "户籍类型", "户籍所在地",
					"通讯地址", "社会保险电脑号", "是否有传染病史", "是否有遗传病史", "特长爱好", "配偶姓名", "配偶身份证号码", "配偶居住地", "父母居住地", "紧急联系人", "紧急联系人电话", "工资卡号", "工行卡号" };
			// 导出没有合同的数据
			if (CollectionUtils.isNotEmpty(noList))
				exportRecords(fileName, titles, noList, request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}

	private Date converTime(String value) {
		if (StringUtils.isEmpty(value))
			return null;
		String[] times = value.split("-");
		String year = times[0];
		String month = null;
		if (times.length > 1)
			month = times[1];

		String day = null;
		if (times.length > 2)
			day = times[2];

		if (StringUtils.isEmpty(month))
			month = "01";
		else if (month.length() < 2)
			month = "0" + month;

		if (StringUtils.isEmpty(day))
			day = "01";
		else if (day.length() < 2)
			day = "0" + month;
		return DateUtils.parse(year + "-" + month + "-" + day + " 00:00:00");
	}

	private void exportRecords(String fileName, String[] titles, List<String[]> noContractList, HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 偶数行单元格风格
			HSSFCellStyle evencellStyle = createCellStyle(workbook, new HSSFColor.LIGHT_GREEN().getIndex());
			// 奇数行单元格风格
			HSSFCellStyle oddcellStyle = createCellStyle(workbook, new HSSFColor.WHITE().getIndex());
			// 行头单元格风格
			HSSFCellStyle rowtitlecellStyle = createCellStyle(workbook, new HSSFColor.YELLOW().getIndex());
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight((short) 9);
			rowtitlecellStyle.setFont(titleFont);
			// 初始化表
			HSSFSheet sheet = workbook.createSheet(fileName);
			initSheetColumns(sheet, titles.length);
			// 初始化标题
			// 初始化表格的列头内容
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 450);
			initSheetColumnsHeads(row, rowtitlecellStyle, titles);

			// 将数据输出到表格中
			addRecordToExcel(noContractList, row, 0, sheet, evencellStyle, oddcellStyle);

			// 写入字节输出流对象中
			workbook.write(byteArrayOut);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			// 执行导出
			exportExcel(fileName, out.toByteArray(), request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}

	}

	private void initSheetColumns(HSSFSheet sheet, int size) {
		int index = 0;
		for (int i = 0; i < size; i++) {
			sheet.setColumnWidth(index++, (40 * 120));
		}
		sheet.setPrintGridlines(true);// 打印网格线
		sheet.setAutobreaks(true);
	}

	private void initSheetColumnsHeads(HSSFRow row, HSSFCellStyle rowtitlecellStyle, String[] titles) {
		int index = 0;
		for (String title : titles) {
			createCell(row, index++, title, rowtitlecellStyle);
		}
	}

	private int addRecordToExcel(List<String[]> noContractList, HSSFRow row, int rowIndex, HSSFSheet sheet, HSSFCellStyle evencellStyle, HSSFCellStyle oddcellStyle) {
		// 用来存放奇偶数行的临时对象
		HSSFCellStyle tempcellStyle = null;
		for (String[] d : noContractList) {
			row = sheet.createRow(++rowIndex);
			row.setHeight((short) 450);
			if (rowIndex % 2 == 0)
				tempcellStyle = oddcellStyle;
			else
				tempcellStyle = evencellStyle;
			int index = 0;
			for (String val : d) {
				createCell(row, index++, val, tempcellStyle);
			}
		}
		return rowIndex;
	}

	/**
	 * 流方式导出数据到excel表格中，并区分对待火狐、IE、Chrome三种浏览器对中文文件名的处理
	 * 
	 * @param fileName
	 * @param datas
	 * @throws Exception
	 */
	private void exportExcel(String fileName, byte[] datas, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String agent = request.getHeader("USER-AGENT");
		if (agent != null && agent.indexOf("Firefox") >= 0) {
			// FF
		} else {
			// IE or Chrome
			fileName = URLEncoder.encode(fileName, "UTF-8");// 兼容IE6，此处设置需IE选项中urf-8选项支持
			fileName = fileName.replaceAll("\\+", "%20"); // 解决编码后空格变+号的情况
		}

		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");// attachment
		response.setContentLength(datas.length);
		ServletOutputStream ouputStream = response.getOutputStream();

		ouputStream.write(datas, 0, datas.length);
		ouputStream.flush();
		ouputStream.close();
	}

	private String getCell(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}

	/**
	 * 创建单元格表格样式
	 * 
	 * @param workbook
	 * @param isWrapText
	 * @param alignment
	 * @param valignment
	 * @param bBorder
	 * @param lBorder
	 * @param rBorder
	 * @param tBorder
	 * @param fillPattern
	 * @param color
	 * @return
	 */
	private HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short color) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(color);
		return cellStyle;
	}

	/**
	 * 向表格中添加单元格
	 * 
	 * @param row ：需要添加单元格的行
	 * @param cellnum ：在第几行添加单元格
	 * @param value ：单元格的值
	 * @param cellStyle ：单元格的样式
	 */
	private void createCell(HSSFRow row, int cellnum, String value, HSSFCellStyle cellStyle) {
		if (value == null || value.equals("null")) {
			value = " ";
		}
		HSSFCell cell = row.createCell(cellnum);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

}
