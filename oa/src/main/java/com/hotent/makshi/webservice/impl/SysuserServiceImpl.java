package com.hotent.makshi.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.model.vacation.AnuualLeave;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.webservice.api.SysuserWebService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;
import com.hotent.platform.service.system.UserRoleService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.ReturnCode;

public class SysuserServiceImpl implements SysuserWebService {
	private Logger logger = LogManager.getLogger(SysuserServiceImpl.class);
	
	private final String defaultUserImage = "commons/image/default_image_male.jpg";
	@Resource
	private DemensionService demensionService;
	@Context
	private HttpServletRequest request;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private AnuualLeaveService anuualLeaveService;
	
	@Override
	public String getuserinfo(String account) {
		try{
			BaseRuelt result = new BaseRuelt();
			//获取用户个人信息
			SysUser user=sysUserService.getByAccount(account);
			String pictureLoad = defaultUserImage;
			if (user == null) {
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND, "用户不存在！");
			}
			if (StringUtil.isEmpty(user.getPicture())) {
				pictureLoad=user.getPicture();
			}
	//		//获取所属组织角色
	//		Map<SysOrg, List<SysRole>> roles=getOrgRoles(user.getUserId());
			//获取用户角色
//			List<UserRole> roleList = userRoleService.getByUserId(user.getUserId());
			//获取用户所属岗位
			List<UserPosition> posList = userPositionService.getByUserId(user.getUserId());
			List<Map<String, Object>> poslist = new ArrayList<>();
			for(UserPosition userPosition:posList){
				Map<String, Object> map = new HashMap<>();
				map.put("posName",userPosition.getPosName());
				map.put("isPrimary",userPosition.getIsPrimary());
				map.put("posId",userPosition.getPosId());
				poslist.add(map);
			}
			//获取所属组织
			List<UserPosition> userPosList = userPositionService.getOrgListByUserId(user.getUserId());
			List<Map<String, Object>> userpostList = new ArrayList<>();
			for(UserPosition userPosition:userPosList){
				Map<String, Object> map = new HashMap<>();
				map.put("orgName",userPosition.getOrgName());
				map.put("isPrimary",userPosition.getIsPrimary());
				String orgpathname = userPosition.getOrgPathName();
				if(StringUtils.isNotEmpty(orgpathname)&&orgpathname.indexOf("/深水咨询/")>-1){
					orgpathname=orgpathname.replace("/深水咨询/", "");
				}else{
					orgpathname=orgpathname.replaceFirst("/", "");
				}	
				map.put("orgPathName",orgpathname);
				map.put("orgId",userPosition.getOrgId());
				map.put("orgPath",userPosition.getOrgPath());
				userpostList.add(map);
			}
	//		SysOrg sysorg= sysOrgService.getPrimaryOrgByUserId(user.getUserId());
			//计算年假 
			AnuualLeave anuualLeave;
			UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(user.getUserId());
			userInfomation.setYearVacation(0.0d);
			anuualLeave = anuualLeaveService.calculateAnuualVacation(user.getUserId());
			userInfomation.setYearVacation(anuualLeave.getActualLeaveVacation());
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserId());
			map.put("account", user.getAccount());
			map.put("resignationDate",user.getResignationDate()==null?"":DateUtils.formatDateS(user.getResignationDate()));
			map.put("formalDate",user.getFormalDate()==null?"":DateUtils.formatDateS(user.getFormalDate()));
			map.put("entryDate",user.getEntryDate()==null?"":DateUtils.formatDateS(user.getEntryDate()));
			map.put("fullname", user.getFullname());
			map.put("nation", userInfomation.getNation());
			map.put("identification_id", userInfomation.getIdentification_id());
            map.put("laborcontstarttime", user.getLaborcontstarttime()==null?"":user.getLaborcontstarttime());
            map.put("laborcontendtime", user.getLaborcontendtime()==null?"":user.getLaborcontendtime());
            map.put("education", userInfomation.getEducation());
            map.put("major", userInfomation.getMajor());
            map.put("positional_major", userInfomation.getPositional_major());
            map.put("positional", userInfomation.getPositional());
			map.put("mobile", user.getMobile());
			map.put("phone", user.getPhone());
			map.put("sjdh", userInfomation.getSjdh());
			map.put("email", user.getEmail());
			map.put("weixinid",user.getWeixinid());
			map.put("emergency_link_person",userInfomation.getEmergency_link_person());
			map.put("emergency_link_person_phone",userInfomation.getEmergency_link_person_phone());
			map.put("picture", pictureLoad);
			map.put("sex", StringUtils.isEmpty(user.getSex())?"":user.getSex().equals("1")?"男":"女");
			map.put("userStatus", user.getUserStatus());
			map.put("yearVacation", userInfomation.getYearVacation());
			map.put("startWorkTime", userInfomation.getStart_work_time()==null?"":DateUtils.formatDateS(userInfomation.getStart_work_time()));
			map.put("marriageState", userInfomation.getMarriage_state());	
			map.put("posList", poslist);
			map.put("userPosList", userpostList);
			result.setResultMap(map, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		}catch(ApiExcetpion e){
			throw e;
		}catch (Exception e) {
			logger.error("错误信息",e);
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}
	
	@Override
	public String updateUserInfomation(String account){
		try {
			BaseRuelt result = new BaseRuelt();
			SysUser user=sysUserService.getByAccount(account);
			if(user==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND, "用户不存在！account="+account);
			}
			String email = request.getParameter("email");
			String mobile = request.getParameter("mobile");
			String weixinid = request.getParameter("weixinid");
			String sjdh = request.getParameter("sjdh");
			String emergency_link_person= request.getParameter("emergency_link_person");
			String emergency_link_person_phone= request.getParameter("emergency_link_person_phone");
			if (StringUtils.isNotBlank(email)) 
				user.setEmail(email);
			if (StringUtils.isNotBlank(mobile)) 
				user.setMobile(mobile);
			if (StringUtils.isNotBlank(weixinid)) 
				user.setWeixinid(weixinid);
			UserInfomation userInfomation = userInfomationService.getUserInfomationByAccount(account);
			if (userInfomation != null) {
				if (StringUtils.isNotBlank(sjdh)) 
					userInfomation.setSjdh(sjdh);
				if(StringUtils.isNotBlank(emergency_link_person))
					userInfomation.setEmergency_link_person(emergency_link_person);
				if(StringUtils.isNotBlank(emergency_link_person_phone))
					userInfomation.setEmergency_link_person_phone(emergency_link_person_phone);
			}
			userInfomationService.updateSysuserAndUserInfomation(user,userInfomation);
			result.setResultMap("SUCCESS", ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		}catch(ApiExcetpion e){
			logger.error("错误信息",e);
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			logger.error("错误信息",e);
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}
	
	@Override
	public String updatePwd(String account){
		BaseRuelt result = new BaseRuelt();
		try {
			SysUser user=sysUserService.getByAccount(account);
			if(user==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在  account="+account);
			}
			String primitivePassword = RequestUtil.getString(request, "primitivePassword");
			String enPassword = EncryptUtil.encryptSha256(primitivePassword);
			String newPassword = RequestUtil.getString(request, "newPassword");
			SysUser sysUser = sysUserService.getById(user.getUserId());
			String password = sysUser.getPassword();
			if (StringUtil.isEmpty(newPassword) || StringUtil.isEmpty(primitivePassword)) {
				throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL, "输入的密码不能为空!");
			} else if (!enPassword.equals(password)) {
				throw new ApiExcetpion(ReturnCode.PRAMS_ERROR, "您输入的原始密码不正确!");
			} else if (primitivePassword.equals(newPassword)) {
				throw new ApiExcetpion(ReturnCode.PRAMS_ERROR, "您修改的密码和原始密码相同!");
			}
			sysUserService.updPwd(user.getUserId(), newPassword);
			result.setResultMap("SUCCESS", ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (ApiExcetpion e) {
			logger.error("错误信息",e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}
	
}
