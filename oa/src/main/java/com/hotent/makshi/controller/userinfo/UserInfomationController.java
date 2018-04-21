
package com.hotent.makshi.controller.userinfo;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryFamily;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.EntryWorkHistory;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.platform.annotion.Action;

/**
 * 对象功能:用户信息档案表 控制器类
 */
@Controller
@RequestMapping("/makshi/userinfo/userInfomation/")
public class UserInfomationController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private GroovyScriptEngine engine;

	@Resource
	private AnuualLeaveService anuualLeaveService;

	/**
	 * 添加或更新用户信息档案表。
	 * 
	 * @param request
	 * @param response
	 * @param userInfomation 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新用户信息档案表")
	public void save(HttpServletRequest request, HttpServletResponse response, UserInfomation userInfomation) throws Exception {
		String resultMsg = null;
		try {
			if (userInfomation.getId() == null) {
				userInfomationService.save(userInfomation);
				resultMsg = getText("添加", "用户信息档案表");
			} else {
				userInfomationService.save(userInfomation);
				resultMsg = getText("更新", "用户信息档案表");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得用户信息档案表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看用户信息档案表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<UserInfomation> list = userInfomationService.getAll(new QueryFilter(request, "userInfomationItem"));
		ModelAndView mv = this.getAutoView().addObject("userInfomationList", list);
		return mv;
	}

	/**
	 * 删除用户信息档案表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除用户信息档案表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			userInfomationService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除用户信息档案表及其从表成功!");
			userInfomationService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除用户信息档案表成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑用户信息档案表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑用户信息档案表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		UserInfomation userInfomation = userInfomationService.getById(id);
		if (BeanUtils.isEmpty(userInfomation)) {
			userInfomation = new UserInfomation();
			String userId_script = "return scriptImpl.getCurrentUserId();";
			userInfomation.setUserId(Long.parseLong(engine.executeObject(userId_script, null).toString()));
		}
		List<EntryFamily> entryFamilyList = userInfomationService.getEntryFamilyList(id);
		List<EntryEducationHistory> entryEducationHistoryList = userInfomationService.getEntryEducationHistoryList(id);
		List<EntryWorkHistory> entryWorkHistoryList = userInfomationService.getEntryWorkHistoryList(id);
		List<EntryProfessional> entryProfessionalList = userInfomationService.getEntryProfessionalList(id);
		List<EntryVocationQualification> entryVocationQualificationList = userInfomationService.getEntryVocationQualificationList(id);
		List<RegistrationQualification> registrationQualificationyList = userInfomationService.getRegistrationQualificationList(id);

		return getAutoView().addObject("userInfomation", userInfomation).addObject("entryFamilyList", entryFamilyList).addObject("entryEducationHistoryList", entryEducationHistoryList)
				.addObject("entryWorkHistoryList", entryWorkHistoryList).addObject("entryProfessionalList", entryProfessionalList)
				.addObject("entryVocationQualificationList", entryVocationQualificationList).addObject("registrationQualificationyList", registrationQualificationyList)
				.addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得用户信息档案表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看用户信息档案表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		UserInfomation userInfomation = userInfomationService.getById(id);
		List<EntryFamily> entryFamilyList = userInfomationService.getEntryFamilyList(id);
		List<EntryEducationHistory> entryEducationHistoryList = userInfomationService.getEntryEducationHistoryList(id);
		List<EntryWorkHistory> entryWorkHistoryList = userInfomationService.getEntryWorkHistoryList(id);
		List<EntryProfessional> entryProfessionalList = userInfomationService.getEntryProfessionalList(id);
		List<EntryVocationQualification> entryVocationQualificationList = userInfomationService.getEntryVocationQualificationList(id);
		return getAutoView().addObject("userInfomation", userInfomation).addObject("entryFamilyList", entryFamilyList).addObject("entryEducationHistoryList", entryEducationHistoryList)
				.addObject("entryWorkHistoryList", entryWorkHistoryList).addObject("entryProfessionalList", entryProfessionalList)
				.addObject("entryVocationQualificationList", entryVocationQualificationList);
	}

	/**
	 * 取年假数
	 * 
	 * @param userIds
	 * @return
	 */
	@RequestMapping("yearvacs")
	@ResponseBody
	public JSONObject getYearVacByUserIds(@RequestParam(required = true, value = "userids") String userIds) {
		JSONObject json = new JSONObject();
		json.put("code", 1);

		if (StringUtils.isEmpty(userIds)) {
			json.put("code", -1);
			return json;
		}

		if (!validIds(userIds)) {
			json.put("code", -1);
			return json;
		}

		JSONArray ja = new JSONArray();

		if (userIds.indexOf(",") > -1) {
			userIds = userIds.substring(0, userIds.length() - 1);
			String[] ids = userIds.split(",");
			try {
				for (String id : ids) {
					json.put(id, anuualLeaveService.calculateAnuualVacation(Long.parseLong(id)).getActualLeaveVacation());
				}

			} catch (ParseException e) {
				log.error("错误信息", e);
				json.put("code", -1);
				return json;
			}

		}

		return json;
	}

	/**
	 * 校验ids
	 * 
	 * @param str
	 * @return
	 */
	private static boolean validIds(String str) {
		if (str != null && !"".equals(str.trim())) {
			return str.matches("^([0-9]*,)*$");
		}
		return false;
	}

}