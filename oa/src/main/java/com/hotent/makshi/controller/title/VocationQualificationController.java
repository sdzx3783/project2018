
package com.hotent.makshi.controller.title;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.qualification.PersonalQualification;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.title.VocationQualification;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.qualification.PersonalQualificationService;
import com.hotent.makshi.service.title.OccupationalRequirementsService;
import com.hotent.makshi.service.title.VocationQualificationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONObject;

/**
 * 对象功能:个人执业印章 控制器类
 */
@Controller
@RequestMapping("/makshi/title/vocationQualification/")
public class VocationQualificationController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private VocationQualificationService vocationQualificationService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private PersonalQualificationService personalQualificationService;
	@Resource
	private OccupationalRequirementsService occupationalRequirementsService;

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

	@RequestMapping("getByUserId")
	public void getByUserId(String userId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", false);
		if (StringUtils.isEmpty(userId)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		SysUser sysUser = sysUserService.getById(Long.valueOf(userId));
		if (null == sysUser) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		PersonalQualification qualification = personalQualificationService.getByAccount(sysUser.getAccount());
		if (null == qualification) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		json.put("regist_id", qualification.getRegist_id());
		json.put("seal_id", qualification.getSeal_id());
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 添加或更新从业资格信息管理。
	 * 
	 * @param request
	 * @param response
	 * @param occupationalRequirements 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新从业资格信息管理")
	public void save(HttpServletRequest request, HttpServletResponse response, PersonalQualification personalQualification) throws Exception {
		String resultMsg = null;
		try {
			if (personalQualification.getIn_date() != null) {
				personalQualification.setSwitchs(1);
			}
			if (personalQualification.getRegist_out_date() != null) {
				personalQualification.setSwitchs(0);
			}
			if (personalQualification.getId() == null) {
				if (StringUtil.isEmpty(personalQualification.getName())) {
					String getMessage = "姓名未填写";
					throw new RuntimeException(getMessage);
				}
				if (StringUtil.isEmpty(personalQualification.getCertificate_type()) && StringUtil.isEmpty(personalQualification.getOcc_type())) {
					String getMessage = "证书类型未选择";
					throw new RuntimeException(getMessage);
				}
				if (StringUtil.isEmpty(personalQualification.getCertificate_id()) && StringUtil.isEmpty(personalQualification.getOcc_certificate_id())) {
					String getMessage = "证书编号未填写";
					throw new RuntimeException(getMessage);
				}
				if (!StringUtil.isEmpty(personalQualification.getCertificate_type()) && !StringUtil.isEmpty(personalQualification.getCertificate_id())) {
					// 根据证书类型和编号判断是否已存在相同类型及编号
					List<PersonalQualification> personalQualificationRegistList = personalQualificationService.getByIdRegist(personalQualification.getCertificate_id(),
							personalQualification.getCertificate_type(), 1);
					if (personalQualificationRegistList.size() > 0) {
						// 存在该证书
						String getMessage = "存在该资格证书";
						throw new RuntimeException(getMessage);
					}
				}
				if (!StringUtil.isEmpty(personalQualification.getOcc_certificate_id()) && !StringUtil.isEmpty(personalQualification.getOcc_type())) {
					List<PersonalQualification> personalQualificationRegistList = personalQualificationService.getByIdRegist(personalQualification.getOcc_certificate_id(),
							personalQualification.getOcc_type(), 2);
					if (personalQualificationRegistList.size() > 0) {
						// 存在该证书
						String getMessage = "存在该从业证书";
						throw new RuntimeException(getMessage);
					}
				}
				SysUser sysUser = sysUserService.getByAccount(personalQualification.getAccount());
				if (null != sysUser) {
					personalQualificationService.save(personalQualification);
				} else {
					personalQualificationService.saveBinding(personalQualification);
				}
				resultMsg = getText("添加", "从业资格信息管理");
			} else {
				if (StringUtil.isEmpty(personalQualification.getName())) {
					String getMessage = "姓名未填写";
					throw new RuntimeException(getMessage);
				}
				if (StringUtil.isEmpty(personalQualification.getCertificate_type()) && StringUtil.isEmpty(personalQualification.getOcc_type())) {
					String getMessage = "证书类型未选择";
					throw new RuntimeException(getMessage);
				}
				if (StringUtil.isEmpty(personalQualification.getCertificate_id()) && StringUtil.isEmpty(personalQualification.getOcc_certificate_id())) {
					String getMessage = "证书编号未填写";
					throw new RuntimeException(getMessage);
				}
				SysUser sysUser = sysUserService.getByAccount(personalQualification.getAccount());
				if (null != sysUser) {
					personalQualificationService.save(personalQualification);
				} else {
					personalQualificationService.saveBinding(personalQualification);
				}
				resultMsg = getText("更新", "从业资格信息管理");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (RuntimeException e) {
			String getMessage = e.getMessage();
			writeResultMessage(response.getWriter(), getMessage, ResultMessage.Fail);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	private OccupationalRequirements takeOccupationalRequirements(PersonalQualification personalQualification, SysUser sysUser) {
		OccupationalRequirements occupationalRequirements = setOccupationalRequirements(personalQualification);
		occupationalRequirements.setUserId(sysUser.getUserId());
		return occupationalRequirements;
	}

	private OccupationalRequirements setOccupationalRequirements(PersonalQualification personalQualification) {
		OccupationalRequirements occupationalRequirements = new OccupationalRequirements();
		occupationalRequirements.setId(personalQualification.getOccId());
		occupationalRequirements.setOcc_type(personalQualification.getOcc_type());
		occupationalRequirements.setOcc_certificate_id(personalQualification.getOcc_certificate_id());
		occupationalRequirements.setOcc_get_date(personalQualification.getOcc_get_date());
		occupationalRequirements.setOcc_period_of_validity(personalQualification.getOcc_period_of_validity());
		occupationalRequirements.setOcc_major(personalQualification.getOcc_major());
		occupationalRequirements.setOcc_out_date(personalQualification.getOcc_out_date());
		occupationalRequirements.setOcc_send_unit(personalQualification.getOcc_send_unit());
		occupationalRequirements.setOcc_type_work(personalQualification.getOcc_type_work());
		occupationalRequirements.setOcc_degree_work(personalQualification.getOcc_degree_work());
		occupationalRequirements.setOcc_contine_edu_comple(personalQualification.getOcc_contine_edu_comple());
		occupationalRequirements.setOcc_remark(personalQualification.getOcc_remark());
		occupationalRequirements.setOcc_attachment(personalQualification.getOcc_attachment());
		occupationalRequirements.setOcc_compulsory(personalQualification.getOcc_compulsory());
		occupationalRequirements.setOcc_elective(personalQualification.getOcc_elective());
		occupationalRequirements.setOcc_period(personalQualification.getOcc_period());
		return occupationalRequirements;
	}

	/**
	 * 个人执业资格
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
		QueryFilter filter = new QueryFilter(request, "vocationQualificationoItem");
		Boolean isQlfAdmin = true;
		List<VocationQualification> list = vocationQualificationService.getOutAll(filter);
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		List<Position> positions = positionService.getByUserId(sysUser.getUserId());
		ModelAndView mv = this.getAutoView();
		if (null != positions && positions.size() > 0) {
			for (Position position : positions) {
				if ((null != position && position.getJobId().longValue() == 10000000130364l)) {// 判断是否是资质管理员
					isQlfAdmin = true;
				}
			}
		}
		if (sysUser.getUserId() == 1) {
			isQlfAdmin = true;
		}
		if (isQlfAdmin) {
			mv.addObject("vocationQualificationoList", list);
			return mv;
		}
		mv = new ModelAndView("/makshi/title/vocationQualificationListAll.jsp");
		mv.addObject("vocationQualificationoList", list);
		return mv;
	}

	/**
	 * 只有从业资格
	 * 
	 * @param occupationalRequirements
	 * @return
	 */
	public VocationQualification makeVocationQualification(OccupationalRequirements occupationalRequirements) {
		VocationQualification vocationQualification = new VocationQualification();
		vocationQualification.setOccupationalBorrower(occupationalRequirements.getBorrower());
		vocationQualification.setOccupationalCertificateId(occupationalRequirements.getOcc_certificate_id());
		vocationQualification.setOccupationalBorrowerID(occupationalRequirements.getIsBorrowed());
		vocationQualification.setOccupationalType(occupationalRequirements.getOcc_type());
		vocationQualification.setOccupationalId(occupationalRequirements.getId());
		vocationQualification.setUserName(occupationalRequirements.getUserName());
		return vocationQualification;
	}

	/**
	 * 删除个人执业资格
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delOut")
	@Action(description = "删除个人从业资格")
	public void delOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			occupationalRequirementsService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除个人从业资格成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 删除个人执业资格
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除个人从业资格")
	public void delIsBinding(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			// 从分表中删除
			personalQualificationService.delCertificates(lAryId);
			// 从总信息表中删除
			personalQualificationService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除执业证书成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败!" + ex.getMessage());

		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得用户变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getChangeHisById")
	public ModelAndView getChangeHisById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/platform/system/sysUserChangeHis.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}

	/**
	 * 取得用户变更历史
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@Action(description = "用户变更历史", execOrder = ActionExecOrder.AFTER, detail = "用户变更历史", exectype = "管理日志")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		// 查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		// 获取证书信息
		VocationQualification vocationQualification = vocationQualificationService.getById(id);
		List<WChangeHistory> changeHisList = null;
		if (vocationQualification != null) {
			changeHisList = changeHistoryService.getListByType("vocationQualification", id + "");
		}

		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType).addObject("changeHisList", changeHisList);
	}

	/**
	 * 编辑个人执业资格
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑个人执业印章")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		PersonalQualification personalQualification = null;
		String returnUrl = RequestUtil.getPrePage(request);
		if (0 != id) {
			personalQualification = personalQualificationService.getByIdTable(id);
		}
		return getAutoView().addObject("returnUrl", returnUrl).addObject("personalQualification", personalQualification);
	}

	/**
	 * 总监任命书，选择注册号
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dialogCountVocation")
	@Action(description = "个人执业印章弹出框")
	public void dialogCountVocation(Long userId, @RequestParam(required = false, value = "registNull", defaultValue = "false") Boolean registNull,
			@RequestParam(required = false, value = "sealNull", defaultValue = "false") Boolean sealNull, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", false);
		if (null == userId) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		SysUser sysUser = sysUserService.getById(Long.valueOf(userId));
		if (null == sysUser) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		List<PersonalQualification> list = personalQualificationService.getAllDialog(sysUser.getAccount(), registNull, sealNull);
		if (CollectionUtils.isEmpty(list)) {
			writeJsonAjaxResponse(response, json.toString());
			return;
		}
		json.put("result", true);
		writeJsonAjaxResponse(response, json.toString());
	}

	/**
	 * 总监任命书，选择注册号
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dialogVocation")
	@Action(description = "个人执业印章弹出框")
	public ModelAndView dialogVocation(Long userId, @RequestParam(required = false, value = "registNull", defaultValue = "false") Boolean registNull,
			@RequestParam(required = false, value = "sealNull", defaultValue = "false") Boolean sealNull, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/dialog/bpmDialogVocation.jsp");
		if (null == userId)
			return mv;
		SysUser sysUser = sysUserService.getById(Long.valueOf(userId));
		if (null == sysUser) {
			return mv;
		}
		List<PersonalQualification> list = personalQualificationService.getAllDialog(sysUser.getAccount(), registNull, sealNull);
		return mv.addObject("list", list);
	}

	/**
	 * 根据从业证书编号查找记录
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	public List<OccupationalRequirements> getOccupationalRequirementsList(HttpServletRequest request, String id) {
		QueryFilter filter = new QueryFilter(request);
		filter.addFilter("linkId", id);
		List<OccupationalRequirements> occupationalRequirementsList = occupationalRequirementsService.getAll(filter);
		return occupationalRequirementsList;
	}

	/**
	 * 转出个人执业印章
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	// @RequestMapping("change")
	// @Action(description="转出个人执业印章")
	// public void change(HttpServletRequest request, HttpServletResponse response) throws Exception
	// {
	// String preUrl= RequestUtil.getPrePage(request);
	// ResultMessage message=null;
	// try{
	// Long id=RequestUtil.getLong(request,"id");
	//
	// String returnUrl=RequestUtil.getPrePage(request);
	// PersonalSeal personalSeal=professionInfoService.getById(id);
	// personalSeal.setStatus("1");
	// professionInfoService.update(personalSeal);
	// message=new ResultMessage(ResultMessage.Success, "转出个人执业印章成功!");
	// }catch(Exception ex){
	// message=new ResultMessage(ResultMessage.Fail, "转出失败" + ex.getMessage());
	// }
	// addMessage(message, request);
	// response.sendRedirect(preUrl);
	// }

	/**
	 * 取得个人执业印章明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看个人证书明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		PersonalQualification personalQualification = personalQualificationService.getByIdTable(id);
		return getAutoView().addObject("personalQualification", personalQualification);
	}

}