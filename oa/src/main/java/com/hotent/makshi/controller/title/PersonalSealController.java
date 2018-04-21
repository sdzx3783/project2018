
package com.hotent.makshi.controller.title;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.title.PersonalSeal;
import com.hotent.makshi.service.qualification.CertificateAndBorrowService;
import com.hotent.makshi.service.title.PersonalSealService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 对象功能:个人执业印章 控制器类
 */
@Controller
@RequestMapping("/makshi/title/personalSeal/")
public class PersonalSealController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private PersonalSealService personalSealService;
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;
	@Resource
	private SysUserService sysUserService;

	protected void writeJsonAjaxResponse(HttpServletResponse response, String jsonString) {
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType("application/json; charset=UTF-8");
			writer = response.getWriter();
			writer.write(jsonString);
			writer.flush();
		} catch (Exception e) {
			log.error("错误信息",e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	@RequestMapping("getByHolderId")
	public void getByHolderId(Long userId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", false);
		PersonalSeal seal = personalSealService.getByHolderId(userId);
		if (null == seal || StringUtils.isEmpty(seal.getSeal_num()) || !StringUtils.equals(seal.getStatus(), "0")) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		json.put("no", seal.getSeal_num());
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 添加或更新个人执业印章。
	 * 
	 * @param request
	 * @param response
	 * @param personalSeal 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新个人执业印章")
	public void save(HttpServletRequest request, HttpServletResponse response, PersonalSeal personalSeal) throws Exception {
		String resultMsg = null;
		// 获取userId
		String userId = personalSeal.getHolderID();
		// 若userId为NUll,通过用户名获取userId
		String userName = personalSeal.getHolder();
		if (null == userId || userId.length() == 0) {
			List<SysUser> list = sysUserService.getByUserName(userName);
			if (list.size() > 0) {
				personalSeal.setHolderID(list.get(0).getUserId().toString());
			}
		}
		try {
			if (personalSeal.getId() == null) {
				personalSealService.save(personalSeal);
				resultMsg = getText("添加", "个人执业印章");
			} else {
				personalSealService.save(personalSeal);
				resultMsg = getText("更新", "个人执业印章");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得个人执业印章分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看个人执业印章分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<PersonalSeal> list = personalSealService.getAll(new QueryFilter(request, "personalSealItem"));
		ModelAndView mv = this.getAutoView().addObject("personalSealList", list);
		return mv;
	}

	@RequestMapping("getByUserId")
	@Action(description = "查看个人执业印章分页列表")
	public void getByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "personalSealItem");
		queryFilter.addFilter("statusValue", 0);// 未借出的证书
		List<PersonalSeal> list = personalSealService.getAll(queryFilter);
		if (null != list && list.size() > 0) {
			JSONArray obj = JSONArray.fromObject(list);
			writeResultMessage(response.getWriter(), obj.toString(), ResultMessage.Success);
		} else {
			writeResultMessage(response.getWriter(), "", ResultMessage.Fail);
		}
	}

	/**
	 * 删除个人执业印章
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除个人执业印章")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			for (int i = 0; i < lAryId.length; i++) {
				PersonalSeal personalSeal = personalSealService.getById(lAryId[i]);
				certificateAndBorrowService.updateDelete(personalSeal.getId());
				personalSealService.delById(lAryId[i]);
			}
			message = new ResultMessage(ResultMessage.Success, "删除个人执业印章成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 转出个人执业印章
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("change")
	@Action(description = "转出个人执业印章")
	public void change(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long id = RequestUtil.getLong(request, "id");
			// String returnUrl = RequestUtil.getPrePage(request);
			PersonalSeal personalSeal = personalSealService.getById(id);
			personalSeal.setStatus("1");
			personalSealService.update(personalSeal);
			message = new ResultMessage(ResultMessage.Success, "转出个人执业印章成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "转出失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑个人执业印章
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑个人执业印章")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		String returnUrl = RequestUtil.getPrePage(request);
		PersonalSeal personalSeal = personalSealService.getById(id);

		return getAutoView().addObject("personalSeal", personalSeal).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得个人执业印章明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看个人执业印章明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		PersonalSeal personalSeal = personalSealService.getById(id);
		return getAutoView().addObject("personalSeal", personalSeal);
	}

	@RequestMapping("restartSeal")
	public void restart() throws Exception {
		List<PersonalSeal> list = personalSealService.getAll();
		for (PersonalSeal personalSeal : list) {
			personalSealService.save(personalSeal);
		}
	}
}