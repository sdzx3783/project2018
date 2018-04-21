package com.hotent.makshi.controller.operation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.IPosition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.operation.OverAndAdjust;
import com.hotent.makshi.model.operation.OverTime;
import com.hotent.makshi.service.operation.OverAndAdjustService;
import com.hotent.makshi.service.operation.OverTimeAndAdjustService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.SysCalendar;
import com.hotent.platform.service.bpm.impl.ScriptImpl;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.worktime.CalendarSettingService;
import com.hotent.platform.service.worktime.SysCalendarService;

import net.sf.json.JSONObject;

/**
 * 对象功能:车辆登记 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/overTimeAndAdjust/")
public class OverTimeAndAdjustController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	public OverTimeAndAdjustService overTimeAndAdjustService;
	@Resource
	public OverAndAdjustService overAndAdjustService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysCalendarService sysCalendarService;
	@Resource
	private CalendarSettingService calendarSettingService;

	/**
	 * 取得加班调休分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看车辆登记分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApplicationContext context = AppUtil.getContext();
		ScriptImpl script = (ScriptImpl) context.getBean(com.hotent.platform.service.bpm.impl.ScriptImpl.class);
		QueryFilter fiter = new QueryFilter(request, "overTimeItem");
		String alias = "bpm_bmlly";
		Boolean flag = false;
		Boolean department = false;
		// Boolean grop = false;
		Long orgId = 0L;
		Long userId = ContextUtil.getCurrentUserId();
		List<String> userIdList = new ArrayList<String>();
		List<String> orgIdList = new ArrayList<String>();
		if (userId == 1) {
			flag = true;
			orgId = (Long) request.getSession().getAttribute("chooseOrgId");
			orgIdList = getIncludeOrg(orgId);
			userIdList = getIncludeUserId(orgIdList);
			fiter.addFilter("userId", userIdList);
		}
		// 判断角色是否具有编辑权限
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		for (SysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				flag = true;
			}
		}
		// 获取所在组织
		List<SysOrg> orgList = sysOrgDao.getOrgsByUserId(userId);
		if (orgList.size() > 0) {
			if (flag) {
				Long type = 10L;
				for (SysOrg sysOrg : orgList) {
					if (sysOrg.getOrgType() < type) {
						type = sysOrg.getOrgType();
						orgId = sysOrg.getOrgId();
					}
				}
			} else {
				orgId = orgList.get(0).getOrgId();
			}
			// 判断是否为部门负责人
			department = script.isDepartmentLeaderByUserId(userId.toString());
			// 判断是否为小组负责人
			// grop = script.isGropLeader(userId.toString());
			orgIdList = getIncludeOrg(orgId);
			userIdList.add(userId.toString());
			if (flag || department) {
				flag = true;
				userIdList = getIncludeUserId(orgIdList);
				fiter.addFilter("userId", userIdList);
			}
			if (!flag) {
				fiter.addFilter("userId", userIdList);
			}
		}
		List<OverTime> list = overTimeAndAdjustService.getAll(fiter);
		ModelAndView mv = this.getAutoView().addObject("overTimeList", list);
		return mv;
	}

	public List<String> getIncludeOrg(Long orgId) {
		List<String> list = new ArrayList<String>();
		list.add(orgId.toString());
		List<SysOrg> orgList = sysOrgDao.getAllOrgIdByOrgId(orgId);
		if (orgList.size() > 0) {
			for (SysOrg sysOrg : orgList) {
				list.add(sysOrg.getOrgId().toString());
			}
		}
		return list;
	}

	public List<String> getIncludeUserId(List<String> orgIdList) {
		List<String> userIdlist = new ArrayList<String>();
		if (orgIdList.size() > 0) {
			for (String orgId : orgIdList) {
				List<SysUser> list = sysUserDao.getByOrgId(Long.valueOf(orgId));
				for (SysUser sysUser : list) {
					userIdlist.add(sysUser.getUserId().toString());
				}

			}
		}
		return userIdlist;
	}

	/**
	 * 清空加班调休信息
	 */
	public void cleanInfo() {
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int month = date.getMonth();
		@SuppressWarnings("deprecation")
		int day = date.getDay();
		if (month == 3 && day == 1) {
			overTimeAndAdjustService.cleanInfo();
			overAndAdjustService.cleanInfo();
		}

	}

	/**
	 * 删除车辆登记
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除车辆登记")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			// carRegistService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除车辆登记成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得加班调休明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		List<String> userIdList = new ArrayList<String>();
		QueryFilter fiter = new QueryFilter(request);
		userIdList.add(id.toString());
		fiter.addFilter("userId", userIdList);
		List<OverTime> list = overTimeAndAdjustService.getAll(fiter);
		List<OverAndAdjust> overList = overAndAdjustService.getOverListByUsrId(id);
		List<OverAndAdjust> adjustList = overAndAdjustService.getAdjustListByUsrId(id);
		return getAutoView().addObject("overList", overList).addObject("adjustList", adjustList).addObject("overTimeList", list);
	}

	/**
	 * 历史数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("past")
	public ModelAndView past(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		List<OverTime> list = overTimeAndAdjustService.getPast(id);
		return getAutoView().addObject("overTimeList", list);
	}

	/*
	 * @RequestMapping("overTimeAppGet") public ModelAndView overTimeAppGet(HttpServletRequest request, HttpServletResponse response) throws Exception { ModelAndView mv = new
	 * ModelAndView("/makshi/operation/adjustApp.jsp"); String businessKey = request.getParameter("businessKey"); //判断是否为项目负责人或部门负责人 ApplicationContext context = AppUtil.getContext(); ScriptImpl
	 * script =(ScriptImpl) context.getBean(com.hotent.platform.service.bpm.impl.ScriptImpl.class); ISysUser user = ContextUtil.getCurrentUser(); Long userId = user.getUserId(); ISysOrg org =
	 * ContextUtil.getCurrentOrg(); String orgName = ""; IPosition pos = ContextUtil.getCurrentPos(); orgName = org.getOrgName(); Integer isProjectLeader = 0; Integer isGropLeader = 0; boolean
	 * gropLeader = script.isGropLeader(userId.toString()); boolean departmentLeader = script.isDepartmentLeaderByUserId(userId.toString()); if(gropLeader){ isGropLeader = 1; }
	 * if((pos.getPosName()).equals(orgName+"_项目负责人")){ isProjectLeader = 1; } boolean flag = false; Long id = 0L; if(!StringUtil.isEmpty(businessKey)){ flag = true; id = Long.valueOf(businessKey); }
	 * Adjust adjust = new Adjust(); if(flag){ adjust = adjustService.getById(id); } mv.addObject("adjust", adjust ).addObject("isProjectLeader",isProjectLeader ).addObject("isGropLeader",isGropLeader
	 * ).addObject("departmentLeader",departmentLeader); return mv; }
	 */

	@RequestMapping("overTimeApp")
	@ResponseBody
	public Map<String, Object> overTimeApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		/*
		 * String type = request.getParameter("type"); String businessKey = request.getParameter("businessKey"); boolean flag = false; Long id = 0L; if(!StringUtil.isEmpty(businessKey)){ flag = true;
		 * id = Long.valueOf(businessKey); } ModelAndView mv = new ModelAndView("/makshi/operation/overTimeApp.jsp"); if(type!=null&&Integer.valueOf(type)==2){ mv = new
		 * ModelAndView("/makshi/operation/adjustApp.jsp"); Adjust adjust = new Adjust(); if(flag){ adjust = adjustService.getById(id); } mv.addObject("adjust", adjust ); }
		 */
		ApplicationContext context = AppUtil.getContext();
		ScriptImpl script = (ScriptImpl) context.getBean(com.hotent.platform.service.bpm.impl.ScriptImpl.class);
		ISysUser user = ContextUtil.getCurrentUser();
		String userName = user.getFullname();
		Long userId = user.getUserId();
		ISysOrg org = ContextUtil.getCurrentOrg();
		String orgName = "";
		if (userId != 1L) {
			IPosition pos = ContextUtil.getCurrentPos();
			orgName = org.getOrgName();
			Integer isProjectLeader = 0;
			Integer isGropLeader = 0;
			boolean gropLeader = script.isGropLeader(userId.toString());
			if (gropLeader) {
				isGropLeader = 1;
			}
			/*
			 * if((pos.getPosName()).equals(orgName+"_项目负责人")){ isProjectLeader = 1; }
			 */
			result.put("isProjectLeader", isProjectLeader);
			result.put("isGropLeader", isGropLeader);
			result.put("userName", userName);
			result.put("userId", userId);
			return result;
		}
		result.put("isProjectLeader", 1);
		result.put("isGropLeader", 1);
		return result;
	}

	@RequestMapping("overTimeGet")
	public ModelAndView overTimeGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/operation/overTimeGet.jsp");
		return mv;
	}

	@RequestMapping("overTimeInfoList")
	@ResponseBody
	public Integer overTimeInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer isExist = 0;
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		if (null == start || ("").equals(start) || end == null || ("").equals(end)) {
			isExist = 1;
			return isExist;
		}
		Integer startPoint = Integer.valueOf(request.getParameter("startPoint"));
		Integer endPoint = Integer.valueOf(request.getParameter("endPoint"));
		if (startPoint == 1) {
			start = start + " 12:00:01";
		}
		if (endPoint == 1) {
			end = end + " 12:00:01";
		}
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.getFilters().clear();
		queryFilter.addFilter("userId", sysUser.getUserId());
		queryFilter.addFilter("start", start);
		queryFilter.addFilter("end", end);
		// 值班列表
		List<OverAndAdjust> overList = overAndAdjustService.getAll(queryFilter);
		// 调休列表
		List<OverAndAdjust> adjustList = overAndAdjustService.getAllAdjustByDate(start, end, sysUser.getUserId());

		// 申请时间段内有加班记录
		if (overList != null && overList.size() > 0) {
			isExist = 2;
		}

		// 申请时间段内有调休记录
		if (adjustList != null && adjustList.size() > 0) {
			isExist = 3;
		}
		return isExist;
	}

	@RequestMapping("getDays")
	@ResponseBody
	public Double getDays(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer type = Integer.valueOf(request.getParameter("type"));
		Double result = 0.0;
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		Date startDate = DateUtils.parseDateS(start);
		Date endDate = DateUtils.parseDateS(end);
		Integer startPoint = Integer.parseInt(request.getParameter("startPoint"));
		Integer endPoint = Integer.parseInt(request.getParameter("endPoint"));
		Integer includeFront = 0;
		Integer includeLater = 0;
		if (type == 1) {
			includeFront = Integer.parseInt(request.getParameter("front"));
			includeLater = Integer.parseInt(request.getParameter("later"));
		}
		int day = 0;
		if (type == 1) {
			day = DateUtils.getDiffDaysByDay(startDate, endDate);
		}
		if (type == 2) {
			// day = DateUtils.getWorkDay(startDate, endDate);
			day = getWorkDays(startDate, endDate) - 1;
		}
		result = result + day;
		if ((endPoint - startPoint) < 0) {
			// result = result+(-1);
		}
		if ((endPoint - startPoint) == 0) {
			result = result + 0.5;
		}
		if ((endPoint - startPoint) > 0) {
			result = result + 1;
		}
		if (type == 1) {
			if (includeFront == 1) {
				result = result + 0.5;
			}
			if (includeLater == 1) {
				result = result + 0.5;
			}

		}
		return result;
	}

	public int getWorkDays(Date startDate, Date endDate) {
		int countDay = 0;
		try {
			// 判断两个日期相差的时间
			int day = DateUtils.getDiffDaysByDay(startDate, endDate);
			// 获取日历空间设置的日期
			SysCalendar calendar = sysCalendarService.getDefaultCalendar();
			List<CalendarSetting> calSetlist = calendarSettingService.getCalByIdCalDay(calendar.getId(), DateUtils.format(startDate, "yyyy-MM-dd"), DateUtils.format(endDate, "yyyy-MM-dd"));
			if (null == calSetlist || calSetlist.size() == 0) {
				// 未设置工作日历
				return -1;
			}
			Map<String, CalendarSetting> calendarSetMap = getCalDayMap(calSetlist);
			for (int i = 0; i <= day; i++) {
				String curDate = DateUtils.transDate(DateUtils.getDateAddDay(DateUtils.format(startDate, "yyyyMMdd"), i));
				if (calendarSetMap.containsKey(curDate)) {
					countDay += 1;
				}
			}
			return countDay;
		} catch (Exception e) {
			log.error("错误信息", e);
		}
		return countDay;
	}

	public Map<String, CalendarSetting> getCalDayMap(List<CalendarSetting> calSetlist) {
		if (null != calSetlist && calSetlist.size() > 0) {
			Map<String, CalendarSetting> rstMap = new HashMap<String, CalendarSetting>();
			for (CalendarSetting cal : calSetlist) {
				if (null != cal.getCalDay()) {
					rstMap.put(cal.getCalDay(), cal);
				}
			}
			return rstMap;
		} else {
			return null;
		}
	}

	@RequestMapping("leftDays")
	@ResponseBody
	public JSONObject leftDays(HttpServletRequest request, String id) {
		JSONObject json = new JSONObject();
		json.put("left", 0);
		if (StringUtils.isEmpty(id))
			return json;
		List<String> userIdList = new ArrayList<String>();
		QueryFilter fiter = new QueryFilter(request);
		userIdList.add(id);
		fiter.addFilter("userId", userIdList);
		List<OverTime> list = overTimeAndAdjustService.getAll(fiter);
		if (CollectionUtils.isEmpty(list))
			return json;
		json.put("left", list.get(0).getLeftAdjustDays());
		return json;
	}

	/**
	 * 导出值班调休信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upload")
	@Action(description = "导出信息")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> dataList = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		String[] rowName = new String[] { "序号", "姓名", "值班总天数", "调休总天数", "剩余未调休天数" };
		Long orgId = 0L;
		List<String> userIdList = new ArrayList<String>();
		List<String> orgIdList = new ArrayList<String>();
		orgId = (Long) request.getSession().getAttribute("chooseOrgId");
		orgIdList = getIncludeOrg(orgId);
		userIdList = getIncludeUserId(orgIdList);
		params.put("userIdList", userIdList);
		List<OverTime> list = overTimeAndAdjustService.getAllInfo(params);
		for (OverTime ls : list) {
			// 填充数据
			int count = 1;
			Object[] object = new Object[rowName.length];
			object[count++] = ls.getName();
			object[count++] = ls.getOverTimeDays();
			object[count++] = ls.getAdjustDays();
			object[count++] = ls.getLeftAdjustDays();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "值班调休信息表");
		String filename = "值班调休信息表" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		BufferedInputStream bis = new BufferedInputStream(export);
		// 输出流
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
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