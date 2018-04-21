package com.hotent.makshi.service.finance;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.finance.WorkHourApplication;
import com.hotent.makshi.model.finance.Workstats;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysUserService;
import com.sun.tools.jdi.LinkedHashMap;
import com.hotent.makshi.dao.finance.WorkHourApplicationDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.finance.ProjectTaskHour;
import com.hotent.makshi.model.finance.UserWorkCost;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.finance.WorkCount;
import com.hotent.makshi.dao.finance.ProjectTaskHourDao;
import com.hotent.makshi.model.finance.CustomTaskHour;
import com.hotent.makshi.model.finance.OverTime;
import com.hotent.makshi.model.finance.ProjectMonthIncome;
import com.hotent.makshi.dao.finance.CustomTaskHourDao;

import com.hotent.core.service.BaseService;

@Service
public class WorkHourApplicationService extends BaseService<WorkHourApplication> {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private WorkHourApplicationDao dao;
	@Resource
	private ProjectTaskHourDao projectTaskHourDao;
	@Resource
	private CustomTaskHourDao customTaskHourDao;
	@Resource
	private UserWorkCostService userWorkCostService;
	@Resource
	private SysUserService userService;
	@Resource
	private ProjectMonthIncomeService projectMonthIncomeService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessRunService processRunService;
	public WorkHourApplicationService() {
	}

	@Override
	protected IEntityDao<WorkHourApplication, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据外键删除子表记录
	 * 
	 * @param id
	 */
	private void delByPk(Long id) {
		projectTaskHourDao.delByMainId(id);
		customTaskHourDao.delByMainId(id);
	}

	/**
	 * 删除数据 包含相应子表记录
	 * 
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for (Long id : lAryId) {
			delByPk(id);
			dao.delById(id);
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param workHourApplication
	 * @throws Exception
	 */
	public void addAll(WorkHourApplication workHourApplication) throws Exception {
		super.add(workHourApplication);
		addSubList(workHourApplication);
	}

	/**
	 * 更新数据
	 * 
	 * @param workHourApplication
	 * @throws Exception
	 */
	public void updateAll(WorkHourApplication workHourApplication) throws Exception {
		super.update(workHourApplication);
		delByPk(workHourApplication.getId());
		addSubList(workHourApplication);
	}

	/**
	 * 添加子表记录
	 * 
	 * @param workHourApplication
	 * @throws Exception
	 */
	public void addSubList(WorkHourApplication workHourApplication) throws Exception {

		List<ProjectTaskHour> projectTaskHourList = workHourApplication.getProjectTaskHourList();
		if (BeanUtils.isNotEmpty(projectTaskHourList)) {

			for (ProjectTaskHour projectTaskHour : projectTaskHourList) {
				projectTaskHour.setRefId(workHourApplication.getId());
				Long id = UniqueIdUtil.genId();
				projectTaskHour.setId(id);
				projectTaskHourDao.add(projectTaskHour);
			}
		}

		List<CustomTaskHour> customTaskHourList = workHourApplication.getCustomTaskHourList();
		if (BeanUtils.isNotEmpty(customTaskHourList)) {

			for (CustomTaskHour customTaskHour : customTaskHourList) {
				customTaskHour.setRefId(workHourApplication.getId());
				Long id = UniqueIdUtil.genId();
				customTaskHour.setId(id);
				customTaskHourDao.add(customTaskHour);
			}
		}
	}

	/**
	 * 根据外键获得项目任务工时列表
	 * 
	 * @param id
	 * @return
	 */
	public List<ProjectTaskHour> getProjectTaskHourList(Long id) {
		return projectTaskHourDao.getByMainId(id);
	}

	/**
	 * 根据外键获得自定义任务工时列表
	 * 
	 * @param id
	 * @return
	 */
	public List<CustomTaskHour> getCustomTaskHourList(Long id) {
		return customTaskHourDao.getByMainId(id);
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			WorkHourApplication workHourApplication = getWorkHourApplication(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				workHourApplication.setId(genId);
				this.addAll(workHourApplication);
			} else {
				workHourApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(workHourApplication);
			}
			cmd.setBusinessKey(workHourApplication.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取WorkHourApplication对象
	 * 
	 * @param json
	 * @return
	 */
	public WorkHourApplication getWorkHourApplication(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("projectTaskHourList", ProjectTaskHour.class);
		map.put("customTaskHourList", CustomTaskHour.class);
		WorkHourApplication workHourApplication = (WorkHourApplication) JSONObject.toBean(obj, WorkHourApplication.class, map);
		return workHourApplication;
	}

	/**
	 * 保存 工时填报申请 信息
	 * 
	 * @param workHourApplication
	 */

	public void save(WorkHourApplication workHourApplication) throws Exception {
		Long id = workHourApplication.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			workHourApplication.setId(id);
			this.addAll(workHourApplication);
		} else {
			this.updateAll(workHourApplication);
		}
	}

	public List<WorkHourApplication> getByApplicantTime(Date applicant_time, Long userid) {
		Map<String, Object> params = new HashMap<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date time = applicant_time;
		try {
			time = simpleDateFormat.parse(simpleDateFormat.format(applicant_time));
		} catch (ParseException e) {
			log.error("错误信息", e);
		}
		params.put("applicant_time", time);
		params.put("userid", userid);
		return dao.getBySqlKey("getByApplicantTime", params);
	}

	public void importExcel(InputStream is, String fileName, String month) throws Exception {
		Workbook wkb = null;
		Sheet sht;
		Row headRow;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if (fileType.equals("xls")) {
			wkb = new HSSFWorkbook(is);
		} else if (fileType.equals("xlsx")) {
			wkb = new XSSFWorkbook(is);
		} else {
			throw new RuntimeException("上传文件的格式不正确");
		}
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		// 标题
		Cell cell1 = headRow.getCell(0);
		Cell cell2 = headRow.getCell(1);
		Cell cell3 = headRow.getCell(2);
		String stringCellValue1 = getStringCellValue(cell1);
		String stringCellValue2 = getStringCellValue(cell2);
		String stringCellValue3 = getStringCellValue(cell3);
		if (!"员工编号".equals(stringCellValue1)) {
			throw new RuntimeException("第一列行标题必须为员工编号");
		}
		if (!"正常单位工时成本".equals(stringCellValue2)) {
			throw new RuntimeException("第二列行标题必须为正常单位工时成本");
		}
		if (!"加班单位工时成本".equals(stringCellValue3)) {
			throw new RuntimeException("第三列行标题必须为加班单位工时成本");
		}
		List<String> error = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			Row row = sht.getRow(i);
			String cellValue1 = getStringCellValue(row.getCell(0));
			String cellValue2 = getStringCellValue(row.getCell(1));
			String cellValue3 = getStringCellValue(row.getCell(2));
			SysUser sysUser = userService.getByAccount(cellValue1);
			if (sysUser == null) {
				error.add("第" + i + "行数据错误：" + "员工编号为" + cellValue1 + "的用户不存在");
				continue;
			}
			Double workcost = 0d;
			Double overcost = 0d;
			try {
				workcost = Double.valueOf(cellValue2.toString());
			} catch (NumberFormatException e) {
				error.add("第" + i + "行数据错误：" + "员工编号为" + cellValue1 + "的正常单位工时成本应为数字格式");
				continue;
			}
			try {
				overcost = Double.valueOf(cellValue3.toString());
			} catch (NumberFormatException e) {
				error.add("第" + i + "行数据错误：" + "员工编号为" + cellValue1 + "的加班单位工时成本应为数字格式");
				continue;
			}
			List<UserWorkCost> list = userWorkCostService.getByUserIdAndMonth(sysUser.getUserId(), month);
			UserWorkCost uwc = new UserWorkCost();
			if (list != null && list.size() > 0) {
				uwc = list.get(0);
				uwc.setUtime(new Date());
			} else {
				uwc.setCtime(new Date());
				uwc.setUtime(new Date());
				uwc.setUserid(sysUser.getUserId() + "");
				uwc.setMonth(month);
			}
			uwc.setWork_hour_cost(workcost);
			uwc.setOver_hour_cost(overcost);
			userWorkCostService.save(uwc);
		}

	}

	private String getStringCellValue(Cell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell.trim();
	}

	public void importProIncomeExcel(InputStream is, String fileName, String month) throws Exception {
		Workbook wkb = null;
		Sheet sht;
		Row headRow;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if (fileType.equals("xls")) {
			wkb = new HSSFWorkbook(is);
		} else if (fileType.equals("xlsx")) {
			wkb = new XSSFWorkbook(is);
		} else {
			throw new RuntimeException("上传文件的格式不正确");
		}
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		// 标题
		Cell cell1 = headRow.getCell(0);
		Cell cell2 = headRow.getCell(1);
		String stringCellValue1 = getStringCellValue(cell1);
		String stringCellValue2 = getStringCellValue(cell2);
		if (!"项目ID".equalsIgnoreCase(stringCellValue1)) {
			throw new RuntimeException("第一列行标题必须为项目ID");
		}
		if (!"项目收入".equals(stringCellValue2)) {
			throw new RuntimeException("第二列行标题必须为项目收入");
		}
		List<String> error = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		DecimalFormat decimalFormat = new DecimalFormat("#");
		for (int i = 1; i <= rowNum; i++) {
			Row row = sht.getRow(i);
			String cellValue1 = getStringCellValue(row.getCell(0));
			String cellValue2 = getStringCellValue(row.getCell(1));
			// Project pro = projectService.getProByName(cellValue1);
			Long proId;
			try {
				Number parse = decimalFormat.parse(cellValue1);
				proId = parse.longValue();
			} catch (Exception e1) {
				error.add("第" + i + "行数据错误：" + "项目ID必须为数字格式！");
				continue;
			}
			Project pro = projectService.getById(proId);
			if (pro == null) {
				error.add("第" + i + "行数据错误：" + "项目ID为" + cellValue1 + "的项目不存在！");
				continue;
			}
			Double income = 0d;
			try {
				income = Double.valueOf(cellValue2.toString());
			} catch (NumberFormatException e) {
				error.add("第" + i + "行数据错误：" + "项目ID为" + cellValue1 + "的项目不存在！");
				continue;
			}
			List<ProjectMonthIncome> proIncomes = projectMonthIncomeService.getByProIdAndMonth(pro.getId() + "", month);

			ProjectMonthIncome proin = new ProjectMonthIncome();
			if (proIncomes != null && proIncomes.size() > 0) {
				proin = proIncomes.get(0);
				proin.setUtime(new Date());
			} else {
				proin.setCtime(new Date());
				proin.setUtime(new Date());
				proin.setProid(pro.getId() + "");
				proin.setMonth(month);
			}
			proin.setIncome(income);
			projectMonthIncomeService.save(proin);
		}

	}

	// 根据用户id和指定顺序的项目id集合和月份获取该用户对应的项目的创收以及当年(该月份之前所有月份)的累计创收
	public List<List<Map<String, String>>> getListMapByUserIdAndPros(List<String> proIds, String yearmonth, List<Map<String, String>> userData) {
		List<List<Map<String, String>>> list = new ArrayList<>();
		List<String> userids = new ArrayList<>();
		if (userData != null && userData.size() > 0) {
			for (Map<String, String> userMap : userData) {
				userids.add(userMap.get("userid"));
			}
		} else {
			return list;
		}
		Map<String, Double> monthYearTotal = new HashMap<>();
		Map<String, Double> rowCountMap = new LinkedHashMap();// 行的合计
		double rowYearCount = 0d;
		double rowCurmonCount = 0d;
		for (Map<String, String> userMap : userData) {
			List<Map<String, String>> listMap = new ArrayList<>();
			String userid = userMap.get("userid");
			double yearcount = 0d;
			double curmoncount = 0d;
			String[] split = yearmonth.split("-");
			int mon = Integer.valueOf(split[1]);
			for (int i = 1; i <= mon; i++) {
				String yearmon = split[0] + "-" + String.format("%02d", i);
				for (String proid : proIds) {
					Map<String, String> monmap = new LinkedHashMap();
					monmap.put("proid", proid);
					monmap.put("userid", userid);
					monmap.put("username", userMap.get("username"));
					Double montCreatedIncome = montCreatedIncome(userid, proid, yearmon, userids);
					monmap.put("createdincome", montCreatedIncome.toString());
					yearcount += montCreatedIncome;
					if (i == mon) {
						curmoncount += montCreatedIncome;
						listMap.add(monmap);
						// 计算行累加
						Double temp = rowCountMap.get(proid) == null ? 0d : rowCountMap.get(proid);
						montCreatedIncome = montCreatedIncome + temp;
						rowCountMap.put(proid, montCreatedIncome);
					}
				}
			}
			monthYearTotal.put(userid, yearcount);
			Map<String, String> curmoncountMap = new LinkedHashMap();
			Map<String, String> yearMoncountMap = new LinkedHashMap();
			curmoncountMap.put("createdincome", String.valueOf(curmoncount));
			yearMoncountMap.put("createdincome", String.valueOf(yearcount));
			rowYearCount += yearcount;
			rowCurmonCount += curmoncount;
			listMap.add(curmoncountMap);
			listMap.add(yearMoncountMap);
			list.add(listMap);
		}
		// 添加行合计List信息
		List<Map<String, String>> rowlist = new ArrayList<>();
		Collection<Double> values = rowCountMap.values();
		for (Double d : values) {
			Map map = new HashMap<>();
			map.put("createdincome", d);
			rowlist.add(map);
		}
		Map rowMonCountMap = new HashMap<>();// 当月合计
		Map rowYearCountMap = new HashMap<>();// 当年累计
		rowMonCountMap.put("createdincome", rowCurmonCount);
		rowYearCountMap.put("createdincome", rowYearCount);
		rowlist.add(rowMonCountMap);
		rowlist.add(rowYearCountMap);
		list.add(rowlist);
		return list;
	}

	// 计算某月份 员工对某个项目的创收=(员工成本/总员工成本)*某个项目的收入
	public Double montCreatedIncome(String userid, String proid, String yearmon, List<String> userids) {
		Map<String, Double> map = getMonthCostByProid(proid, yearmon, userids);
		Collection<Double> values = map.values();
		double monTotalCost = 0d;
		for (Double d : values) {
			monTotalCost += d;
		}
		Set<String> keySet = map.keySet();
		Double userCost = map.get(userid);
		if (userCost == null || userCost == 0d || monTotalCost == 0d) {
			return 0d;
		} else {
			List<ProjectMonthIncome> monIncome = projectMonthIncomeService.getByProIdAndMonth(proid, yearmon);
			if (monIncome != null && monIncome.size() > 0) {
				ProjectMonthIncome projectMonthIncome = monIncome.get(0);
				if (projectMonthIncome != null) {
					Double income = projectMonthIncome.getIncome();
					if (income != null && income > 0) {
						return (userCost * income) / monTotalCost;
					}
				}
			}
		}
		return 0d;
	}

	/**
	 * 
	 * @param proid
	 * @param mon
	 * @param userids
	 * @return
	 */
	private Map<String, Double> getMonthCostByProid(String proid, String yearmon, List<String> userids) {
		Map<String, Double> map = new HashMap<>();// 用户成本
		for (String userid : userids) {
			Double monthProCostByUserIdAndMon = userWorkCostService.getMonthProCostByUserIdAndMon(proid, userid, yearmon);
			map.put(userid, monthProCostByUserIdAndMon);
		}
		return map;
	}

	public InputStream export(List<Object[]> proHourList, List<Object[]> userHourCostList, List<Object[]> proIncomeList, List<Object[]> proCostList, List<Object[]> userCreateIncomeList, String title,
			String[] subtitle) {
		// proIncomeList和userCreateIncomeList为null时
		InputStream is = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
			XSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			int row = 0;
			XSSFRow rowm = sheet.createRow(row++);
			XSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			XSSFCellStyle columnTopStyle = this.getEntryColumnTopStyle(workbook);// 获取列头样式对象
			XSSFCellStyle subTitleStyle = this.getSubTitleStyle(workbook);// 获取子标题标语样式对象
			XSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象

			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, ((proHourList.get(0).length == 0 ? 10 : proHourList.get(0).length) - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);
			// 标语1：
			XSSFRow firstSubTitle = sheet.createRow(row);
			XSSFCell firstSubTitleCell = firstSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, ((proHourList.get(0).length == 0 ? 10 : proHourList.get(0).length) - 1)));
			firstSubTitleCell.setCellStyle(subTitleStyle);
			firstSubTitleCell.setCellValue(subtitle[0]);
			row++;
			// 将查询出的数据设置到sheet对应的单元格中
			if ((proHourList == null) || (proHourList != null && proHourList.size() <= 0)) {
				// 无数据
				XSSFRow createRow = sheet.createRow(row);
				XSSFCell createCell = createRow.createCell(0);
				createCell.setCellValue("无数据");
				row++;
			} else {
				for (int i = 0; i < proHourList.size(); i++) {
					Object[] obj = proHourList.get(i);// 遍历每个对象
					XSSFRow cellrow = sheet.createRow(i + row + 1);// 创建所需的行数
					for (int j = 0; j < obj.length; j++) {
						XSSFCell cell = null; // 设置单元格的数据类型
						// 此处注释部分为第一列创建序号
						cell = cellrow.createCell(j, XSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的值
						}
						cell.setCellStyle(style); // 设置单元格样式
					}
				}
				row += proHourList.size() + 1;
			}

			// 获取标语二出现的行数
			// 标语2：
			XSSFRow secondSubTitle = sheet.createRow(row);// 空一行
			XSSFCell secondSubTitleCell = secondSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, ((userHourCostList.get(0).length == 0 ? 10 : userHourCostList.get(0).length) - 1)));
			secondSubTitleCell.setCellStyle(subTitleStyle);
			secondSubTitleCell.setCellValue(subtitle[1]);
			row++;
			if ((userHourCostList == null) || (userHourCostList != null && proHourList.size() <= 0)) {
				// 无数据
				XSSFRow createRow = sheet.createRow(row);
				XSSFCell createCell = createRow.createCell(0);
				createCell.setCellValue("无数据");
				row++;
			} else {
				for (int i = 0; i < userHourCostList.size(); i++) {
					Object[] obj = userHourCostList.get(i);// 遍历每个对象
					XSSFRow cellrow = sheet.createRow(i + row + 1);// 创建所需的行数
					for (int j = 0; j < obj.length; j++) {
						XSSFCell cell = null; // 设置单元格的数据类型
						// 此处注释部分为第一列创建序号
						cell = cellrow.createCell(j, XSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的值
						}
						cell.setCellStyle(style); // 设置单元格样式
					}
				}
				row += userHourCostList.size() + 1;
			}
			// 标语3：(项目收入 统计工时成本是不需要导出该项)
			if (proIncomeList == null) {
				// (项目收入 统计工时成本是不需要导出该项)
			} else {
				XSSFRow thirdSubTitle = sheet.createRow(row);// 空一行
				XSSFCell thirdSubTitleCell = thirdSubTitle.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(row, row, 0, (proIncomeList.get(0).length - 1)));
				thirdSubTitleCell.setCellStyle(subTitleStyle);
				thirdSubTitleCell.setCellValue(subtitle[2]);
				row++;
				if ((proIncomeList == null) || (proIncomeList != null && proIncomeList.size() <= 0)) {
					// 无数据
					XSSFRow createRow = sheet.createRow(row);
					XSSFCell createCell = createRow.createCell(0);
					createCell.setCellValue("无数据");
					row++;
				} else {
					for (int i = 0; i < proIncomeList.size(); i++) {
						Object[] obj = proIncomeList.get(i);// 遍历每个对象
						XSSFRow cellrow = sheet.createRow(i + row + 1);// 创建所需的行数
						for (int j = 0; j < obj.length; j++) {
							XSSFCell cell = null; // 设置单元格的数据类型
							// 此处注释部分为第一列创建序号
							cell = cellrow.createCell(j, XSSFCell.CELL_TYPE_STRING);
							if (!"".equals(obj[j]) && obj[j] != null) {
								cell.setCellValue(obj[j].toString()); // 设置单元格的值
							}
							cell.setCellStyle(style); // 设置单元格样式
						}
					}
					row += proIncomeList.size() + 1;
				}
			}

			// 获取标语4出现的行数
			// 标语4：
			XSSFRow fourthSubTitle = sheet.createRow(row);// 空一行
			XSSFCell fourthSubTitleCell = fourthSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, ((proCostList.get(0).length == 0 ? 10 : proCostList.get(0).length) - 1)));
			fourthSubTitleCell.setCellStyle(subTitleStyle);
			fourthSubTitleCell.setCellValue(subtitle[3]);
			row++;
			if ((proCostList == null) || (proCostList != null && proCostList.size() <= 0)) {
				// 无数据
				XSSFRow createRow = sheet.createRow(row);
				XSSFCell createCell = createRow.createCell(0);
				createCell.setCellValue("无数据");
				row++;
			} else {
				for (int i = 0; i < proCostList.size(); i++) {
					Object[] obj = proCostList.get(i);// 遍历每个对象
					XSSFRow cellrow = sheet.createRow(i + row + 1);// 创建所需的行数
					for (int j = 0; j < obj.length; j++) {
						XSSFCell cell = null; // 设置单元格的数据类型
						// 此处注释部分为第一列创建序号
						cell = cellrow.createCell(j, XSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的值
						}
						cell.setCellStyle(style); // 设置单元格样式
					}
				}
				row += proCostList.size() + 1;
			}
			if (userCreateIncomeList == null) {
				// 员工创收:统计员工工时成本时不导出该项
			} else {
				// 标语5：(员工创收:统计员工工时成本时不导出该项)
				XSSFRow fifthSubTitle = sheet.createRow(row);// 空一行
				XSSFCell fifthSubTitleCell = fifthSubTitle.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(row, row, 0, ((userCreateIncomeList.get(0).length == 0 ? 10 : userCreateIncomeList.get(0).length) - 1)));
				fifthSubTitleCell.setCellStyle(subTitleStyle);
				fifthSubTitleCell.setCellValue(subtitle[4]);
				row++;
				if ((userCreateIncomeList == null) || (userCreateIncomeList != null && userCreateIncomeList.size() <= 0)) {
					// 无数据
					XSSFRow createRow = sheet.createRow(row);
					XSSFCell createCell = createRow.createCell(0);
					createCell.setCellValue("无数据");
					row++;
				} else {
					for (int i = 0; i < userCreateIncomeList.size(); i++) {
						Object[] obj = userCreateIncomeList.get(i);// 遍历每个对象
						XSSFRow cellrow = sheet.createRow(i + row + 1);// 创建所需的行数
						for (int j = 0; j < obj.length; j++) {
							XSSFCell cell = null; // 设置单元格的数据类型
							// 此处注释部分为第一列创建序号
							cell = cellrow.createCell(j, XSSFCell.CELL_TYPE_STRING);
							if (!"".equals(obj[j]) && obj[j] != null) {
								cell.setCellValue(obj[j].toString()); // 设置单元格的值
							}
							cell.setCellStyle(style); // 设置单元格样式
						}
					}
					row += userCreateIncomeList.size() + 1;
				}
			}

			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < proHourList.get(0).length; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					XSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						XSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}

				}

				if (colNum == 0) {
					sheet.setColumnWidth(colNum, 10 * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth > 50 ? 50 : columnWidth + 4) * 256);
				}
			}

			if (workbook != null) {
				try {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					workbook.write(output);
					byte[] ba = output.toByteArray();
					is = new ByteArrayInputStream(ba);
				} catch (IOException e) {
					log.error("错误信息", e);
				}
			}

		} catch (Exception e) {
			log.error("错误信息", e);
		}
		return is;// 返回的是一个输入流
	}

	/*
	 * 招聘入职员工表单列头单元格样式
	 */
	public XSSFCellStyle getEntryColumnTopStyle(XSSFWorkbook workbook) {

		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 20);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 子标题信息单元格样式
	 */
	public XSSFCellStyle getSubTitleStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	public boolean hasExistSubmitedBpm(Map<String, Object> filters) {
		List<WorkHourApplication> bySqlKey = dao.getBySqlKey("hasExist", filters);
		if (bySqlKey != null && bySqlKey.size() > 0) {
			return true;
		}
		return false;
	}

	public List getVactions(Date applicant_time, Long userid) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("applicant_time", applicant_time);
		filters.put("userid", userid);
		return dao.getBySqlKey("getVactions", filters);
	}

	public List<Vaction> getCompletedVactionsByOrgPathAndMonth(String queryMonth, String orgPath) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("queryMonth", queryMonth);
		filters.put("orgPath", orgPath);
		List bySqlKey = dao.getBySqlKey("getCompletedVactionsByOrgPathAndMonth", filters);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<Vaction>();
		}
		return bySqlKey;
	}
	public List<Vaction> getHadStartedVactionByUserId(Long userid) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("userId", userid);
		List bySqlKey = dao.getBySqlKey("getHadStartedVactionByUserId", filters);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<Vaction>();
		}
		return bySqlKey;
	}
	
	public List<OverTime> getCompletedOvertimeByOrgPathAndMonth(String queryMonth, String orgPath) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("queryMonth", queryMonth);
		filters.put("orgPath", orgPath);
		List bySqlKey = dao.getBySqlKey("getCompletedOvertimeByOrgPathAndMonth", filters);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<OverTime>();
		}
		return bySqlKey;
	}

	public List<Workstats> getWorkStatsByOrgPathAndMonth(String queryMonth, String orgPath) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("queryMonth", queryMonth);
		filters.put("orgPath", orgPath);
		List bySqlKey = dao.getBySqlKey("getWorkStatsByOrgPathAndMonth", filters);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<Workstats>();
		}
		return bySqlKey;
	}

	public List<WorkCount> getWorkStatsCountByOrgPathAndMonth(String queryMonth, String orgPath, String queryneedWorkedDay) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("queryMonth", queryMonth);
		filters.put("orgPath", orgPath);
		filters.put("queryneedWorkedDay", queryneedWorkedDay); // 只查询需要上班的考勤 加班情况通过加班流程计算
		List bySqlKey = dao.getBySqlKey("getWorkStatsCountByOrgPathAndMonth", filters);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<WorkCount>();
		}
		return bySqlKey;
	}

	/**
	 * 带分页+
	 * 
	 * @param orgPath
	 * @param queryneedWorkedDay
	 * @param queryFilter
	 * @return
	 */
	public List<WorkCount> getWorkStatsCountByOrgPathAndMonth(String orgPath, String queryneedWorkedDay, QueryFilter queryFilter) {
		Map<String, Object> filters = queryFilter.getFilters();
		filters.put("orgPath", orgPath);
		filters.put("queryneedWorkedDay", queryneedWorkedDay); // 只查询需要上班的考勤 加班情况通过加班流程计算
		List bySqlKey = dao.getBySqlKey("getWorkStatsCountByOrgPathAndMonth", queryFilter);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return new ArrayList<WorkCount>();
		}
		return bySqlKey;
	}
	/**
	 * 考勤流程提交失败后 需删除流程保存的流程实例相关数据
	 * @param businessDateId 考勤业务保存的id（非流程直接关联的）
	 * 需通过业务表w_work_hour_application的F_businessDateId字段查出主键Id(对应流程的businesskey) 然后通过businesskey查出待删除的流程实例
	 */
	public void clearProcessRun(Long bussinessDataId){
		List<WorkHourApplication> bySqlKey = dao.getBySqlKey("getByBusinessDataId", bussinessDataId);
		if(bySqlKey!=null && bySqlKey.size()>0){
			WorkHourApplication workHourApplication = bySqlKey.get(0);
			Long bussinessKey = workHourApplication.getId();
			ProcessRun byBusinessKey = processRunService.getByBusinessKey(bussinessKey+"");
			if(byBusinessKey!=null){
				processRunService.delByIds(new Long[]{byBusinessKey.getRunId()});
			}
		}
	}
	
	public boolean lockById(Long taskid) {
		boolean f=false;
		int insert = dao.insert("lockById", taskid);
		if(insert==1) {
			f=true;
		}
		return f;
	}
	public void unlock(Long taskid) {
		dao.delBySqlKey("unlockById", taskid);
	}
	/**
	 * 清理考勤提交超时的锁
	 * @param taskid
	 */
	public void clearTimeOutLock() {
		dao.delBySqlKey("clearTimeOutLock",null);
	}
}
