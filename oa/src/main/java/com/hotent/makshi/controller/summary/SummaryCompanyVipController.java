/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-20，cp创建。 
 */
package com.hotent.makshi.controller.summary;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.gates.ExecutiveOrg;
import com.hotent.makshi.model.summary.SummaryCompanyVip;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.service.summary.SummaryCompanyVipService;
import com.hotent.platform.annotion.Action;

@Controller
@RequestMapping("/makshi/summary/company/")
public class SummaryCompanyVipController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SummaryCompanyVipService summaryCompanyVipService;
	@Resource
	private ExecutiveService executiveService;
	
	@RequestMapping("list")
	@Action(description = "查看测试表分页列表")
	public ModelAndView list(@RequestParam(required = false, value = "departmentID") String departmentID,HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "vipItem");
		List<ExecutiveOrg> orgs = executiveService.getOrgsBySplitLevel(3);
		List<SummaryCompanyVip> list = summaryCompanyVipService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("vipList", list).addObject("orgs", orgs).addObject("departmentID", departmentID);;
		return mv;
	}

	@RequestMapping("edit")
	@Action(description = "编辑")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		SummaryCompanyVip summaryCompanyVip = summaryCompanyVipService.getById(id);

		return getAutoView().addObject("summaryCompanyVip", summaryCompanyVip).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("detail")
	@Action(description = "明细")
	public ModelAndView detail(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		SummaryCompanyVip summaryCompanyVip = summaryCompanyVipService.getById(id);

		return getAutoView().addObject("summaryCompanyVip", summaryCompanyVip).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("save")
	@Action(description = "添加或更新")
	public void save(HttpServletRequest request, HttpServletResponse response, SummaryCompanyVip summaryCompanyVip) throws Exception {
		String resultMsg = null;
		try {
			if (summaryCompanyVip.getId() == null) {
				summaryCompanyVipService.save(summaryCompanyVip);
				resultMsg = getText("添加", "单位会员");
			} else {
				summaryCompanyVipService.save(summaryCompanyVip);
				resultMsg = getText("更新", "单位会员");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("del")
	@Action(description = "删除")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			summaryCompanyVipService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response) {
		QueryFilter queryFilter = new QueryFilter(request, "vipItem");
		List<SummaryCompanyVip> list = summaryCompanyVipService.getAll(queryFilter);
		String name = "单位会员汇总表";
		try {
			summaryCompanyVipService.export(list, name, request, response);
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}
}
