package com.hotent.platform.controller.system;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
//import com.hotent.makshi.model.userinfo.UserInfomation;
//import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.service.system.SysUserService;


/**
 * 对象功能:用户表 控制器类 开发公司:广州宏天软件有限公司 开发人员:csx 创建时间:2011-11-28 10:17:09
 */
@Controller
@RequestMapping("/platform/system/sysUser/")
@Action(ownermodel=SysAuditModelType.USER_MANAGEMENT)
public class SysUserFormController extends BaseFormController
{
	@Resource
	private SysUserService sysUserService;
//	@Resource
//	private UserInfomationService userInfomationService;
	@Resource
	private IdentityService identityService;
	/**
	 * 添加或更新用户表。
	 * 
	 * @param request
	 * @param response
	 * @param sysUser
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新用户表",
			detail="<#if isAdd>添加<#else>更新</#if>用户表" +
					"【${SysAuditLinkService.getSysUserLink(Long.valueOf(userId))}】")
	public void save(HttpServletRequest request, HttpServletResponse response, SysUser sysUser, BindingResult bindResult) throws Exception {

//		System.out.println(userInfomation.getMajor());
		ResultMessage resultMessage = validForm("sysUser", sysUser, bindResult,
				request);
		// add your custom validation rule here such as below code:
		// bindResult.rejectValue("name","errors.exist.student",new
		// Object[]{"jason"},"重复姓名");
		if (resultMessage.getResult() == ResultMessage.Fail){
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;

		Long selectedOrgId = RequestUtil.getLong(request, "selectedOrgId");
		boolean isOrgAdd = selectedOrgId>0;//用于判断是否是组织新增人员
		
		Long[] aryOrgId = RequestUtil.getLongAry(request, "orgId") ;// 组织Id数组
		Long[] aryChargeOrg = RequestUtil.getLongAry(request, "posIdCharge") ;// 主要负责人
		Long[] aryPosId =  RequestUtil.getLongAry(request,"posId");// 岗位Id数组
		Long posIdPrimary = RequestUtil.getLong(request, "posIdPrimary");// 主要岗位Id
		Long[] aryRoleId =RequestUtil.getLongAry(request,"roleId");// 角色Id数组
		Long[] arySuperiorId =RequestUtil.getLongAryByStr(request,"superiorId");// 角色Id数组
		Integer bySelf=RequestUtil.getInt(request,"bySelf");//是否用户自己编辑
		
		if (BeanUtils.isNotEmpty(aryOrgId)&& posIdPrimary==0){
			resultMsg = "请选择主岗位！";
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;
		}else if(isOrgAdd && !BeanUtils.isNotEmpty(aryPosId)){
			resultMsg ="请选择组织岗位！";
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;
		}

		boolean isadd=true;
		ISysUser currentUser = ContextUtil.getCurrentUser();
		if (sysUser.getUserId() == null){
			
			boolean isExist = sysUserService.isAccountExist(sysUser.getAccount());
			if(isExist){
				resultMsg = "该账号已经存在！";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			String nextId = identityService.nextId("员工编号");
			if(!nextId.equals(sysUser.getAccount())){
				resultMsg = "该账号已经存在！";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
			sysUser.setPassword(enPassword);
			sysUser.setSuperiorIds(arySuperiorId);
			
			
			sysUser.setCreator(currentUser.getFullname());
			sysUser.setCreatorId(currentUser.getUserId()+"");
			
			try {
				sysUserService.saveUser(bySelf,sysUser,aryChargeOrg,aryPosId,posIdPrimary,aryRoleId);
//				userInfomation.setUserId(sysUser.getUserId());
//				userInfomationService.save(userInfomation);
			} catch (Exception e) {
				resultMsg = "添加用户失败:"+e.getMessage();
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				e.printStackTrace();
				return;
			}
			resultMsg = "添加用户成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			
		}else{
			
			if(ContextUtil.isSuperAdmin((ISysUser) sysUser)){
				if(!ContextUtil.isSuperAdmin()){
					resultMsg = "使用限制，不能编辑超级管理员！";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return ;
				}
			}
			boolean isExist=sysUserService.isAccountExistForUpd(sysUser.getUserId(), sysUser.getAccount());
			if(isExist){
				resultMsg = "该账号已经存在！";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			sysUser.setSuperiorIds(arySuperiorId);
			sysUser.setUpdateor(currentUser.getFullname());
			sysUser.setUpdateorId(currentUser.getUserId()+"");
			sysUser.setUpdateTime(new Date());
			try {
				sysUserService.saveUser(bySelf,sysUser,aryChargeOrg,aryPosId,posIdPrimary,aryRoleId);
//				userInfomation.setUserId(sysUser.getUserId());
//				userInfomationService.save(userInfomation);
			} catch (Exception e) {
				resultMsg = "添加用户失败:"+e.getMessage();
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				e.printStackTrace();
				return;
			}
			
			resultMsg = "更新用户成功";
			isadd=false;
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Success);
		}
		SysAuditThreadLocalHolder.putParamerter("isAdd", isadd);
		SysAuditThreadLocalHolder.putParamerter("userId", sysUser.getUserId().toString());
	}
	
	/**
	 * 普通用户更新信息
	 * 
	 * @param request
	 * @param response
	 * @param sysUser
	 */
	@RequestMapping("updateCommon")
	public void updateCommon(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) throws Exception {
		String resultMsg = null;
		try {
			sysUserService.updateCommon(sysUser);
			String sjdh = sysUser.getSjdh();
			if(StringUtils.isNotEmpty(sjdh)){
//				UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(sysUser.getUserId());
//				if(userInfomation!=null){
//					userInfomation.setSjdh(sjdh);
//					userInfomationService.updateSjdh(userInfomation);
//				}
			}
			resultMsg = getText("更新", "用户信息");
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
		
	}
	
	

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected SysUser getFormObject(@RequestParam("userId") Long userId, 	Model model) throws Exception{
		logger.debug("enter SysUser getFormObject here....");
		SysUser sysUser = null;
		if (userId != null){
			sysUser = sysUserService.getById(userId);
		} 
		else{
			sysUser = new SysUser();
		}
		return sysUser;
	}

}
