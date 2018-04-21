
package com.hotent.makshi.controller.honor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.honor.PersonalHonor;
import com.hotent.makshi.service.honor.PersonalHonorService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.UserPositionService;

/**
 * 对象功能:个人荣誉 控制器类
 */
@Controller
@RequestMapping("/makshi/honor/personalHonor/")
public class PersonalHonorController extends BaseController {
	@Resource
	private PersonalHonorService personalHonorService;
	@Resource
	private UserPositionService userPositionService;

	/**
	 * 添加或更新个人荣誉。
	 * 
	 * @param request
	 * @param response
	 * @param personalHonor 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新个人荣誉")
	public void save(HttpServletRequest request, HttpServletResponse response, PersonalHonor personalHonor) throws Exception {
		String resultMsg = null;
		try {
			if (personalHonor.getId() == null) {
				personalHonorService.save(personalHonor);
				resultMsg = getText("添加", "个人荣誉");
			} else {
				personalHonorService.save(personalHonor);
				resultMsg = getText("更新", "个人荣誉");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得个人荣誉分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看个人荣誉分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// QueryFilter fiter = new QueryFilter(request, "personalHonorItem");
		// Boolean isEditer = false;
		// AccessExamine accessExamine =personalHonorService.examineIsEditer(isEditer,fiter,2);
		List<PersonalHonor> list = examineIsEditer(request, response);
		boolean addFlag = false;
		List<UserPosition> pos = userPositionService.getByUserId(ContextUtil.getCurrentUserId());
		if (CollectionUtils.isNotEmpty(pos)) {
			for (UserPosition userPosition : pos) {
				if (StringUtils.contains(userPosition.getPosName(), "党建工作")) {
					addFlag = true;
					break;
				}
			}
		}
		return this.getAutoView().addObject("personalHonorList", list).addObject("addFlag", addFlag);
	}

	public List<PersonalHonor> examineIsEditer(HttpServletRequest request, HttpServletResponse response) {
		// 获取当前登录用户角色,判断是否具有编辑权限
		Long userId = ContextUtil.getCurrentUserId();
		List<PersonalHonor> list = personalHonorService.getAll(new QueryFilter(request, "personalHonorItem"));
		ISysUser sys = ContextUtil.getCurrentUser();
		String sysuserId = sys.getAccount();
		Boolean is = false;
		for (PersonalHonor personal : list) {
			String user_num = personal.getUser_num();
			if (userId == 1) {
				is = true;
				personal.setIsEditers(is);
			}
			if (user_num.equals(sysuserId)) {
				is = true;
				personal.setIsEditers(is);
			}
		}

		return list;
	}

	/**
	 * 删除个人荣誉
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除个人荣誉")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			personalHonorService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除个人荣誉成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑个人荣誉
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑个人荣誉")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		PersonalHonor personalHonor = personalHonorService.getById(id);

		return getAutoView().addObject("personalHonor", personalHonor).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得个人荣誉明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看个人荣誉明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		PersonalHonor personalHonor = personalHonorService.getById(id);
		return getAutoView().addObject("personalHonor", personalHonor);
	}

}