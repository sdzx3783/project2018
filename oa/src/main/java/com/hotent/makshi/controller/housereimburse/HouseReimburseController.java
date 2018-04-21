
package com.hotent.makshi.controller.housereimburse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.scheduler.SchedulerService;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.housereimburse.HouseReimburse;
import com.hotent.makshi.model.housereimburse.HouseReimburseRecord;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.housereimburse.HouseReimburseRecordService;
import com.hotent.makshi.service.housereimburse.HouseReimburseService;
import com.hotent.makshi.service.renthouseregistration.RentHouseRegistrationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.service.bpm.thread.MessageUtil;

/**
 * 对象功能:租房报销记录表 控制器类
 */
@Controller
@RequestMapping("/makshi/housereimburse/houseReimburse/")
public class HouseReimburseController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private HouseReimburseService houseReimburseService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	@Resource
	private RentHouseRegistrationService rentHouseRegistrationService;
	@Resource
	private SchedulerService schedulerService;
	@Resource
	private HouseReimburseRecordService houseReimburseRecordService;

	/**
	 * 添加或更新租房报销记录表。
	 * 
	 * @param request
	 * @param response
	 * @param houseReimburse 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新租房报销记录表")
	public void save(HttpServletRequest request, HttpServletResponse response, String items, HouseReimburse houseReimburse) throws Exception {
		String resultMsg = null;
		try {

			if (houseReimburse.getId() == null) {
				houseReimburse.setId(UniqueIdUtil.genId());
				houseReimburseService.add(houseReimburse);
				resultMsg = getText("添加", "租房报销记录表");
			} else {
				houseReimburseService.update(houseReimburse);
				resultMsg = getText("更新", "租房报销记录表");
			}

			houseReimburseRecordService.saveAll(houseReimburse.getId(), items);

			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得租房报销记录表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看租房报销记录表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HouseReimburse> list = houseReimburseService.getAll(new QueryFilter(request, "houseReimburseItem"));
		ModelAndView mv = this.getAutoView().addObject("houseReimburseList", list);
		return mv;
	}

	/**
	 * 删除租房报销记录表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除租房报销记录表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			houseReimburseService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除租房报销记录表成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑租房报销记录表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑租房报销记录表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		HouseReimburse houseReimburse = houseReimburseService.getById(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("HouseReimburse", houseReimburse);
		map.put("queryType", "HouseReimburse");
		map.put("queryCondition", id + "");
		request.getSession().setAttribute("historyData", map);

		return getAutoView().addObject("houseReimburse", houseReimburse).addObject("records", houseReimburseRecordService.getALlByReimburseId(id)).addObject("returnUrl", returnUrl);

	}

	@RequestMapping("records")
	public void records(Long reimburseId, HttpServletRequest request, HttpServletResponse response) {
		List<HouseReimburseRecord> lists = houseReimburseRecordService.getALlByReimburseId(reimburseId);
		JSONObject json = new JSONObject();
		if (CollectionUtils.isEmpty(lists)) {
			json.put("result", false);
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		JSONArray arr = new JSONArray();
		for (HouseReimburseRecord h : lists) {
			JSONObject e = new JSONObject();
			e.put("reimburse_at", h.getReimburse_at());
			e.put("moneys", h.getMoneys());
			e.put("person", h.getPerson());
			arr.add(e);
		}
		json.put("items", arr);
		writeJsonAjaxResponse(response, json.toString());
	}

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
			log.error("错误信息", e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * 取得变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("historyList")
	public ModelAndView getChangeHisByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/housereimburse/houseReimburseHistoryList.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}

	/**
	 * 取得变更历史
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@Action(description = "用户变更历史", execOrder = ActionExecOrder.AFTER, detail = "用户变更历史", exectype = "管理日志")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		// 查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		// 获取基本信息
		HouseReimburse houseReimburse = houseReimburseService.getById(id);
		List<WChangeHistory> changeHisList = null;
		if (houseReimburse != null) {
			changeHisList = changeHistoryService.getListByType("HouseReimburse", id + "");
		}

		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("houseReimburse", houseReimburse).addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType)
				.addObject("changeHisList", changeHisList);
	}

	@RequestMapping("add")
	@Action(description = "编辑租房报销记录表")
	public ModelAndView add(HttpServletRequest request) throws Exception {
		return getAutoView();
	}

	/**
	 * 取得租房报销记录表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看租房报销记录表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		HouseReimburse houseReimburse = houseReimburseService.getById(id);
		return getAutoView().addObject("houseReimburse", houseReimburse);
	}

	@RequestMapping("getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String houseId = request.getParameter("houseId");
		QueryFilter filter = new QueryFilter(request, "houseReimburseItem");
		filter.addFilter("house_id", houseId);
		List<HouseReimburse> list = houseReimburseService.getAll(filter);
		if (list.size() > 0) {
			result.put("houseReimburse", list.get(0));
			result.put("state", 1);
			return result;
		}
		result.put("state", 1);
		return result;
	}

	/**
	 * 导出报销记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upload")
	@Action(description = "导出报销记录")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<HouseReimburse> list = houseReimburseService.getAll(new QueryFilter(request, "houseReimburseItem"));

		List<Object[]> dataList = new ArrayList<>();
		String[] rowName = new String[] { "序号", "租房编号", "部门名称", "承租人", "房屋地址", "租房开始日期", "租房结束日期", "房租金额", "房屋结构", "租房性质", "入住人数", "费用开始日期 ", "费用截止日期", "报销总费用" };
		// List<RentHouseRegistration> list = rentHouseRegistrationService.getAll(new QueryFilter(request, "houseReimburseItem"));
		for (HouseReimburse ls : list) {
			// 填充数据
			int count = 1;
			Object[] object = new Object[rowName.length];
			object[count++] = ls.getHouse_id();
			object[count++] = ls.getDepartment();
			object[count++] = ls.getRent_person();
			object[count++] = ls.getAddress();
			object[count++] = DateUtils.format(ls.getStart_date(), "yyyy-MM-dd");
			object[count++] = DateUtils.format(ls.getEnd_date(), "yyyy-MM-dd");
			object[count++] = ls.getRent_money();
			object[count++] = ls.getHouse_type();

			object[count++] = getRentType(ls.getRent_type());// 租房性质
			object[count++] = ls.getNumber_people();// 入住人数

			object[count++] = DateUtils.format(ls.getPay_start_date(), "yyyy-MM-dd");
			object[count++] = DateUtils.format(ls.getPay_end_date(), "yyyy-MM-dd");
			object[count++] = ls.getReimburse_money(); // 报销总费用
			dataList.add(object);
		}
		InputStream export = ExcelUtil.export(rowName, dataList, "深水水务咨询有限公司租房报销情况登记表");
		String filename = "租房记录" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
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

	private Object getRentType(String rent_type) {
		if (("1").equals(rent_type)) {
			return "办公";
		}
		if (("2").equals(rent_type)) {
			return "宿舍";
		}
		if (("3").equals(rent_type)) {
			return "办公兼宿舍";
		}
		return null;
	}

	/**
	 * 添加计划
	 * 
	 * @param response
	 * @param request
	 * @param viewName
	 * @return
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@RequestMapping("/addTrigger{viewName}")
	@Action(description = "添加定时计划", execOrder = ActionExecOrder.AFTER, detail = "<#if STEP1>进入 添加定时计划  编辑页面<#else>添加定时计划作业【${jobName}】计划【${name}】</#if>")
	public ModelAndView addTrigger(HttpServletResponse response, HttpServletRequest request, @PathVariable("viewName") String viewName) throws IOException, SchedulerException, ParseException {
		// 添加系统日志信息 -B
		try {
			SysAuditThreadLocalHolder.putParamerter("STEP1", STEP1.equals(viewName));
		} catch (Exception e) {
			log.error("错误信息", e);
			logger.error(e.getMessage());
		}

		PrintWriter out = response.getWriter();
		ModelAndView mv = new ModelAndView();
		if (STEP1.equals(viewName)) {
			String jobName = RequestUtil.getString(request, "jobName");
			mv.setViewName("/platform/scheduler/triggerAdd.jsp");
			mv.addObject("jobName", jobName);
			return mv;
		} else if (STEP2.equals(viewName)) {
			String trigName = RequestUtil.getString(request, "name");
			String jobName = RequestUtil.getString(request, "jobName");

			String planJson = RequestUtil.getString(request, "planJson");
			// 判断触发器是否存在
			boolean rtn = schedulerService.isTriggerExists(trigName);
			if (rtn) {
				ResultMessage obj = new ResultMessage(ResultMessage.Fail, "指定的计划名称已经存在!");
				out.print(obj.toString());
			}
			try {
				schedulerService.addTrigger(jobName, trigName, planJson);
				ResultMessage obj = new ResultMessage(ResultMessage.Success, "添加计划成功!");
				out.print(obj.toString());
			} catch (SchedulerException e) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "添加计划失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(e);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
			return null;
		}
		return null;
	}
}