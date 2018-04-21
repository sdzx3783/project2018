
package com.hotent.makshi.controller.contract;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.ContractChangeApplication;
import com.hotent.makshi.model.contract.ContractCustomers;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumAndMoney;
import com.hotent.makshi.model.contract.ContractNumAndMoneyTotal;
import com.hotent.makshi.model.contract.ContractNumSecond;
import com.hotent.makshi.model.contract.ContractNumYear;
import com.hotent.makshi.model.contract.ContractOutput;
import com.hotent.makshi.model.contract.ContractPaymentRecord;
import com.hotent.makshi.model.contract.ContractSingingRate;
import com.hotent.makshi.model.contract.ContractStatistics;
import com.hotent.makshi.model.contract.ContractSupplier;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.contract.Contractnum;
import com.hotent.makshi.model.contract.MonthlyCollection;
import com.hotent.makshi.model.gates.ExecutiveOrg;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.contract.ContractChangeApplicationService;
import com.hotent.makshi.service.contract.ContractCustomersService;
import com.hotent.makshi.service.contract.ContractNumAdminService;
import com.hotent.makshi.service.contract.ContractNumAndMoneyService;
import com.hotent.makshi.service.contract.ContractNumYearService;
import com.hotent.makshi.service.contract.ContractOutputService;
import com.hotent.makshi.service.contract.ContractSealApplicationService;
import com.hotent.makshi.service.contract.ContractSingingRateService;
import com.hotent.makshi.service.contract.ContractStatisticsService;
import com.hotent.makshi.service.contract.ContractSupplierService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.contract.ContractnumService;
import com.hotent.makshi.service.contract.MonthlyCollectionService;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:合同基本信息 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/contractinfo/")
public class ContractinfoController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ContractinfoService contractinfoService;
	@Resource
	private IdentityService identityService;
	@Resource
	private ContractNumAdminService contractNumAdminService;
	@Resource
	private MonthlyCollectionService monthlyCollectionService;
	@Resource
	private ContractSingingRateService contractSingingRateService;
	@Resource
	private ContractSupplierService contractSupplierService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private ContractStatisticsService contractStatisticsService;
	@Resource
	private ContractCustomersService contractCustomersService;
	@Resource
	private ContractOutputService contractOutputService;
	@Resource
	private ContractNumAndMoneyService contractNumAndMoneyService;
	@Resource
	private ContractSealApplicationService contractSealApplicationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	@Resource
	private ContractnumService contractnumService;
	@Resource
	private ExecutiveService executiveService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private ContractChangeApplicationService contractChangeApplicationService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ContractNumYearService contractNumYearService;

	/**
	 * 添加或更新合同基本信息。
	 * 
	 * @param request
	 * @param response
	 * @param contractinfo 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新合同基本信息")
	public void save(HttpServletRequest request, HttpServletResponse response, Contractinfo contractinfo, ContractBillingRecord contractBillingRecord, ContractPaymentRecord contractPaymentRecord)
			throws Exception {
		String resultMsg = null;
		try {
			if (contractinfo.getId() == null) {
				// Contractinfo exist = contractinfoService.getContractinfoByNum(contractinfo.getContract_num());
				int exist = contractIsExist(contractinfo.getContract_num(), contractinfo.getContracttype());
				if (exist == -1) {
					resultMsg = getText("添加合同基本信息失败,合同编号和合同类型不能为空", "添加合同基本信息失败,合同编号和合同类型不能为空");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				} else if (exist == 1) {
					resultMsg = getText("添加合同基本信息失败,该编号已使用", "添加合同基本信息失败,该编号已使用");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				} else {
					contractinfo.setCreaterID(Long.valueOf(ContextUtil.getCurrentUserId()).toString());
					contractinfo.setCreater(ContextUtil.getCurrentUser().getFullname());
					contractinfo.setCreatetime(new Date());
					contractinfo.setContract_type("0");
					if (("采购合同").equals(contractinfo.getContracttype())) {
						contractinfo.setContract_type("1");
					}
					if (StringUtil.isNotEmpty(contractinfo.getContract_department())) {
						Long contract_departmentID = sysOrgService.getByOrgNameNotDel(contractinfo.getContract_department()).get(0).getOrgId();
						contractinfo.setContract_departmentID(contract_departmentID.toString());
					}
					contractinfoService.save(contractinfo);
					/*
					 * ContractNumAdmin contractNumAdminByType = contractNumAdminService.getContractNumAdminByType(contractinfo.getContracttype()); if(contractNumAdminByType!=null){
					 * contractNumAdminByType.setFlowNo((Integer.parseInt(contractNumAdminByType.getFlowNo())+1)+"");//流水号 contractNumAdminService.update(contractNumAdminByType); }
					 */
					resultMsg = getText("添加", "合同基本信息");
				}

			} else {
				contractinfo.setUpdatetime(new Date());
				//contractinfo.setUpdater(ContextUtil.getCurrentUserId() + "");
				List<ContractBillingRecord> list = contractinfo.getContractBillingRecordList();
				// List<ContractBillingRecord> list =contractinfoService.getContractBillingRecordList(contractinfo.getId());
				Double allInvoice = 0.0;
				Double arrival_amount = 0.0;
				for (ContractBillingRecord record : list) {
					if (record != null && record.getInvoice_money() != null && record.getInvoice_money().length() > 0) {
						allInvoice = allInvoice + Double.valueOf(record.getInvoice_money());
					}
					if (record.getArrival_money() != null && record.getArrival_money().length() > 0) {
						arrival_amount = arrival_amount + Double.valueOf(record.getArrival_money());
					}
				}
				contractinfo.setInvoice_amount(allInvoice);
				contractinfo.setArrival_amount(arrival_amount);
				contractinfoService.save(contractinfo);
				resultMsg = getText("更新", "合同基本信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得合同基本信息分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看合同基本信息分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// List<Contractinfo> list=contractinfoService.getAll(new QueryFilter(request,"contractinfoItem"));

		// ModelAndView mv=this.getAutoView().addObject("contractinfoList",list);

		Long orgId = (Long) request.getSession().getAttribute("chooseOrgId");
		boolean isEditor = false;
		boolean readonly = false;
		HttpSession session = request.getSession();
		if (orgId != null && orgId != -1L) {
			SysOrg sysOrg = sysOrgService.getById(orgId);
			session.setAttribute("orgName", sysOrg.getOrgName());
		} else {
			isEditor = true;
			session.removeAttribute("orgName");
			session.removeAttribute("chooseOrgId");
		}
		Long userId = ContextUtil.getCurrentUserId();
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		// 是否包含公司合同查看角色
		for (SysRole sysRole : roleList) {
			if (("公司合同查看角色").equals(sysRole.getRoleName())) {
				readonly = true;
			}
		}
		return this.getAutoView().addObject("isEditor", isEditor).addObject("readonly", readonly);
	}

	/**
	 * 删除合同基本信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除合同基本信息")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			for (Long long1 : lAryId) {
				Contractinfo contractinfo = contractinfoService.getById(long1);
				if (contractinfo != null) {
					contractnumService.addByContractnum(contractinfo.getContract_num(), contractinfo.getContracttype());
				}
			}
			contractinfoService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除合同基本信息成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑合同基本信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑合同基本信息")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Contractinfo contractinfo = contractinfoService.getById(id);
		if (contractinfo != null) {
			List<ContractBillingRecord> contractBillingRecordList = contractinfoService.getContractBillingRecordList(contractinfo.getId());
			contractinfo.setContractBillingRecordList(contractBillingRecordList);
			List<ContractPaymentRecord> contractPaymentRecordList = contractinfoService.getContractPaymentRecordList(contractinfo.getId());
			contractinfo.setContractPaymentRecordList(contractPaymentRecordList);
		}
		if (BeanUtils.isEmpty(contractinfo)) {
			contractinfo = new Contractinfo();
			String no_id = identityService.nextId("contractinfo");
			contractinfo.setNo(no_id);
		}
		List<String> contractTypes = contractinfoService.getContractType();
		ContractNumAdmin contractNum = contractNumAdminService.getContractNumAdminByType(contractinfo.getContracttype());
		List<ContractNumSecond> secondList  = new ArrayList<ContractNumSecond>();
		if(null!=contractNum){
			 secondList = contractNumAdminService.getContractNumSecondList(contractNum.getId());
			
		}
		ContractNumAdmin contractNumThrid = contractNumAdminService.getContractNumAdminByType(contractinfo.getType());
		List<ContractNumSecond> thirdList = new ArrayList<ContractNumSecond>();
		if(null!=contractNumThrid){
			thirdList = contractNumAdminService.getContractNumSecondList(contractNumThrid.getId());
		}	
		return getAutoView().addObject("contractinfo", contractinfo).addObject("returnUrl", returnUrl).addObject("orgs", executiveService.getOrgsBySplitLevel(3)).addObject("cutYear",
				DateUtils.getDefinedYear(0, "yyyy")).addObject("contractTypes", contractTypes).addObject("secondList", secondList).addObject("thirdList", thirdList);
	}

	/**
	 * 取得合同基本信息明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看合同基本信息明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Contractinfo contractinfo = contractinfoService.getById(id);
		List<ContractChangeApplication> contractChangeApplicationList = null;
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		List<Project> pros =new ArrayList<>();
		if (contractinfo != null) {
			// 合同开票记录
			List<ContractBillingRecord> contractBillingRecordList = contractinfoService.getContractBillingRecordList(contractinfo.getId());
			contractinfo.setContractBillingRecordList(contractBillingRecordList);
			if (CollectionUtils.isNotEmpty(contractBillingRecordList)) {
				for (ContractBillingRecord c : contractBillingRecordList) {
					long linkId = null == c.getLinkId() ? 0L : c.getLinkId().longValue();
					if (map.containsKey(linkId)) {
						map.put(linkId, map.get(linkId) + 1);
					} else
						map.put(linkId, 1);
				}
			}
			// 合同付款记录
			List<ContractPaymentRecord> contractPaymentRecordList = contractinfoService.getContractPaymentRecordList(contractinfo.getId());
			contractinfo.setContractPaymentRecordList(contractPaymentRecordList);
			// 合同变更记录
			contractChangeApplicationList = contractChangeApplicationService.getList(contractinfo.getContract_num());
			pros=projectService.getProsByContractnum(contractinfo.getContract_num());
		}
		return getAutoView().addObject("linkMap", map).addObject("contractinfo", contractinfo).addObject("contractChangeApplicationList", contractChangeApplicationList)
				.addObject("pros", pros);
	}

	@RequestMapping("getBaseType")
	@ResponseBody
	public Object getBaseType(HttpServletRequest request, HttpServletResponse response) {
		List<String> contractTypes = contractinfoService.getContractType();
		return contractTypes;
	}
	
	
	@RequestMapping("queryContractType")
	@Action(description = "查询合同类型")
	@ResponseBody
	public Object queryContractType(HttpServletRequest request, HttpServletResponse response) {
		String year = DateUtils.getDefinedYear(0, "yyyy");
		year = RequestUtil.getString(request, "year");
		Map<String, String> param = new HashMap<String, String>();
		param.put("year", year);
		List<ContractNumAdmin> contractTypeList = contractNumAdminService.getLoadList();
		for (ContractNumAdmin contractNumAdmin : contractTypeList) {
			Long contractId = contractNumAdmin.getId();
			ContractNumYear contractNumYear = contractNumYearService.getByRefIdAndYear(contractId, year);
			if (null != contractNumYear) {
				contractNumAdmin.setFlowNo(contractNumYear.getFlowNo());
			} else {
				contractNumAdmin.setFlowNo("1");
			}
		}
		for (ContractNumAdmin contractNum : contractTypeList) {
			List<ContractNumSecond> contractNumSecondList = contractNumAdminService.getContractNumSecondList(contractNum.getId());
			String type = contractNum.getType();
			Contractnum contracttype = contractnumService.getByContracttype(type, year);
			if (contracttype != null) {// 回收的合同编号
				contractNum.setFlowNo(contracttype.getNum());
			}
			contractNum.setContractNumSecondList(contractNumSecondList);
		}
		return contractTypeList;
	}

	
	@RequestMapping("checkContracNum")
	@Action(description = "验证合同编码")
	@ResponseBody
	public String vaildateContractNum(HttpServletRequest request, HttpServletResponse response) {
		String contractNum = request.getParameter("contractNum");
		Contractinfo contractinfo = contractinfoService.getContractinfoByNum(contractNum);
		if (contractinfo == null) {
			return "true";
		} else {
			return "false";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("monthlyCollection")
	@Action(description = "查看合同基本信息分页列表")
	public ModelAndView MonthlyCollectionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MonthlyCollection> list = new ArrayList<MonthlyCollection>();
		String year = request.getParameter("year");
		if (year == null) {
			year = DateUtils.getDefinedYear(0, "yyyy");
		}
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (ExecutiveOrg org : orgs) {
				MonthlyCollection monthlyCollection = monthlyCollectionService.getMonthlyNewCollection(org.getOrgId(), Integer.valueOf(year));
				if (null == monthlyCollection) {
					monthlyCollection = new MonthlyCollection();
				}
				monthlyCollection.setDepartment(org.getOrgName());
				list.add(monthlyCollection);
			}
		}
		return this.getAutoView().addObject("year", year).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy")).addObject("monthlyCollectionList", list);
	}

	@RequestMapping("singingRate")
	public ModelAndView ContractSingingRateList(@RequestParam(required = false, value = "years") String years, @RequestParam(required = false, value = "orgIds") String orgIds,
			@RequestParam(required = false, value = "cotps") String cotps,@RequestParam(required = false, value = "contractType") String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// if (StringUtils.isEmpty(years)) {
		// years = DateUtils.getDefinedYear(0, "yyyy");
		// }
		//判断是否只为采购合同
		List<String> cotpsList = new ArrayList<String>();
		if(StringUtil.isEmpty(cotps)){
			cotpsList = contractinfoService.getContractType();
			cotpsList.remove("采购合同");
		}
		List<ContractSingingRate> list = contractSingingRateService.getAllDepartmentContractSigningStatistics(years, orgIds, cotps , cotpsList);
		if (CollectionUtils.isNotEmpty(list)) {
			list.add(contractSingingRateService.getAllDepartmentTotalContractSigningStatistics(years, orgIds, cotps ,cotpsList));
		}
		return this.getAutoView().addObject("contractSingingRateList", list).addObject("orgs", executiveService.getOrgsBySplitLevel(3)).addObject("contyps", contractinfoService.getContractType())
				.addObject("years", years).addObject("orgIds", orgIds).addObject("cotps", cotps).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
	}

	@RequestMapping("statistics")
	public ModelAndView ContractStatisticsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String number = request.getParameter("years");
		if (number == null || ("").equals(number)) {
			number = "1";
		}
		List<ContractStatistics> list = contractStatisticsService.getAllContractStatistics(number);
		return this.getAutoView().addObject("statisticsList", list).addObject("year", number);
	}

	@RequestMapping("supplier")
	public ModelAndView SupplierList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// List<ContractSupplier> list = new ArrayList<ContractSupplier>();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		Date startDate = new Date();
		Date endDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (null != startDateStr && null != endDateStr) {
			if ((startDateStr).equals(endDate)) {
				startDate = sdf.parse(startDateStr + "-01-01 00:00:00");
				endDate = sdf.parse(startDateStr + "-12-31 23:59:59");
				endDateStr = null;
			} else {
				startDate = sdf.parse(startDateStr + "-01-01 00:00:00");
				endDate = sdf.parse(endDateStr + "-12-31 23:59:59");
			}
		}
		if (startDateStr == null && endDateStr != null) {
			startDate = sdf.parse(endDateStr + "-01-01 00:00:00");
			endDate = sdf.parse(endDateStr + "-12-31 23:59:59");
			startDateStr = endDateStr;
			endDateStr = null;
		}
		if (startDateStr != null && endDateStr == null) {
			startDate = sdf.parse(startDateStr + "-01-01 00:00:00");
			endDate = sdf.parse(startDateStr + "-12-31 23:59:59");

		}

		if (startDateStr == null && endDateStr == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			int year = c.get(Calendar.YEAR);
			startDate = sdf.parse(String.valueOf(year) + "-01-01 00:00:00");
			endDate = sdf.parse(String.valueOf(year) + "-12-31 23:59:59");
			startDateStr = Integer.valueOf(year).toString();
			endDateStr = null;
		}
		List<ContractSupplier> supplierlist = contractSupplierService.getAllSupplier(startDate, endDate);
		return this.getAutoView().addObject("supplierlist", supplierlist).addObject("syear", startDateStr).addObject("eyear", endDateStr);
	}

	@RequestMapping("customers")
	public ModelAndView CustomersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "customersItem");
		List<ContractCustomers> customerslist = new ArrayList<ContractCustomers>();
		String startYear = request.getParameter("startDate");
		String endYear = request.getParameter("endDate");
		Date startDate = new Date();
		if (startYear == null && endYear == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			int year = c.get(Calendar.YEAR);
			startYear = Integer.valueOf(year).toString();
			endYear = Integer.valueOf(year).toString();
		}
		if (startYear == null && endYear != null) {
			startYear = endYear;
		}
		if (startYear != null && endYear == null) {
			endYear = startYear;
		}
		filter.getFilters().put("startYear", Integer.valueOf(startYear));
		filter.getFilters().put("endYear", Integer.valueOf(endYear));
		customerslist = contractCustomersService.getAll(filter);
		return this.getAutoView().addObject("customerslist", customerslist).addObject("syear", startYear).addObject("eyear", endYear);
	}

	@RequestMapping("output")
	public ModelAndView OutputList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "outputItem");
		// ContractOutput total = new ContractOutput();
		List<ContractOutput> outputlist = new ArrayList<ContractOutput>();
		List<String> contractTypes = new ArrayList<String>();
		Integer countType = 1;
		Map<String, Object> params = new HashMap<String, Object>();
		String selectYear = request.getParameter("selectYear");
		String contractType = request.getParameter("contractTypes");
		if (contractType != null && contractType.trim().length() > 0) {
			String[] contractTypeTem = contractType.split(",");
			for (int i = 0; i < contractTypeTem.length; i++) {
				contractTypes.add(contractTypeTem[i]);
			}
		}
		if (null == selectYear || ("").equals(selectYear)) {
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			selectYear = Integer.valueOf(year).toString();
		}
		String department = request.getParameter("department");
		String countTypeTem = request.getParameter("countType");
		if (null == countTypeTem) {
			countTypeTem = "1";
		}
		String selectSeason = request.getParameter("selectSeason");
		String selectMonth = request.getParameter("selectMonth");
		if (Integer.valueOf(countTypeTem) == 2) {
			if (selectSeason == null || selectSeason.length() == 0) {
				selectSeason = "1";
			}
		}
		if (Integer.valueOf(countTypeTem) == 3) {
			if (selectMonth == null || selectMonth.length() == 0) {
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int month = c.get(Calendar.MONTH);
				selectMonth = Integer.valueOf(month).toString();
			}
		}
		params.put("selectMonth", selectMonth);
		filter.getFilters().put("selectMonth", selectMonth);
		params.put("contractTypes", contractTypes);
		filter.getFilters().put("contractTypes", contractTypes);
		params.put("selectYear", selectYear);
		filter.getFilters().put("selectYear", selectYear);
		if (department != null && !("").equals(department)) {
			params.put("department", department);
			filter.getFilters().put("department", department);
		}
		if (countTypeTem != null) {
			countType = Integer.valueOf(countTypeTem);
		}
		outputlist = contractOutputService.getAll(filter);
		/*
		 * Double totalMoney = 0.0; Double totalOutPut = 0.0; for(ContractOutput contractOutput:outputlist){ //totalMoney = totalMoney + contractOutput.getTotalInvestment(); if(countType==1){
		 * totalOutPut = totalOutPut + contractOutput.getYearPut(); } if(countType==2){
		 * 
		 * if(Integer.valueOf(selectSeason)==1){ totalOutPut = totalOutPut + contractOutput.getSesonOne(); } if(Integer.valueOf(selectSeason)==2){ totalOutPut = totalOutPut +
		 * contractOutput.getSesonTwo(); } if(Integer.valueOf(selectSeason)==3){ totalOutPut = totalOutPut + contractOutput.getSesonThree(); } if(Integer.valueOf(selectSeason)==4){ totalOutPut =
		 * totalOutPut + contractOutput.getSesonFour(); } } if(countType==3){ totalOutPut = totalOutPut + contractOutput.getTheOutput(); } } total.setContractType("合计");
		 * total.setTotalInvestment(totalMoney); total.setTheOutput(totalOutPut); total.setYearPut(totalOutPut); total.setSesonOne(totalOutPut); total.setSesonTwo(totalOutPut);
		 * total.setSesonThree(totalOutPut); total.setSesonFour(totalOutPut);
		 */
		// outputlist.add(total);
		return this.getAutoView().addObject("outputlist", outputlist).addObject("countType", countType).addObject("selectYear", selectYear).addObject("contractTypes", contractType)
				.addObject("selectSeason", selectSeason).addObject("selectMonth", selectMonth).addObject("department", department);
	}

	@RequestMapping("numAndMoney")
	public ModelAndView getNumAndMoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView mv = new ModelAndView("/makshi/contract/contractinfoGetNumAndMoney.jsp");
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, ContractNumAndMoneyTotal> result = new HashMap<String, ContractNumAndMoneyTotal>();
			Map<String, ContractNumAndMoneyTotal> cgResult = new HashMap<String, ContractNumAndMoneyTotal>();
			// 获取合同类型和查询的年份数
			String[] contractTypeArry = request.getParameterValues("contractType");
			if (null == contractTypeArry || contractTypeArry.length == 0) {
				List<String> typeList = contractinfoService.getContractType();
				contractTypeArry = typeList.toArray(new String[typeList.size()]);
			}
			String yearNum = request.getParameter("yearNum");
			if (null == yearNum || yearNum.length() == 0) {
				yearNum = "3";
			}
			Integer IntYear = Integer.valueOf(yearNum);
			// 获取今年
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			if (null == IntYear) {
				IntYear = 1;
			}
			// 遍历合同类型
			for (int i = 0; i < contractTypeArry.length; i++) {
				ContractNumAndMoney contractNumAndMoney = new ContractNumAndMoney();
				ContractNumAndMoneyTotal contractNumAndMoneyTotal = new ContractNumAndMoneyTotal();
				List<ContractNumAndMoney> list = new ArrayList<ContractNumAndMoney>();
				// 遍历年份数
				param.clear();
				param.put("type", contractTypeArry[i]);
				for (int j = 0; j < IntYear; j++) {
					// 获取该年份下的统计值
					param.put("year", year - j);
					List<ContractNumAndMoney> listTemp = contractNumAndMoneyService.getNumAndMoney(param);
					if (listTemp.size() > 0) {
						contractNumAndMoney = listTemp.get(0);
						contractNumAndMoney.setYear(year - j);
						list.add(contractNumAndMoney);
					}
				}
				// 获取合计数
				// 1.横向合计
				Integer rowNum = 0;
				Double rowMoney = 0.0;
				if (list.size() > 0) {
					for (ContractNumAndMoney conNumAndMoney : list) {
						rowNum = rowNum + conNumAndMoney.getNum();
						rowMoney = rowMoney + conNumAndMoney.getMoney();

					}
					contractNumAndMoneyTotal.setList(list);
					contractNumAndMoneyTotal.setRowTotalMoney(rowMoney);
					contractNumAndMoneyTotal.setRowTotalNum(rowNum);
				}
				// 添加结果集
				if (null != contractNumAndMoneyTotal) {
					if (("采购合同").equals(contractTypeArry[i])) {
						cgResult.put(contractTypeArry[i], contractNumAndMoneyTotal);
					} else {
						result.put(contractTypeArry[i], contractNumAndMoneyTotal);
					}

				}
			}
			// 2.纵向合计
			Integer colNum = 0;
			Double colMoney = 0.0;
			// 特定年份的不同类型，获取个数和金额
			List<ContractNumAndMoney> list = new ArrayList<ContractNumAndMoney>();
			for (int j = 0; j < IntYear; j++) {
				ContractNumAndMoney contractNumAndMoney = new ContractNumAndMoney();
				param.put("year", year - j);
				param.put("contractTypeArry", Arrays.asList(contractTypeArry));
				List<ContractNumAndMoney> colList = contractNumAndMoneyService.getNumAndMoneyCol(param);
				if (colList.size() > 0) {
					contractNumAndMoney = colList.get(0);
					contractNumAndMoney.setYear(year - j);
					list.add(contractNumAndMoney);
				}
			}
			// 获取横向合计列的竖向合计值
			Map<String, ContractNumAndMoneyTotal> coltotal = new HashMap<String, ContractNumAndMoneyTotal>();
			ContractNumAndMoneyTotal colContractNumAndMoneyTotal = new ContractNumAndMoneyTotal();
			Iterator<Entry<String, ContractNumAndMoneyTotal>> it = result.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, ContractNumAndMoneyTotal> entry = it.next();
				colNum = colNum + entry.getValue().getRowTotalNum();
				colMoney = colMoney + entry.getValue().getRowTotalMoney();
			}
			colContractNumAndMoneyTotal.setList(list);
			colContractNumAndMoneyTotal.setRowTotalMoney(colMoney);
			colContractNumAndMoneyTotal.setRowTotalNum(colNum);
			coltotal.put("合计", colContractNumAndMoneyTotal);
			return mv.addObject("result", result).addObject("coltotal", coltotal).addObject("cgResult", cgResult);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
		return null;
	}

	@RequestMapping("getContractType")
	@ResponseBody
	public List<String> getContractType(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> list = contractinfoService.getContractType();
			return list;
		} catch (Exception e) {
			log.error("错误信息",e);
		}
		return null;
	}

	@RequestMapping("contractIsExist")
	public void beforeValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contractnum = RequestUtil.getString(request, "contractnum", "");
		String contracttype = RequestUtil.getString(request, "contracttype", "");
		Long runid = RequestUtil.getLong(request, "runid", 0l);
		if (StringUtils.isEmpty(contracttype) || StringUtils.isEmpty(contractnum)) {
			writeResultMessage(response.getWriter(), "合同编号错误", ResultMessage.Fail);
			return;
		}
		Contractinfo contractinfoByNum = contractinfoService.getContractinfoByNum(contractnum);
		int result = 0;// 0：不存在 1:已存在
		if (contractinfoByNum != null) {
			result = 1;
		} else {
			long countByContractnum = contractnumService.getBpmDataCountByContractnum(contractnum, runid);
			if (countByContractnum > 0) {
				result = 1;
			}
		}
		if (result == 1) {
			// 矫正合同编号相关数据
			contractnumService.adjustContractnum(contractnum, contracttype);
		}
		writeResultMessage(response.getWriter(), result + "", ResultMessage.Success);
	}

	/**
	 * 仅供合同添加操作调用
	 * 
	 * @param contractnum
	 * @param contracttype
	 * @return
	 */
	private int contractIsExist(String contractnum, String contracttype) {

		if (StringUtils.isEmpty(contracttype) || StringUtils.isEmpty(contractnum)) {
			return -1;// 参数错误
		}
		Contractinfo contractinfoByNum = contractinfoService.getContractinfoByNum(contractnum);
		int result = 0;// 0：不存在 1:已存在
		if (contractinfoByNum != null) {
			result = 1;
		} else {
			long countByContractnum = contractnumService.getBpmDataCountByContractnum(contractnum, 0l);
			if (countByContractnum > 0) {
				result = 1;
			}
		}
		if (result == 1) {
			// 矫正合同编号相关数据
			contractnumService.adjustContractnum(contractnum, contracttype);
		}
		return result;
	}
	/*
	 * //批量跟新
	 * 
	 * @RequestMapping("updateByExcel")
	 * 
	 * @ResponseBody public void updateByExcel(HttpServletRequest request, HttpServletResponse response){ List<Contractinfo> list = contractinfoService.getAllInfo(); Contractinfo newContractinfo = new
	 * Contractinfo(); for(Contractinfo contractinfo:list){ //通过合同编号获取信息 List<Contractinfo> contractInfoList = contractinfoService.getByNum(contractinfo.getContract_num());
	 * if(contractInfoList.size()>0){ //跟新合同信息 newContractinfo = contractInfoList.get(0); newContractinfo.setArrival_amount(contractinfo.getArrival_amount());
	 * newContractinfo.setArrival_time(contractinfo.getArrival_time()); newContractinfo.setBidding_method(contractinfo.getBidding_method());
	 * 
	 * newContractinfo.setContract_name(contractinfo.getContract_name()); newContractinfo.setOwner_name(contractinfo.getOwner_name());
	 * newContractinfo.setContract_money(contractinfo.getContract_money()); newContractinfo.setContract_handler(contractinfo.getContract_handler());
	 * newContractinfo.setContract_handlerID(contractinfo.getContract_handlerID()); newContractinfo.setContract_department(contractinfo.getContract_department()); newContractinfo.setContract_ } } }
	 */
	/*
	 * @RequestMapping("updateById") public void updateById(HttpServletRequest request, HttpServletResponse response){ Long Id = 30000000923000L; ContractSealApplication contractSeal =
	 * contractSealApplicationService.getById(Id); if(contractSeal!=null){ String contract_money = contractSeal.getContract_money(); if(contract_money==null || !(contract_money.length()>0)){
	 * contract_money = "0.000000"; } String settlement_money = contractSeal.getSettlement_money(); if(settlement_money==null || !(settlement_money.length()>0)){ settlement_money = "0.000000"; }
	 * String project_bid_floorprice = contractSeal.getProject_bid_floorprice(); if(project_bid_floorprice==null || !(project_bid_floorprice.length()>0)){ project_bid_floorprice = "0.000000"; } String
	 * project_bid_price = contractSeal.getProject_bid_price(); if(project_bid_price==null || !(project_bid_price.length()>0)){ project_bid_price = "0.000000"; } String total_investment =
	 * contractSeal.getTotal_investment(); if(total_investment==null || !(total_investment.length()>0)){ total_investment = "0.000000"; } String invoice_amount = contractSeal.getInvoice_amount();
	 * if(invoice_amount==null || !(invoice_amount.length()>0)){ invoice_amount = "0.000000"; } Contractinfo contractinfo = new Contractinfo(contractSeal.getContract_departmentID(),
	 * contractSeal.getContract_num(), Double.valueOf(contract_money), contractSeal.getContract_name(), Double.valueOf(settlement_money), contractSeal.getContracttype(),
	 * Double.valueOf(project_bid_floorprice), contractSeal.getContract_status(), Double.valueOf(project_bid_price), contractSeal.getFirst_party(), contractSeal.getRate(),
	 * contractSeal.getSecond_party(), contractSeal.getProject_content(), contractSeal.getPayment_type(), contractSeal.getProject_scale(), contractSeal.getSigning_time(), contractSeal.getIsrecord(),
	 * contractSeal.getProject_director(), contractSeal.getIssave(), contractSeal.getProject_status(), contractSeal.getIsrecovery(), contractSeal.getContract_department(), contractSeal.getInout(),
	 * contractSeal.getProject_place(), contractSeal.getStart_time(), contractSeal.getOwner_name(), contractSeal.getEnd_time(), contractSeal.getProject_origin(), contractSeal.getProject_leader(),
	 * contractSeal.getBidding_platform(), contractSeal.getContract_handler(), contractSeal.getBidding_method(), contractSeal.getContract_reviewer(), contractSeal.getContract_authorized_person(),
	 * contractSeal.getRemark(), contractSeal.getUpdater(), contractSeal.getCreater(), contractSeal.getUpdate_time(), contractSeal.getCreate_time(), contractSeal.getCustodian(),
	 * Double.valueOf(total_investment), flowToEntityService.flowToEntityFile(contractSeal.getFile()), contractSeal.getNo(), contractSeal.getType(),Double.valueOf(invoice_amount),
	 * contractSeal.getProject_directorID(),contractSeal.getContract_handlerID(),
	 * contractSeal.getContract_reviewerID(),contractSeal.getProject_leaderID(),contractSeal.getContract_authorized_personID(),contractSeal.getUpdaterID(),contractSeal.getCreaterID(),
	 * contractSeal.getCustodianID(),contractSeal.getProjiect_name(),contractSeal.getSections_name()); try { contractinfoService.save(contractinfo); } catch (Exception e) { log.error("错误信息",e); } } }
	 */

	@RequestMapping("toprint")
	public ModelAndView toprint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Contractinfo contractinfo = contractinfoService.getById(id);
		if (contractinfo != null) {
			List<ContractBillingRecord> contractBillingRecordList = contractinfoService.getContractBillingRecordList(contractinfo.getId());
			contractinfo.setContractBillingRecordList(contractBillingRecordList);
			List<ContractPaymentRecord> contractPaymentRecordList = contractinfoService.getContractPaymentRecordList(contractinfo.getId());
			contractinfo.setContractPaymentRecordList(contractPaymentRecordList);
		}
		return getAutoView().addObject("contractinfo", contractinfo).addObject("returnUrl", returnUrl);
	}
	
	/**
	 * 导出合同收款记录
	 */
	@RequestMapping("upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
		XSSFSheet sheet = workbook.createSheet(); // 创建工作表
		int row = 0;
		XSSFRow rowm = sheet.createRow(row);
		XSSFCell cellTiltle = rowm.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(row, row, 0,13));
		cellTiltle.setCellValue("合同开票记录");
		XSSFCellStyle cellStyle = workbook.createCellStyle();   
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		cellTiltle.setCellStyle(cellStyle);
		row++;
		
		XSSFRow row1 = sheet.createRow(row);
		List<ContractBillingRecord> contractBillingRecordList = contractinfoService.getAllContractBillingRecordList();
		String[] rowName = new String[] { "序号","合同编号","合同名称","甲方","合同金额(万元)","合同经手人","合归属部门","总投资(万元)","开票时间","开票金额","经办人","审批状态","领票人","到账时间"};
		for(int i=0;i<=13;i++){
			XSSFCell createCell = row1.createCell(i);
			createCell.setCellValue(rowName[i]);
		}
		row++;
		
		int changeRow=row;
		String contractNum = "";
		int num = 1;
		int totalRow = contractBillingRecordList.size()+row-1;
		for (ContractBillingRecord contractBillingRecord : contractBillingRecordList) {
			Contractinfo contractinfo = contractinfoService.getById(contractBillingRecord.getRefId());
			if(null==contractinfo){//开票记录有，但合同信息里面找不到对应的信息
				continue;
			}
			String contractNumTemp = contractinfo.getContract_num();
			//List<ContractBillingRecord> countSum = contractinfoService.getContractBillingRecordList(contractBillingRecord.getRefId());
			XSSFRow rowTemp = sheet.createRow(row);
			XSSFCell cell0 = rowTemp.createCell(0);
			cell0.setCellValue(num);
			XSSFCell cell1 = rowTemp.createCell(1);
			cell1.setCellValue(contractinfo.getContract_num());
			XSSFCell cell2 = rowTemp.createCell(2);
			cell2.setCellValue(contractinfo.getContract_name());
			XSSFCell cell3 = rowTemp.createCell(3);
			cell3.setCellValue(contractinfo.getFirst_party());
			XSSFCell cell4 = rowTemp.createCell(4);
			cell4.setCellValue(contractinfo.getContract_money());
			XSSFCell cell5 = rowTemp.createCell(5);
			cell5.setCellValue(contractinfo.getContract_handler());
			XSSFCell cell6 = rowTemp.createCell(6);
			cell6.setCellValue(contractinfo.getContract_department());
			XSSFCell cell7 = rowTemp.createCell(7);
			cell7.setCellValue(contractinfo.getTotal_investment());
			XSSFCell cell8 = rowTemp.createCell(8);
			cell8.setCellValue(contractBillingRecord.getBilling_date()==null?"":DateUtil.formatDate(contractBillingRecord.getBilling_date(), "yyyy-MM-dd"));
			XSSFCell cell9 = rowTemp.createCell(9);
			cell9.setCellValue( contractBillingRecord.getInvoice_money());
			XSSFCell cell10 = rowTemp.createCell(10);
			cell10.setCellValue(contractBillingRecord.getOperator());
			XSSFCell cell11 = rowTemp.createCell(11);
			cell11.setCellValue(contractBillingRecord.getStatus());
			XSSFCell cell12 = rowTemp.createCell(12);
			cell12.setCellValue(contractBillingRecord.getBearer());
			XSSFCell cell13 = rowTemp.createCell(13);
			cell13.setCellValue(contractBillingRecord.getPayment_date()==null?"":DateUtil.formatDate(contractBillingRecord.getPayment_date(), "yyyy-MM-dd"));
			//计算该合同编号拥有的开票记录条数
			if(!contractNumTemp.equals(contractNum)){
				contractNum = contractNumTemp;
				if((row-changeRow)>1){
					//合并
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,0,0));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,1,1));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,2,2));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,3,3));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,4,4));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,5,5));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,6,6));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row-1,7,7));
				}
				changeRow = row;
				num++;
			}else{
				if(row==totalRow && (row-changeRow)>1){
					//合并
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,0,0));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,1,1));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,2,2));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,3,3));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,4,4));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,5,5));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,6,6));
					sheet.addMergedRegion(new CellRangeAddress(changeRow,row,7,7));
				}
			}
			row++;
		}
		ByteArrayInputStream bis = null;
		if (workbook != null) {
			try {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				workbook.write(output);
				byte[] ba = output.toByteArray();
				bis = new ByteArrayInputStream(ba);
			} catch (IOException e) {
				log.error("错误信息",e);
			}
		}
	    response.setContentType(request.getServletContext().getMimeType("合同开票"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx"));  
	    response.setHeader("Content-disposition", "attachment; filename="+ FileUtil.encodeDownloadFilename("合同开票"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx", request.getHeader("user-agent"))); 
	    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    bis.close();
	    bos.close(); 
	    
	}
	
	//处理合同编号
	@RequestMapping("dealContractNumHis")
	public ModelAndView dealContractNumHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Contractinfo> list = contractinfoService.getAll();
		String errorNum = "";
		int sum = 0;
		for(Contractinfo contractinfo : list){
			String num = contractinfo.getContract_num();
			//String reg = "[a-zA-Z]{2,4}[1,2][0-9]{3}[-]\\d+(|([\\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\\）]))";
			String regex = "([a-zA-Z]{2,4}[1,2][0-9]{3}[-]\\d+(|([\\（][\\u4E00-\u9FA5_a-zA-Z0-9]+[\\）])|([-]\\d+|[-]\\d)+(|([\\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\\）]))))";
			boolean flag = num.matches(regex);
			if(!flag){
				errorNum = errorNum + num +" ";
				sum++;
				continue;
			}
			num = contractinfoService.updateNum(num);
			contractinfo.setContract_num(num);
			contractinfoService.update(contractinfoService.getSort(contractinfo));
		}
		System.out.println("格式不对个数:"+sum);
		System.out.println(errorNum);
		return this.getAutoView();
	}
	
	
}