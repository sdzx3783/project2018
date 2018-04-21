package com.hotent.makshi.controller.qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.qualification.CompanyQualificationCertificate;
import com.hotent.makshi.service.qualification.CompanyQualificationCertificateService;
import com.hotent.platform.annotion.Action;

/**
 * 对象功能:公司资质证书 控制器类
 */
@Controller
@RequestMapping("/makshi/qualification/companyQualificationCertificate/")
public class CompanyQualificationCertificateController extends BaseController {
	@Resource
	private CompanyQualificationCertificateService companyQualificationCertificateService;

	/**
	 * 添加或更新公司资质证书。
	 * 
	 * @param request
	 * @param response
	 * @param companyQualificationCertificate
	 *            添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新公司资质证书")
	public void save(HttpServletRequest request, HttpServletResponse response, CompanyQualificationCertificate companyQualificationCertificate) throws Exception {
		String resultMsg = null;
		try {
			if (companyQualificationCertificate.getId() == null) {
				companyQualificationCertificateService.save(companyQualificationCertificate);
				resultMsg = getText("添加", "公司资质证书");
			} else {
				companyQualificationCertificateService.save(companyQualificationCertificate);
				resultMsg = getText("更新", "公司资质证书");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得公司资质证书分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看公司资质证书分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<CompanyQualificationCertificate> list = companyQualificationCertificateService.getAll(new QueryFilter(request, "companyQualificationCertificateItem"));
		ModelAndView mv = this.getAutoView().addObject("companyQualificationCertificateList", list);
		return mv.addObject("Q_cno_S", request.getParameter("Q_cno_S")).addObject("ctype", request.getParameter("ctype")).addObject("Q_cname_S", request.getParameter("Q_cname_S"));
	}

	/**
	 * 删除公司资质证书
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除公司资质证书")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			companyQualificationCertificateService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除公司资质证书成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑公司资质证书
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑公司资质证书")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		CompanyQualificationCertificate companyQualificationCertificate = companyQualificationCertificateService.getById(id);

		return getAutoView().addObject("companyQualificationCertificate", companyQualificationCertificate).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得公司资质证书明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看公司资质证书明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		CompanyQualificationCertificate companyQualificationCertificate = companyQualificationCertificateService.getById(id);
		return getAutoView().addObject("companyQualificationCertificate", companyQualificationCertificate);
	}

	@RequestMapping("getCnameBytype")
	public @ResponseBody
	List<Map<String, String>> getCnameBytype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		String ctype = RequestUtil.getString(request, "ctype");
		List<CompanyQualificationCertificate> byCtype = companyQualificationCertificateService.getByCtype(ctype);
		if (byCtype != null) {
			for (CompanyQualificationCertificate companyQualificationCertificate : byCtype) {
				Map<String, String> map = new HashMap<>();
				map.put("id", companyQualificationCertificate.getId() + "");
				map.put("cname", companyQualificationCertificate.getCname());
				list.add(map);
			}
		}
		return list;
	}

}