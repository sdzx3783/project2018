package com.hotent.makshi.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.model.vacation.AnuualLeave;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.model.system.UserUnder;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserUnderService;

/**
 * 操作Excel表格的功能类
 */
@Service
public class ExcelReader {
	private static final Logger log = Logger.getLogger(ExcelReader.class);

	private static SysUserService userService = null;
	private static UserInfomationService userInfomationService = null;
	private static AnuualLeaveService anuualLeaveService = null;
	private static ProjectService projectService;
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	private Workbook wkb;
	private Sheet sht;
	private Row headRow;
	private ContractinfoService contractinfoService;
	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String> readExcelContent(InputStream is) {
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println(colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
				// str += getStringCellValue(row.getCell((short) j)).trim() +
				// "-";
				str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
				j++;
			}
			content.put(i, str);
			str = "";
		}
		return content;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}

		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			log.error("错误信息", e);
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf((int) cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<SysUser> readUserExcel(InputStream is) {
		List<SysUser> users = new ArrayList<SysUser>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println(colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			SysUser user = new SysUser();
			user.setUserId(UniqueIdUtil.genId());
			user.setFullname(getCellFormatValue(row.getCell(0)).trim());
			user.setAccount(getCellFormatValue(row.getCell(1)).trim());
			user.setPassword(getCellFormatValue(row.getCell(2)).trim());
			user.setIsExpired((short) 0);
			user.setIsLock((short) 0);
			user.setCreatetime(new Date());
			user.setCreator("超级管理员");
			user.setCreatorId("1");
			user.setStatus((short) 1);
			// user.setSex(getCellFormatValue(row.getCell(12)).trim());
			// user.setEntryDate(DateUtils.parse(getCellFormatValue(row.getCell(16)).trim(),"yyyy-MM-dd"));
			user.setUserStatus(getCellFormatValue(row.getCell(17)).trim().equals("") ? "正式员工" : getCellFormatValue(row.getCell(17)).trim());
			user.setFromType((short) 0);
			user.setPwdUpdTime(new Date());
			user.setHasSyncToWx(0);
			users.add(user);

		}
		return users;
	}

	public List<UserPosition> readUserPosExcel(InputStream is, List<SysUser> users) {
		List<UserPosition> pos = new ArrayList<UserPosition>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println(colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			UserPosition userPos = new UserPosition();
			userPos.setUserPosId(UniqueIdUtil.genId());
			userPos.setOrgId(Long.parseLong(getCellFormatValue(row.getCell(1)).trim()));
			userPos.setUserId(users.get(i - 1).getUserId());
			userPos.setIsPrimary((short) 0);
			userPos.setIsCharge((short) 0);
			userPos.setIsDelete((short) 0);
			pos.add(userPos);
		}
		return pos;
	}

	public List<UserInfomation> readUserInfoExcel(InputStream is, List<SysUser> users) {
		List<UserInfomation> infos = new ArrayList<UserInfomation>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println(colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			UserInfomation info = new UserInfomation();
			info.setUserId(users.get(i - 1).getUserId());
			info.setId(UniqueIdUtil.genId());
			info.setEducation(getCellFormatValue(row.getCell(1)).trim());
			info.setGraduate_school(getCellFormatValue(row.getCell(2)).trim());
			info.setIdentification_id(getCellFormatValue(row.getCell(0)).trim());
			info.setMajor(getCellFormatValue(row.getCell(3)).trim());
			info.setPositional(getCellFormatValue(row.getCell(4)).trim());
			infos.add(info);
		}
		return infos;
	}

	public List<UserRole> readUserRoleExcel(InputStream is, List<SysUser> users) {
		List<UserRole> roles = new ArrayList<UserRole>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println(colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			UserRole role = new UserRole();
			role.setUserRoleId(UniqueIdUtil.genId());
			role.setRoleId(Long.parseLong(getCellFormatValue(row.getCell(1)).trim()));
			role.setUserId(users.get(i - 1).getUserId());
			roles.add(role);
		}
		return roles;
	}

	public List<UserUnder> readUserUnderExcel(InputStream is, String fileName) {
		List<UserUnder> userUnders = new ArrayList<UserUnder>();
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		// int colNum = headRow.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				headRow = sht.getRow(i);
				UserUnder userUnder = new UserUnder();
				userUnder.setId(UniqueIdUtil.genId());
				userUnder.setUserid(Long.parseLong(headRow.getCell(0).getStringCellValue().trim()));
				userUnder.setUnderuserid(Long.parseLong(headRow.getCell(1).getStringCellValue().trim()));
				userUnder.setUnderusername(headRow.getCell(2).getStringCellValue().trim());
				userUnders.add(userUnder);
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}
		return userUnders;
	}

	public void saveUpdateSysUserExcel(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		int cnt = 0;// 统计数据导入系统用户新增数据条数
		int unt = 0;// 统计数据导入系统用户更新数据条数
		int uinfoc = 0;// 统计用户详细信息新增条数
		List<String> logList = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				boolean isNewInfo = true;
				headRow = sht.getRow(i);
				String uno = headRow.getCell(0).getStringCellValue().trim();
				if (uno != null && uno.length() > 0) {
					UserInfomation uinfo = new UserInfomation();
					uinfo.setId(UniqueIdUtil.genId());
					SysUser sysUser = userService.getByUserAcount(uno);
					if (sysUser != null) {
						UserInfomation uinfoTemp = userInfomationService.getUserInfomationByAccount(sysUser.getAccount());
						if (uinfoTemp != null) {
							uinfo = uinfoTemp;
							isNewInfo = false;
						}
						// 更新
						uinfo.setUserId(sysUser.getUserId());
						sysUser.setFullname(headRow.getCell(1).getStringCellValue().trim());
						String sex = headRow.getCell(2).getStringCellValue().trim();
						sysUser.setSex(("男".equals(sex) ? "1" : "0"));
						uinfo.setIdentification_id(headRow.getCell(3).getStringCellValue().trim());
						uinfo.setGraduate_school(getCellValue(headRow.getCell(4).getStringCellValue()));
						uinfo.setEducation(getCellValue(headRow.getCell(5).getStringCellValue()));
						uinfo.setMajor(getCellValue(headRow.getCell(6).getStringCellValue()));
						uinfo.setGraduate_time(getCellDateValue(headRow.getCell(7).getStringCellValue()));
						uinfo.setPositional(getCellValue(headRow.getCell(8).getStringCellValue()));
						uinfo.setQQ(getCellValue(headRow.getCell(9).getStringCellValue()));
						sysUser.setEntryDate(getCellDateValue(headRow.getCell(10).getStringCellValue()));
						// 年龄？headRow.getCell(11)
						uinfo.setAddress(getCellValue(headRow.getCell(12).getStringCellValue()));
						uinfo.setHome_address(getCellValue(headRow.getCell(13).getStringCellValue()));
						uinfo.setSjdh(getCellValue(headRow.getCell(14).getStringCellValue()));
						sysUser.setResignationDate(getCellDateValue(headRow.getCell(15).getStringCellValue()));
						sysUser.setMobile(getCellValue(headRow.getCell(16).getStringCellValue()));
						uinfo.setNation(getCellValue(headRow.getCell(17).getStringCellValue()));
						uinfo.setMarriage_state(getCellValue(headRow.getCell(18).getStringCellValue()));
						uinfo.setPolitical_status(getCellValue(headRow.getCell(19).getStringCellValue()));
						sysUser.setUserStatus(getCellValue(headRow.getCell(20).getStringCellValue()));
						sysUser.setEmail(getCellValue(headRow.getCell(21).getStringCellValue()));
						// 办公电话？headRow.getCell(22)
						// 家庭电话？headRow.getCell(23)
						uinfo.setLink_address(getCellValue(headRow.getCell(23).getStringCellValue()));
						uinfo.setBirthday(getCellDateValue(headRow.getCell(24).getStringCellValue()));
						uinfo.setAddress_type(getCellValue(headRow.getCell(25).getStringCellValue()));
						// 合同开始，合同结束，劳动合同
						userService.update(sysUser);
						if (isNewInfo) {
							userInfomationService.add(uinfo);
						} else {
							userInfomationService.update(uinfo);
						}
						unt++;
					} else {
						// 插入
						logList.add(uno);
						sysUser = new SysUser();
						sysUser.setIsExpired((short) 0);
						sysUser.setIsLock((short) 0);
						sysUser.setStatus((short) 1);
						long genId = UniqueIdUtil.genId();
						sysUser.setUserId(genId);
						sysUser.setAccount(uno);
						sysUser.setPassword("jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=");
						uinfo.setUserId(sysUser.getUserId());
						sysUser.setFullname(headRow.getCell(1).getStringCellValue().trim());
						String sex = headRow.getCell(2).getStringCellValue().trim();
						sysUser.setSex(("男".equals(sex) ? "1" : "0"));
						uinfo.setIdentification_id(headRow.getCell(3).getStringCellValue().trim());
						uinfo.setGraduate_school(getCellValue(headRow.getCell(4).getStringCellValue()));
						uinfo.setEducation(getCellValue(headRow.getCell(5).getStringCellValue()));
						uinfo.setMajor(getCellValue(headRow.getCell(6).getStringCellValue()));
						// 毕业时间？headRow.getCell(7)
						uinfo.setPositional(getCellValue(headRow.getCell(8).getStringCellValue()));
						// 所在部门？headRow.getCell(9)
						sysUser.setEntryDate(getCellDateValue(headRow.getCell(10).getStringCellValue()));
						// 年龄？headRow.getCell(11)
						uinfo.setAddress(getCellValue(headRow.getCell(12).getStringCellValue()));
						uinfo.setHome_address(getCellValue(headRow.getCell(13).getStringCellValue()));
						uinfo.setSjdh(getCellValue(headRow.getCell(14).getStringCellValue()));
						sysUser.setResignationDate(getCellDateValue(headRow.getCell(15).getStringCellValue()));
						sysUser.setMobile(getCellValue(headRow.getCell(16).getStringCellValue()));
						uinfo.setNation(getCellValue(headRow.getCell(17).getStringCellValue()));
						uinfo.setMarriage_state(getCellValue(headRow.getCell(18).getStringCellValue()));
						uinfo.setPolitical_status(getCellValue(headRow.getCell(19).getStringCellValue()));
						sysUser.setUserStatus(getCellValue(headRow.getCell(20).getStringCellValue()));
						sysUser.setEmail(getCellValue(headRow.getCell(21).getStringCellValue()));
						sysUser.setPhone(getCellValue(headRow.getCell(22).getStringCellValue()));
						// 家庭电话？headRow.getCell(23)
						uinfo.setLink_address(getCellValue(headRow.getCell(23).getStringCellValue()));
						uinfo.setBirthday(getCellDateValue(headRow.getCell(24).getStringCellValue()));
						// 岗位?headRow.getCell(26)
						uinfo.setAddress_type(getCellValue(headRow.getCell(25).getStringCellValue()));
						// 合同开始，合同结束，劳动合同

						userService.add(sysUser);
						userInfomationService.add(uinfo);
						cnt++;
					}
					uinfoc++;
				} else {
					throw new RuntimeException("错误：====第" + i + "用户登录账号为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

		System.out.println("导入sys_user表新增数据条数: " + cnt);
		System.out.println("导入sys_user表更新数据条数: " + unt);
		System.out.println("导入w_user_information表数据条数: " + uinfoc);
		System.out.println("插入新用户总数： " + logList.size() + " 账号列表： " + logList);
		System.out.println("++++用户信息数据导入结束++++");
	}

	// 更新员工电话(模板：员工电话)
	public void saveUpdateSysUserPhoneExcel(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		int unt = 0;// 统计数据导入系统用户更新数据条数
		// 正文内容应该从第二行开始,第一行为表头的标题
		String tempname = "";
		for (int i = 1; i <= rowNum; i++) {
			try {
				headRow = sht.getRow(i);
				String uname = headRow.getCell(0).getStringCellValue().trim();
				tempname = uname;
				if (uname != null && uname.length() > 0) {
					UserInfomation uinfo = null;
					SysUser sysUser = userService.getByUserName(uname).get(0);
					if (sysUser != null) {
						// 更新
						uinfo = userInfomationService.getUserInfomationByAccount(sysUser.getAccount());
						// uinfo.setUserId(sysUser.getUserId());
						headRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						headRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						sysUser.setMobile(getCellValue(headRow.getCell(1).getStringCellValue()));
						uinfo.setSjdh(getCellValue(headRow.getCell(2).getStringCellValue()));
						userService.update(sysUser);
						userInfomationService.update(uinfo);
						unt++;
					} else {
						System.out.println("错误：====第" + i + "行数据解析错误!名字为：" + uname);
					}
				} else {
					throw new RuntimeException("用户名为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!" + "名字为：" + tempname + "错误原因：可能名字重名或者名字前面字符相似！");
				log.error("错误信息", e);
			} finally {
				tempname = "";
			}
		}

		System.out.println("更新w_user_information表数据条数: " + unt);
		System.out.println("++++用户信息数据导入结束++++");
	}

	// 更新员工社保号(模板：前提：没有系统不存在员工)
	public void saveUpdateSysUserSocialInsuranceExcel(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		int cnt = 0;// 统计数据导入系统用户新增数据条数
		int unt = 0;// 统计数据导入系统用户更新数据条数
		int uinfoc = 0;// 统计用户详细信息新增条数
		List<String> logList = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				boolean isNewInfo = true;
				headRow = sht.getRow(i);
				String uno = headRow.getCell(0).getStringCellValue().trim();
				if (uno != null && uno.length() > 0) {
					UserInfomation uinfo = new UserInfomation();
					uinfo.setId(UniqueIdUtil.genId());
					SysUser sysUser = userService.getByUserAcount(uno);
					if (sysUser != null) {
						// 更新
						UserInfomation uinfoTemp = userInfomationService.getUserInfomationByAccount(sysUser.getAccount());
						if (uinfoTemp != null) {
							uinfo = uinfoTemp;
							isNewInfo = false;
						}
						uinfo.setSocial_security_computer_id(getCellValue(headRow.getCell(1).getStringCellValue()));
						if (isNewInfo) {
							userInfomationService.add(uinfo);
						} else {
							userInfomationService.update(uinfo);
						}
						unt++;
					} else {
						System.out.println("错误：====第" + i + "行找不到用户!" + "uno为：" + uno);
					}
					uinfoc++;
				} else {
					throw new RuntimeException("错误：====第" + i + "用户登录账号为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

		System.out.println("导入sys_user表更新数据条数: " + unt);
		System.out.println("导入w_user_information表数据条数: " + uinfoc);
		System.out.println("++++用户信息数据导入结束++++");
	}

	// 更新员联系方式(模板：前提：没有系统不存在员工)
	public void saveUpdateSysUserLinks(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		int cnt = 0;// 统计数据导入系统用户新增数据条数
		int unt = 0;// 统计数据导入系统用户更新数据条数
		int uinfoc = 0;// 统计用户详细信息新增条数
		List<String> logList = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				boolean isNewInfo = true;
				headRow = sht.getRow(i);
				String uno = headRow.getCell(0).getStringCellValue().trim();
				if (uno != null && uno.length() > 0) {
					UserInfomation uinfo = new UserInfomation();
					uinfo.setId(UniqueIdUtil.genId());
					SysUser sysUser = userService.getByUserAcount(uno);
					if (sysUser != null) {
						// 更新
						UserInfomation uinfoTemp = userInfomationService.getUserInfomationByAccount(sysUser.getAccount());
						if (uinfoTemp != null) {
							uinfo = uinfoTemp;
							isNewInfo = false;
						}
						headRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						headRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						headRow.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						headRow.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
						sysUser.setMobile(getCellValue(headRow.getCell(2).getStringCellValue()));
						sysUser.setPhone(getCellValue(headRow.getCell(4).getStringCellValue()));
						uinfo.setSjdh(getCellValue(headRow.getCell(3).getStringCellValue()));
						uinfo.setQQ(getCellValue(headRow.getCell(5).getStringCellValue()));
						userService.update(sysUser);
						if (isNewInfo) {
							userInfomationService.add(uinfo);
						} else {
							userInfomationService.update(uinfo);
						}
						unt++;
					} else {
						System.out.println("错误：====第" + i + "行找不到用户!" + "uno为：" + uno);
					}
					uinfoc++;
				} else {
					throw new RuntimeException("错误：====第" + i + "用户登录账号为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

		System.out.println("导入sys_user表更新数据条数: " + unt);
		System.out.println("导入w_user_information表数据条数: " + uinfoc);
		System.out.println("++++用户信息数据导入结束++++");
	}

	// 更新参加工作时间(模板：前提：没有系统不存在员工)
	public void saveUpdateStartWorkTime(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		int cnt = 0;// 统计数据导入系统用户新增数据条数
		int unt = 0;// 统计数据导入系统用户更新数据条数
		int uinfoc = 0;// 统计用户详细信息新增条数
		List<String> logList = new ArrayList<>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 0; i <= rowNum; i++) {
			try {
				boolean isNewInfo = true;
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				headRow = sht.getRow(i);
				String uno = headRow.getCell(0).getStringCellValue().trim();
				if (uno != null && uno.length() > 0) {
					UserInfomation uinfo = new UserInfomation();
					uinfo.setId(UniqueIdUtil.genId());
					SysUser sysUser = userService.getByUserAcount(uno);
					if (sysUser != null) {
						// 更新
						UserInfomation uinfoTemp = userInfomationService.getUserInfomationByAccount(sysUser.getAccount());
						if (uinfoTemp != null) {
							uinfo = uinfoTemp;
							isNewInfo = false;
						}
						uinfo.setStart_work_time(HSSFDateUtil.getJavaDate(headRow.getCell(2).getNumericCellValue()));
						if (isNewInfo) {
							userInfomationService.add(uinfo);
						} else {
							userInfomationService.update(uinfo);
						}
						unt++;
					} else {
						System.out.println("错误：====第" + i + "行找不到用户!" + "uno为：" + uno);
					}
					uinfoc++;
				} else {
					throw new RuntimeException("错误：====第" + i + "用户登录账号为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

		System.out.println("导入sys_user表更新数据条数: " + unt);
		System.out.println("导入w_user_information表数据条数: " + uinfoc);
		System.out.println("++++用户信息数据导入结束++++");
	}

	public static String getCellValue(String src) {
		if (src == null || src.trim().length() <= 0) {
			return null;
		} else {
			return src.trim();
		}
	}

	public static Date getCellDateValue(String src) throws ParseException {
		if (src == null || src.trim().length() <= 0) {
			return null;
		} else {
			return new SimpleDateFormat("yyyy-MM-dd").parse(src.trim());
		}
	}

	public static void main(String[] args) {
		//saveUpdateSysUser();
		updateContractScript();
	}

	/**
	 * 导入用户信息表
	 */
	public static void saveUpdateSysUser() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/app-context.xml");// 此文
		userService = (SysUserService) context.getBean("sysUserService");
		userInfomationService = (UserInfomationService) context.getBean("userInfomationService");
		anuualLeaveService = (AnuualLeaveService) context.getBean("anuualLeaveService");
		projectService = (ProjectService) context.getBean("projectService");
		InputStream is = null;
		try {
			ExcelReader excelReader = new ExcelReader();

			/*
			 * String path="C:\\Users\\sun\\Desktop\\oa\\八月份入职员工.xlsx"; is =new FileInputStream(path); excelReader.saveUpdateSysUserExcel(is,path);
			 */

			/*
			 * String path="C:\\Users\\sun\\Desktop\\oa\\员工电话7-20.xlsx"; is =new FileInputStream(path); excelReader.saveUpdateSysUserPhoneExcel(is, path);
			 */
			/*
			 * String path="C:\\Users\\sun\\Desktop\\oa\\2017年度员工请休假登记表-剩余年假.xls"; is =new FileInputStream(path); excelReader.importAnuualVacation(is,path);
			 */
			String path = "C:\\Users\\sun\\Desktop\\oa\\2017年度员工请休假登记表(9.26).xls";
			is = new FileInputStream(path);
			excelReader.importAnuualVacation(is, path);
		} catch (Exception e) {
			System.out.println("导入失败!");
			log.error("错误信息", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("错误信息", e);
				}
			}
		}

	}

	/**
	 * 导入上下级关系数据
	 */
	public static void readUserUnder() {
		System.out.println("++++导入上下级关系数据开始++++");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/app-context.xml");// 此文
		UserUnderService userUnderService = (UserUnderService) context.getBean("userUnderService");
		InputStream is = null;
		try {
			ExcelReader excelReader = new ExcelReader();
			String path = "C:\\Users\\sun\\Desktop\\oa\\上下级关系表.xlsx";
			is = new FileInputStream(path);
			List<UserUnder> userUnders = excelReader.readUserUnderExcel(is, path);
			if (userUnders != null && userUnders.size() > 0) {
				System.out.println("读取数据总条数：" + userUnders.size());
				for (UserUnder userUnder : userUnders) {
					Integer i = userUnderService.isExistUser(userUnder);
					if (i != null && i > 0) {
						System.out.println("已存在的数据：" + userUnder.toString());
					} else {
						userUnderService.add(userUnder);
					}
				}
				System.out.println("++++导入上下级关系数据结束++++");
			} else {
				System.out.println("空数据!");
			}
		} catch (Exception e) {
			System.out.println("导入失败!");
			log.error("错误信息", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("错误信息", e);
				}
			}
		}
	}

	private void importAnuualVacation(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++用户信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		int cn = 0;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				headRow = sht.getRow(i);
				String uno = headRow.getCell(0).getStringCellValue().trim();
				if (uno != null && uno.length() > 0) {
					SysUser sysUser = userService.getByAccount(uno);
					if (sysUser == null) {
						System.out.println("错误：====第" + i + "行用户账号不存在!  用户账号为：" + uno);
					} else {
						UserInfomation infomation = userInfomationService.getUserInfomationByUid(sysUser.getUserId());
						if (infomation == null) {
							System.out.println("错误：====第" + i + "行用户基本信息未录入! 需手动录入 用户账号为：" + uno);
						} else {
							Date start_work_time = infomation.getStart_work_time();
							if (start_work_time == null) {
								try {
									start_work_time = DateUtils.parse(headRow.getCell(2).getStringCellValue().trim(), "yyyy-MM-dd");
								} catch (Exception e) {
									System.out.println("错误：====第" + i + "日期装换异常：" + uno);
								}
							}
							if (start_work_time == null) {
								System.out.println("错误：====第" + i + "行用户未录入开始工作时间! 需手动录入 用户账号为：" + uno);
							} else {
								AnuualLeave anuualLeave = anuualLeaveService.calculateAnuualVacation(sysUser.getUserId());
								if (anuualLeave == null) {
									System.out.println("错误：====第" + i + "行程序异常! 用户账号为：" + uno);
								} else {
									Double leaveCurrentyear = anuualLeave.getLeaveCurrentyear();
									double dl = headRow.getCell(3).getNumericCellValue();
									double dc = headRow.getCell(4).getNumericCellValue();
									double diff = leaveCurrentyear - dc;

									if (diff < 0) {
										System.out.println("错误：====第" + i + "行数据 异常! 今年还没有到计算年假的时候：" + uno);
									} else {
										anuualLeave.setHasleaveCurrentyear(diff);
										anuualLeave.setLeaveLastyear(dl);
										anuualLeave.setCurrentyear(year + "");
										anuualLeave.setUtime(now);
										anuualLeaveService.update(anuualLeave);
										cn++;
									}
								}
							}
						}
					}
				} else {
					throw new RuntimeException("错误：====第" + i + "用户登录账号为null");
				}
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

		System.out.println("++++成功导入" + cn + "条数据++++");
		System.out.println("++++用户信息数据导入结束++++");
	}
	
	
	
	private void importProjectInfo(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++项目信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		Date now = new Date();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			try {
				headRow = sht.getRow(i);
				String classifyName = headRow.getCell(2).getStringCellValue().trim();

				Project project = new Project();
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

	}
	
	public static void updateContractScript(){
		InputStream is = null;
		try {
			ExcelReader excelReader = new ExcelReader();
			String path = "C:\\Users\\sun\\Desktop\\oa\\合同归档需导入清单2018.4.2.xls";
			is = new FileInputStream(path);
			excelReader.updateContract(is, "合同归档需导入清单2018.4.2.xls");
		} catch (Exception e) {
			System.out.println("导入失败!");
			log.error("错误信息", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("错误信息", e);
				}
			}
		}

	}
	
	private void updateContract(InputStream is, String fileName) {
		try {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		System.out.println("++++项目信息数据导入开始++++");
		sht = wkb.getSheetAt(0);
		// 得到总行数
		int rowNum = sht.getLastRowNum();
		headRow = sht.getRow(0);
		// 正文内容应该从第二行开始,第一行为表头的标题
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		sdf.setLenient(false);
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 1; i <= rowNum; i++) {
			try {
				StringBuffer sb=new StringBuffer();
				headRow = sht.getRow(i);
				String c1 = (headRow.getCell(0)==null)?"":headRow.getCell(0).getStringCellValue();
				String c2 = (headRow.getCell(1)==null)?"":headRow.getCell(1).getStringCellValue();
				String c3 = (headRow.getCell(2)==null)?"":headRow.getCell(2).getStringCellValue();
				String c4 = (headRow.getCell(3)==null)?"":headRow.getCell(3).getStringCellValue();
				String c5 = (headRow.getCell(4)==null)?"":headRow.getCell(4).getStringCellValue();
				String c6 = (headRow.getCell(5)==null)?"":headRow.getCell(5).getStringCellValue();
				if(StringUtils.isBlank(c1)){
					System.out.println("错误：====第" + i + "行合同编号为空!");
					continue;
				}
				if(StringUtils.isBlank(c2) && StringUtils.isBlank(c3)
						&& StringUtils.isBlank(c4) && StringUtils.isBlank(c5)
						&& StringUtils.isBlank(c6)){
					System.out.println("错误：====第" + i + "行除合同编号外其他列数据为空!");
					continue;
				}
				sb.append("update w_contractinfo set ");
				if(!StringUtils.isBlank(c2)){
					c2=c2.replaceAll("\\\\", "/");
					Date parse = sdf.parse(c2);
					String format = sdf2.format(parse);
					sb.append(" F_singing_time='"+format+"',");
				}
				if(!StringUtils.isBlank(c3)){
					
					sb.append(" F_isrecovery='"+c3+"',");
				}
				if(!StringUtils.isBlank(c4)){
					
					sb.append(" F_remark='"+c4+"',");
				}
				if(!StringUtils.isBlank(c5)){
					c5=c5.replaceAll("\\\\", "/");
					Date parse = sdf.parse(c5);
					String format = sdf2.format(parse);
					sb.append(" F_start_time='"+format+"',");
				}
				if(!StringUtils.isBlank(c6)){
					c6=c6.replaceAll("\\\\", "/");
					Date parse = sdf.parse(c6);
					String format = sdf2.format(parse);
					sb.append(" F_end_time='"+format+"',");
				}
				String substring = sb.substring(0,sb.lastIndexOf(","));
				sb=new StringBuffer(substring).append(" where F_contract_num="+"'"+c1+"'").append(";");
				System.out.println(sb.toString());
			} catch (Exception e) {
				System.out.println("错误：====第" + i + "行数据解析错误!");
				log.error("错误信息", e);
			}
		}

	}
	
	/*
	 * public static void main(String[] args) {
	 * 
	 * System.out.println("=====begin====="); ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/app-context.xml");//此文 SysUserService userService = (SysUserService)
	 * context.getBean("sysUserService"); UserPositionService positionService= (UserPositionService) context.getBean("userPositionService"); UserInfomationService userInfomationService=
	 * (UserInfomationService) context.getBean("userInfomationService"); UserRoleService userRoleService =(UserRoleService) context.getBean("userRoleService");
	 * 
	 * System.out.println("=========="); try {
	 * 
	 * ExcelReader excelReader = new ExcelReader();
	 * 
	 * InputStream is2 = new FileInputStream("C:\\Users\\FH\\Desktop\\userlist.xls"); List<SysUser> users = excelReader.readUserExcel(is2); System.out.println("获得Excel表格的内容:");
	 * System.out.println(users.size()); for (SysUser user :users) { System.out.println(user.toString()); userService.add(user); }
	 * 
	 * InputStream is3 = new FileInputStream("C:\\Users\\FH\\Desktop\\userpos2.xls");
	 * 
	 * List<UserPosition> pos = excelReader.readUserPosExcel(is3,users); for (UserPosition userPos : pos) { System.out.println(userPos.toString()); positionService.add(userPos); }
	 * 
	 * 
	 * // InputStream is4 = new FileInputStream("C:\\Users\\FH\\Desktop\\info.xls"); // // List<UserInfomation> infos = excelReader.readUserInfoExcel(is4,users); // for (UserInfomation info : infos) {
	 * // System.out.println(info.toString()); // userInfomationService.add(info); // }
	 * 
	 * 
	 * 
	 * InputStream is5 = new FileInputStream("C:\\Users\\FH\\Desktop\\role.xls");
	 * 
	 * List<UserRole> roles = excelReader.readUserRoleExcel(is5,users); for (UserRole role : roles) { System.out.println(role.toString()); userRoleService.add(role); }
	 * 
	 * System.out.println("=====end=====");
	 * 
	 * } catch (FileNotFoundException e) { System.out.println("未找到指定路径的文件!"); log.error("错误信息",e); } }
	 */

}
