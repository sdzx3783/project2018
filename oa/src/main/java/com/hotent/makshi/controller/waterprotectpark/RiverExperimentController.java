
package com.hotent.makshi.controller.waterprotectpark;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.waterprotectpark.RiverExperiment;
import com.hotent.makshi.service.waterprotectpark.RiverExperimentService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:径流实验申请(水保示范园) 控制器类
 */
@Controller
@RequestMapping("/makshi/waterprotectpark/riverExperiment/")
public class RiverExperimentController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private RiverExperimentService riverExperimentService;
	@Resource
	private SysRoleService sysRoleService;

	/**
	 * 添加或更新径流实验申请(水保示范园)。
	 * 
	 * @param request
	 * @param response
	 * @param riverExperiment 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新径流实验申请(水保示范园)")
	public void save(HttpServletRequest request, HttpServletResponse response, RiverExperiment riverExperiment) throws Exception {
		String resultMsg = null;
		try {
			if (riverExperiment.getId() == null) {
				riverExperiment.setFrom(1);
				riverExperimentService.save(riverExperiment);
				resultMsg = getText("添加", "径流实验申请(水保示范园)");
			} else {
				riverExperimentService.save(riverExperiment);
				resultMsg = getText("更新", "径流实验申请(水保示范园)");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得径流实验申请(水保示范园)分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看径流实验申请(水保示范园)分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "riverExperimentItem");
		Map<String, Object> filters = queryFilter.getFilters();
		Object object = filters.get("endexperiTime");
		if (object != null) {
			try {
				Date endexperiTime = (Date) object;
				String formatDateS = DateUtils.formatDateS(endexperiTime);
				formatDateS += " 23:59:59";
				endexperiTime = DateUtils.parseDateL(formatDateS);
				filters.put("endexperiTime", endexperiTime);
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
		Object object1 = filters.get("endsamlpeTime");
		if (object1 != null) {
			try {
				Date endsamlpeTime = (Date) object1;
				String formatDateS = DateUtils.formatDateS(endsamlpeTime);
				formatDateS += " 23:59:59";
				endsamlpeTime = DateUtils.parseDateL(formatDateS);
				filters.put("endsamlpeTime", endsamlpeTime);
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
		List<RiverExperiment> list = riverExperimentService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("riverExperimentList", list);

		Long currentUserId = ContextUtil.getCurrentUserId();
		Boolean hasAccess = false;
		if (currentUserId == 1l) {
			hasAccess = true;
		} else {
			List<SysRole> roles = sysRoleService.getByUserId(currentUserId);
			for (SysRole sysRole : roles) {
				if ((sysRole.getRoleId() + "").equals("10000013290006")) {// 科研实验管理员——水保部
					hasAccess = true;
					break;
				}
			}
		}
		return mv.addObject("hasAccess", hasAccess);
	}

	/**
	 * 删除径流实验申请(水保示范园)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除径流实验申请(水保示范园)")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			riverExperimentService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除径流实验申请成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑径流实验申请(水保示范园)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑径流实验申请(水保示范园)")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		RiverExperiment riverExperiment = riverExperimentService.getById(id);

		return getAutoView().addObject("riverExperiment", riverExperiment).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得径流实验申请(水保示范园)明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看径流实验申请(水保示范园)明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		RiverExperiment riverExperiment = riverExperimentService.getById(id);
		return getAutoView().addObject("riverExperiment", riverExperiment);
	}

}