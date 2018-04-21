package com.hotent.makshi.controller.finance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.model.finance.UserInfoVo;
import com.hotent.makshi.model.hr.Entry;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.makshi.service.finance.UserInfoVoService;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.service.hr.EntryService;
import com.hotent.makshi.service.hr.TransferpositionService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/makshi/finance/finance")
public class ReportFormController extends BaseController {
	@Resource
	private UserInfoVoService userInfoVoService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private SysUserService userService;
	@Resource
	private PositionService positionService;
	@Resource
	private TransferpositionService transferpositionService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private ContractBillingApplicationService contractBillingApplicationService;
	@Resource
	private EntryService entryService;
	@Resource
	private ExecutiveService executiveService;

	@RequestMapping("salaryVerifyForm")
	@Action(description = "工资核定报表")
	public ModelAndView salaryVerifyForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if (StringUtils.isEmpty(queryTime)) {
			queryTime = DateUtils.formatDateS(new Date());
		} else {
			queryTime = queryTime.trim() + "-01";
		}

		return mv.addObject("queryTime", queryTime).addObject("queryStr", queryTime.substring(0, queryTime.lastIndexOf("-")));
	}

	// 按入职日期查询
	@RequestMapping("selectByEntryDate")
	@Action(description = "按入职日期查询")
	public ModelAndView selectByEntryDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter = new QueryFilter(request, "entryItem");
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if (queryTime != null && StringUtils.isNotEmpty(queryTime.toString())) {

		} else {
			queryTime = DateUtils.formatDateS(new Date());
			filters.put("queryTime", queryTime);
		}
		PageBean pageBean = queryFilter.getPageBean();
		pageBean.setPagesize(10);
		List<UserInfoVo> list = userInfoVoService.getAll(queryFilter);
		for (UserInfoVo userInfoVo : list) {
			Long userId = userInfoVo.getUserId();
			UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
			if (userInfomation != null) {
				userInfoVo.setEducation(userInfomation.getEducation());
				userInfoVo.setMajor(userInfomation.getMajor());
				userInfoVo.setAddress(userInfomation.getAddress());
				userInfoVo.setPositional(userInfomation.getPositional());
				userInfoVo.setGraduateTime(userInfomation.getGraduate_time());
			}
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(userInfoVo.getUserId());
			Position position = positionService.getById(userInfoVo.getPosId1());
			// Job job = jobService.getById(user.getJobId());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				userInfoVo.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				userInfoVo.setJobName(position.getPosName());
			}
			List<Entry> entryList = entryService.getFininshedByUserId(userId);
			if (entryList != null && entryList.size() > 0) {
				Entry entry = entryList.get(0);
				userInfoVo.setBaseSalary(entry.getBaseSalary());
				userInfoVo.setPosSalary(entry.getPosSalary());
			}
		}
		return mv.addObject("entryList", list).addObject("year", queryTime.split("-")[0]).addObject("month", queryTime.split("-")[1]);
	}

	// 按建议转正日期查询
	@RequestMapping("selectByFormatDate")
	@Action(description = "按建议转正日期查询")
	public ModelAndView selectByRegularizationDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter = new QueryFilter(request, "formalItem");
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "formalDate");
		if (queryTime != null && StringUtils.isNotEmpty(queryTime.toString())) {

		} else {
			queryTime = DateUtils.formatDateS(new Date());
			filters.put("formalDate", queryTime);
		}
		PageBean pageBean = queryFilter.getPageBean();
		pageBean.setPagesize(10);
		List<UserInfoVo> list = userInfoVoService.getAll(queryFilter);
		for (UserInfoVo userInfoVo : list) {
			Long userId = userInfoVo.getUserId();
			UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
			if (userInfomation != null) {
				userInfoVo.setEducation(userInfomation.getEducation());
				userInfoVo.setMajor(userInfomation.getMajor());
				userInfoVo.setAddress(userInfomation.getAddress());
				userInfoVo.setPositional(userInfomation.getPositional());
				userInfoVo.setGraduateTime(userInfomation.getGraduate_time());
			}
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(userInfoVo.getUserId());
			Position position = positionService.getById(userInfoVo.getPosId1());
			// Job job = jobService.getById(user.getJobId());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				userInfoVo.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				userInfoVo.setJobName(position.getPosName());
			}
		}
		return mv.addObject("formalList", list).addObject("year", queryTime.split("-")[0]).addObject("month", queryTime.split("-")[1]);
	}

	// 按薪资调整月份查询
	@RequestMapping("selectBySalaryAdjustDate")
	@Action(description = "按薪资调整月份查询")
	public ModelAndView selectBySalaryAdjustDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter = new QueryFilter(request, "adjustItem");
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if (queryTime != null && StringUtils.isNotEmpty(queryTime.toString())) {
			filters.put("queryTime", queryTime.substring(0, queryTime.lastIndexOf("-")));
		} else {
			queryTime = DateUtils.format(new Date(), "yyyy-MM");
			filters.put("queryTime", queryTime);
		}
		PageBean pageBean = queryFilter.getPageBean();
		pageBean.setPagesize(10);
		List<UserInfoVo> list = userInfoVoService.getTransferList(queryFilter);
		for (UserInfoVo userInfoVo : list) {
			Long userId = userInfoVo.getUserId();
			UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
			if (userInfomation != null) {
				userInfoVo.setEducation(userInfomation.getEducation());
				userInfoVo.setMajor(userInfomation.getMajor());
				userInfoVo.setAddress(userInfomation.getAddress());
				userInfoVo.setPositional(userInfomation.getPositional());
				userInfoVo.setGraduateTime(userInfomation.getGraduate_time());
			}
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(userInfoVo.getUserId());
			Position position = positionService.getById(userInfoVo.getPosId1());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				userInfoVo.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				userInfoVo.setJobName(position.getPosName());
			}
		}
		return mv.addObject("adjustList", list).addObject("year", queryTime.split("-")[0]).addObject("month", queryTime.split("-")[1]);
	}

	@RequestMapping("exportExcel")
	@Action(description = "导出Excel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		QueryFilter queryFilter = new QueryFilter(request, "queryTime");
		queryFilter.setPageBean(null);
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if (queryTime != null && StringUtils.isNotEmpty(queryTime.toString())) {
			queryTime = queryTime.trim() + "-01";
			filters.put("queryTime", queryTime);
		} else {
			queryTime = DateUtils.formatDateS(new Date());
			filters.put("queryTime", queryTime);
		}
		List<String[]> rowName = new ArrayList<>();
		String[] r1 = new String[] { "序号", "员工编号", "姓名", "学历", "专业", "毕业时间", "职称", "注册持证情况", "标准工资", "基准工资", "岗位工资", "工资总额", "社保标准", "通讯费标准", "籍贯", "项目部及岗位" };
		rowName.add(r1);
		String[] r2 = new String[] { "序号", "员工编号", "姓名", "学历", "专业", "毕业时间", "职称", "注册持证情况", "基准工资", "岗位工资", "工资总额", "社保标准", "通讯费标准", "户籍", "项目部及岗位", "备注" };
		rowName.add(r2);
		String[] r3 = new String[] { "序号", "员工编号", "姓名", "学历", "原基准工资", "原岗位工资", "", "调前工资总额", "调后基准工资", "调后岗位工资", "", "调后工资总额", "", "调后通讯标准", "项目部及岗位", "备注" };
		rowName.add(r3);
		List<Object[]> dataList = new ArrayList<>();
		List<Object[]> formalList = new ArrayList<>();
		List<Object[]> adjustList = new ArrayList<>();
		List<UserInfoVo> entrylist = userInfoVoService.getAll(queryFilter);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("formalDate", queryTime);
		List<UserInfoVo> formallist = userInfoVoService.getAll(queryFilter);
		queryTime = queryTime.substring(0, queryTime.lastIndexOf("-"));
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("queryTime", queryTime);
		List<UserInfoVo> adjustlist = userInfoVoService.getTransferList(queryFilter);
		structList(entrylist);
		structList(adjustlist);
		structList(formallist);
		// 装载数据
		for (UserInfoVo userInfoVo : entrylist) {
			Object[] object = new Object[r1.length];
			object[1] = userInfoVo.getAccount();
			object[2] = userInfoVo.getFullname();
			object[3] = userInfoVo.getEducation();
			object[4] = userInfoVo.getMajor();
			object[5] = userInfoVo.getGraduateTime();
			object[6] = userInfoVo.getPositional();
			object[7] = userInfoVo.getCertificate();
			object[9] = userInfoVo.getBaseSalary();
			object[10] = userInfoVo.getPosSalary();
			object[14] = userInfoVo.getAddress();
			object[15] = userInfoVo.getJobName();
			dataList.add(object);
		}
		for (UserInfoVo userInfoVo : formallist) {
			Object[] object = new Object[r1.length];
			object[1] = userInfoVo.getAccount();
			object[2] = userInfoVo.getFullname();
			object[3] = userInfoVo.getEducation();
			object[4] = userInfoVo.getMajor();
			object[5] = userInfoVo.getGraduateTime();
			object[6] = userInfoVo.getPositional();
			object[7] = userInfoVo.getCertificate();
			object[13] = userInfoVo.getAddress();
			object[14] = userInfoVo.getJobName();
			formalList.add(object);
		}

		for (UserInfoVo userInfoVo : adjustlist) {
			Object[] object = new Object[r1.length];
			object[1] = userInfoVo.getAccount();
			object[2] = userInfoVo.getFullname();
			object[3] = userInfoVo.getEducation();
			object[14] = userInfoVo.getJobName();
			adjustList.add(object);
		}

		String base = queryTime.split("-")[0] + "年" + queryTime.split("-")[1];
		String title = base + "月份员工工资核定单";
		String[] subtitle = new String[] { base + "月份公司招聘" + entrylist.size() + "名员工，根据其学历、职称，拟定工资金额如下：", base + "月份有" + formallist.size() + "名员工转正，拟定工资金额如下：",
				base + "月份有" + adjustlist.size() + "名员工因职务任命或工作岗位变动或取得注册资质等，基准工资、岗位工资及通讯标准拟需调整：" };
		InputStream export = userInfoVoService.export(rowName, dataList, formalList, adjustList, title, subtitle);

		String filename = base + "月份员工工资核定单" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		bis = new BufferedInputStream(export);
		// 输出流
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

	private void structList(List<UserInfoVo> entrylist) {
		if (entrylist == null || entrylist.size() == 0) {
			return;
		} else {
			for (UserInfoVo userInfoVo : entrylist) {
				Long userId = userInfoVo.getUserId();
				UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
				if (userInfomation != null) {
					userInfoVo.setEducation(userInfomation.getEducation());
					userInfoVo.setMajor(userInfomation.getMajor());
					userInfoVo.setAddress(userInfomation.getAddress());
					userInfoVo.setPositional(userInfomation.getPositional());
				}
				SysOrg org = sysOrgService.getPrimaryOrgByUserId(userInfoVo.getUserId());
				Position position = positionService.getById(userInfoVo.getPosId1());
				// Job job = jobService.getById(user.getJobId());
				if (org != null && org.getOrgName() != null) {
					String orgpath = org.getOrgPathname();
					userInfoVo.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
				}
				if (position != null && position.getPosName() != null) {
					userInfoVo.setJobName(position.getPosName());
				}
				List<Entry> entryList = entryService.getFininshedByUserId(userId);
				if (entryList != null && entryList.size() > 0) {
					Entry entry = entryList.get(0);
					userInfoVo.setBaseSalary(entry.getBaseSalary());
					userInfoVo.setPosSalary(entry.getPosSalary());
				}
			}
		}
	}

	@RequestMapping("contractBillingStats")
	@Action(description = "开票统计表")
	public ModelAndView contractBillingStats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "contractBillingStats");
		String minbillsum = RequestUtil.getString(request, "minbillsum");
		String maxbillsum = RequestUtil.getString(request, "maxbillsum");
		Map<String, Object> filters = queryFilter.getFilters();
		Long chooseOrgId = (Long) request.getSession().getAttribute("chooseOrgId");
		Long orgId = chooseOrgId;
		if (orgId == null || orgId == -1L) {
			orgId = RequestUtil.getLong(request, "orgId");
			if(orgId==0){
				orgId=(long) -1;
			}
		}
		if (orgId != null && orgId != -1L) {
			List<SysOrg> org = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
			List<Long> orgIds = new ArrayList<Long>();
			orgIds.add(orgId);
			if(CollectionUtils.isNotEmpty(org)) {
				for (SysOrg e : org) {
					orgIds.add(e.getOrgId());
				}
			}
			filters.put("orgIds", orgIds);
		}
		
		if (minbillsum != null && StringUtils.isNotEmpty(minbillsum.toString())) {
			filters.put("minbillsum", minbillsum);
		}
		if (maxbillsum != null && StringUtils.isNotEmpty(maxbillsum.toString())) {
			filters.put("maxbillsum", maxbillsum);
		}
		return this.getAutoView().addObject("contractBillingStatsList", contractBillingApplicationService.getAll(queryFilter))
				.addObject("orgs", executiveService.getOrgsBySplitLevel(3)).addObject("chooseOrgId", chooseOrgId).addObject("orgId", orgId);
	}

	@RequestMapping("exportContractExcel")
	@Action(description = "开票统计表")
	public void exportContractExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		QueryFilter queryFilter = new QueryFilter(request, "contractBillingStats");
		queryFilter.setPageBean(null);
		String minbillsum = RequestUtil.getString(request, "minbillsum");
		String maxbillsum = RequestUtil.getString(request, "maxbillsum");
		Map<String, Object> filters = queryFilter.getFilters();
		Long chooseOrgId = (Long) request.getSession().getAttribute("chooseOrgId");
		Long orgId = chooseOrgId;
		if (orgId == null || orgId == -1L) {
			orgId = RequestUtil.getLong(request, "orgId");
		}
		if (orgId != null && orgId != -1L) {
			List<SysOrg> org = sysOrgService.getOrgByOrgSupId(Long.valueOf(orgId));
			List<Long> orgIds = new ArrayList<Long>();
			orgIds.add(orgId);
			if(CollectionUtils.isNotEmpty(org)) {
				for (SysOrg e : org) {
					orgIds.add(e.getOrgId());
				}
			}
			filters.put("orgIds", orgIds);
		}
		if (minbillsum != null && StringUtils.isNotEmpty(minbillsum.toString())) {
			filters.put("minbillsum", minbillsum);
		}
		if (maxbillsum != null && StringUtils.isNotEmpty(maxbillsum.toString())) {
			filters.put("maxbillsum", maxbillsum);
		}
		List<ContractBillingApplication> list = contractBillingApplicationService.getAll(queryFilter);
		List<Object[]> dataList = new ArrayList<>();
		String[] rowName = new String[] { "序号","工单号", "申请人", "申请部门", "申请日期", "紧急程度", "合同编号", "合同名称", "开票类型", "购买方名称", "货物或应税劳务、服务名称", "开票金额", "纳税人识别号", "购买方地址", "购买方电话", "购买方开户银行", "购买方账号", "销售方开户行",
				"销售方账号", "备注", "开票状态"};
		String[] subtitle = new String[3];
		subtitle[0] = RequestUtil.getString(request, "Q_beginapplication_time_DL", "");
		subtitle[1] = RequestUtil.getString(request, "Q_endapplication_time_DG", "");
		String string = RequestUtil.getString(request, "billStatus", "开票/未开票");
		if ("0".equals(string)) {
			string = "未开票";
		} else if ("1".equals(string)) {
			string = "开票";
		} else {
			string = "开票/未开票";
		}
		subtitle[2] = string;
		String title = "开票统计表";
		for (ContractBillingApplication ca : list) {
			int c=1;
			Object[] object = new Object[rowName.length];
			object[c++] = ca.getGlobalflowno();
			object[c++] = ca.getApplicant();
			object[c++] = ca.getDepartment();
			object[c++] = DateUtils.formatDateS(ca.getApplication_time());
			String urgency_level = ca.getUrgency_level();
			if ("1".equals(urgency_level)) {
				object[c] = "正常";
			} else if ("2".equals(urgency_level)) {
				object[c] = "重要";
			} else if ("3".equals(urgency_level)) {
				object[c] = "紧急";
			}
			c++;
			object[c++] = ca.getContract_num();
			object[c++] = ca.getContract_name();
			String billing_type = ca.getBilling_type();
			object[c++] = ("1".equals(billing_type)) ? "普通" : "专票";
			object[c++] = ca.getBuyerName();
			object[c++] = ca.getGoodsName();
			object[c++] = ca.getBilling_money();
			object[c++] = ca.getTaxNum();
			object[c++] = ca.getBuyerAddress();
			object[c++] = ca.getBuyerPhone();
			object[c++] = ca.getBuyerBank();
			object[c++] = ca.getBuyerAccount();
			object[c++] = ca.getSalerBank();
			object[c++] = ca.getSalerAccount();
			object[c++] = ca.getRemark();
			object[c++] = StringUtils.isEmpty(ca.getTicketId()) ? "未开票" : "已开票";
			
			dataList.add(object);
		}

		InputStream export = contractBillingApplicationService.exportContract(rowName, dataList, title, subtitle);
		String filename = "合同开票报表" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		bis = new BufferedInputStream(export);
		// 输出流
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

}
