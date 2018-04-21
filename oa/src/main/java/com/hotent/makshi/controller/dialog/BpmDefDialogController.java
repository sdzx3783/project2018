package com.hotent.makshi.controller.dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.operation.CarRegist;
import com.hotent.makshi.model.operation.CarUse;
import com.hotent.makshi.service.operation.CarRegistService;
import com.hotent.makshi.service.operation.CarUseService;
import com.hotent.platform.model.bpm.AuthorizeRight;
import com.hotent.platform.model.bpm.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.service.bpm.BpmDefAuthorizeService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.system.GlobalTypeService;

@Controller
@RequestMapping("/makshi/dialog/bpmdefdialog/")
public class BpmDefDialogController extends BaseController {
	@Resource
	private BpmDefAuthorizeService bpmDefAuthorizeService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private CarUseService carUseService;
	@Resource
	private CarRegistService carRegistService;

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "bpmDefinitionItem");
		filter.getFilters().put("status", 1);// 显示已发布的流程
		Long typeId = RequestUtil.getLong(request, "typeId", 0);
		if (typeId > 1) {
			GlobalType globalType = globalTypeService.getById(typeId);
			if (globalType != null) {
				filter.getFilters().put("nodePath",
						globalType.getNodePath() + "%");
			}
		}

		// 增加流程分管授权查询判断
		Long userId = ContextUtil.getCurrentUserId();
		String isNeedRight = "";
		Map<String, AuthorizeRight> authorizeRightMap = null;
		if (!ContextUtil.isSuperAdmin()) {
			isNeedRight = "yes";
			// 获得流程分管授权与用户相关的信息
			Map<String, Object> actRightMap = bpmDefAuthorizeService
					.getActRightByUserMap(userId,
							BPMDEFAUTHORIZE_RIGHT_TYPE.MANAGEMENT, true, true);
			// 获得流程分管授权与用户相关的信息集合的流程KEY
			String actRights = (String) actRightMap.get("authorizeIds");
			filter.addFilter("actRights", actRights);
			// 获得流程分管授权与用户相关的信息集合的流程权限内容
			authorizeRightMap = (Map<String, AuthorizeRight>) actRightMap
					.get("authorizeRightMap");
		}
		filter.addFilter("isNeedRight", isNeedRight);

		// 查询流程列表
		List<BpmDefinition> list = bpmDefinitionService.getAll(filter);

		// 把前面获得的流程分管授权的权限内容设置到流程管理列表
		if (authorizeRightMap != null) {
			for (BpmDefinition bpmDefinition : list) {
				bpmDefinition.setAuthorizeRight(authorizeRightMap
						.get(bpmDefinition.getDefKey()));
			}
		} else {
			// 如果需要所有权限的就直接虚拟一个有处理权限的对象
			AuthorizeRight authorizeRight = new AuthorizeRight();
			authorizeRight.setRightByAuthorizeType("Y",
					BPMDEFAUTHORIZE_RIGHT_TYPE.MANAGEMENT);
			for (BpmDefinition bpmDefinition : list) {
				bpmDefinition.setAuthorizeRight(authorizeRight);
			}
		}
		// 流程分类
		List<GlobalType> globalTypelist = globalTypeService.getByCatKey(
				GlobalType.CAT_FLOW, false);
		ModelAndView mv = getAutoView().addObject("bpmDefinitionList", list)
				.addObject("globalTypelist", globalTypelist);
		return mv;
	}

	/**
	 * 自定义弹出框查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialogObj")
	public ModelAndView dialogObj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/makshi/dialog/bpmdefdialogDialogObj.jsp");
		QueryFilter filter = new QueryFilter(request, "bpmDefinitionItem");
		String alias = request.getParameter("alias");
		List<CarRegist> allCarList = carRegistService.getAll(filter);
		Long id = Long.valueOf(request.getParameter("id"));
		CarUse carUse = carUseService.getById(id);
		Date startTime = carUse.getStartTime();
		Date endTime = carUse.getEndTime();
		List<String> idList = new ArrayList<String>();
		List<CarRegist> unUseCarList = new ArrayList<CarRegist>();
		List<CarRegist> list = new ArrayList<CarRegist>();
		List<CarUse> uselist = carUseService.getUnuseCar(startTime, endTime);
		for (CarUse caruse : uselist) {
			if (null != caruse.getCarNameId()) {
				idList.add(caruse.getCarNameId());
			}
		}
		if (null != idList && idList.size() > 0) {
			unUseCarList = carRegistService.getUnuseCar(idList);
			for (CarRegist carRegist : unUseCarList) {
				if (!allCarList.contains(carRegist)) {
					unUseCarList.remove(carRegist);
				}
			}
			for (CarRegist carRegist : allCarList) {
				if (!unUseCarList.contains(carRegist)) {
					carRegist.setStatus("1");
					list.add(carRegist);
				}
			}
			for (CarRegist carRegist : unUseCarList) {
				carRegist.setStatus("0");
			}
			list.addAll(unUseCarList);
			mv.addObject("carRegistList", list).addObject("businessKey", id);
			return mv;
		} else {
			for (CarRegist carRegist : allCarList) {
				carRegist.setStatus("0");
			}
			return mv.addObject("carRegistList", allCarList).addObject(
					"businessKey", id);
		}
	}

	/**
	 * 自定义弹出框查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialogDefs")
	public ModelAndView dialogDefs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/makshi/dialog/bpmdefdialogDialogDefs.jsp");
		QueryFilter filter = new QueryFilter(request, "bpmDefinitionItem");
		filter.getFilters().put("status", 1);// 显示已发布的流程
		// 查询流程列表
		List<BpmDefinition> list = bpmDefinitionService.getAll(filter);
		return mv.addObject("list", list);
	}

	@RequestMapping("dialogContact")
	public ModelAndView dialogContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/platform/form/bpmFormDialogShowMobile.jsp");
		return mv;
	}

}