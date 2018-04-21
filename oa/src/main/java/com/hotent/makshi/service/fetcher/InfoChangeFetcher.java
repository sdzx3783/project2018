/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.hr.Infochange;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.hr.InfochangeService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

/**
 * 
 *个人信息变更
 */
@Component("infoChangeFetcher")
public class InfoChangeFetcher implements IFetcher {
	@Resource
	private InfochangeService infochangeService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	
	private static Logger logger=Logger.getLogger(InfoChangeFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey)  throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String message = "流程数据同步失败";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				//获取流程数据
				Infochange infochange = infochangeService.getById(Long.parseLong(businessKey));
				//获取用户信息
				SysUser sysUser = sysUserService.getByUserAcount(infochange.getAccount());
				UserInfomation userInfo = userInfomationService.getUserInfomationByUid(sysUser.getUserId());
				List<EntryEducationHistory> eduList = userInfomationService.getEntryEducationHistoryList(userInfo.getId());
				List<EntryProfessional> positionList = userInfomationService.getEntryProfessionalList(userInfo.getId());
				//判断更改类型
				String eduAfter = infochange.getEduAfter();
				String school = infochange.getSchoolAfter();
				String eduMajor = infochange.getMajorAfter();
				
				Date endDate = infochange.getEndDateAfter();
				Date startDate = infochange.getStartDateAfter();
				String deuAttachment = infochange.getEduAttachment();
				//添加学习经历并修改基本信息
				if(StringUtil.isNotEmpty(eduAfter)&&StringUtil.isNotEmpty(school)&&StringUtil.isNotEmpty(eduMajor)
					&& startDate!=null && endDate!=null){
					EntryEducationHistory edu = new EntryEducationHistory();
					WChangeHistory eduChangeHis = null;
					eduChangeHis = new WChangeHistory("infoChange","学历",infochange.getEduBefore(),eduAfter,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(eduChangeHis);
					eduChangeHis = new WChangeHistory("infoChange","专业",infochange.getMajorBefore(),eduMajor,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(eduChangeHis);
					eduChangeHis = new WChangeHistory("infoChange","毕业院校",infochange.getSchoolBefore(),school,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(eduChangeHis);
					if(null!=infochange.getStartDateBefore()){
						eduChangeHis = new WChangeHistory("infoChange","开始时间",sdf.format(infochange.getStartDateBefore()),sdf.format(startDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}else{
						eduChangeHis = new WChangeHistory("infoChange","开始时间",null,sdf.format(startDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}
					changeHistoryService.save(eduChangeHis);
					if(null!=infochange.getStartDateBefore()){
						eduChangeHis = new WChangeHistory("infoChange","结束时间",sdf.format(infochange.getEndDateBefore()),sdf.format(endDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}else{
						eduChangeHis = new WChangeHistory("infoChange","结束时间",null,sdf.format(endDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}
					changeHistoryService.save(eduChangeHis);
					edu.setEducation_school(school);
					edu.setMajor(eduMajor);
					edu.setStartDate(startDate);
					edu.setEndDate(endDate);
					edu.setAttachment(deuAttachment);
					edu.setId(sysUser.getUserId());
					eduList.add(edu);
					userInfo.setEducation(eduAfter);
					userInfo.setGraduate_school(school);
					userInfo.setMajor(eduMajor);
					userInfo.setGraduate_time(endDate);
					userInfo.setEntryEducationHistoryList(eduList);
				}
				String positionId = infochange.getPositionIdAfter();
				String positionMajor = infochange.getPositionMajorAfter();
				String positionName = infochange.getPositionNameAfter();
				Date positionGetDate = infochange.getPositionGetDateAfter();
				String positionOrganization = infochange.getOrganizationAfter();
				String positionAttachment = infochange.getPositionalAttachment();
				if(StringUtil.isNotEmpty(positionId) && StringUtil.isNotEmpty(positionName) && StringUtil.isNotEmpty(positionMajor)
						&& positionGetDate!=null){
					EntryProfessional position = new EntryProfessional();
					WChangeHistory positionChangeHis = new WChangeHistory("infoChange","职称编号",infochange.getPositionIdBefore(),positionId,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(positionChangeHis);
					 positionChangeHis = new WChangeHistory("infoChange","职称专业",infochange.getPositionMajorBofer(),positionMajor,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(positionChangeHis);
					positionChangeHis = new WChangeHistory("infoChange","职称名称",infochange.getPositonNameBefore(),positionName,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(positionChangeHis);
					if(null!=infochange.getStartDateBefore()){
						positionChangeHis = new WChangeHistory("infoChange","取得职称时间",sdf.format(infochange.getPositionGetDateBefore()),sdf.format(positionGetDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}else{
						positionChangeHis = new WChangeHistory("infoChange","取得职称时间",null,sdf.format(positionGetDate),sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					}
					changeHistoryService.save(positionChangeHis);
					positionChangeHis = new WChangeHistory("infoChange","发证机构",infochange.getOrganizationBefore(),positionOrganization,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(positionChangeHis);
					position.setMajor(positionMajor);
					position.setNum(positionId);
					position.setName(positionName);
					position.setOrganization(positionOrganization);
					position.setAchieve_time(positionGetDate);
					position.setAttachment(positionAttachment);
					positionList.add(position);
					userInfo.setPositional(positionName);
					userInfo.setPositional_major(positionMajor);
					userInfo.setEntryProfessionalList(positionList);
				}
				//户籍信息
				
				String addressType = infochange.getAddressType();
				if(StringUtil.isNotEmpty(addressType)){
					WChangeHistory changeHis = new WChangeHistory("infoChange","户籍变更",userInfo.getAddress_type(),addressType,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(changeHis);
					userInfo.setAddress_type(addressType);
				}
				//家庭成员信息
				String marrigeStatus = infochange.getMarrigeStatusAfter();
				String spouseName = infochange.getSpouseNameAfter();
				String spouseId = infochange.getSpouseIdAfter();
				if(StringUtil.isNotEmpty(spouseId)){
					WChangeHistory changeHis = new WChangeHistory("infoChange","配偶身份证",userInfo.getSpouse_identification_id(),spouseId,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(changeHis);
					userInfo.setSpouse_identification_id(spouseId);
				}
				if(StringUtil.isNotEmpty(spouseName)){
					WChangeHistory changeHis = new WChangeHistory("infoChange","配偶姓名",userInfo.getSpouse_name(),spouseName,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(changeHis);
					userInfo.setSpouse_name(spouseName);
				}
				if(StringUtil.isNotEmpty(marrigeStatus)){
					WChangeHistory changeHis = new WChangeHistory("infoChange","婚姻状况",userInfo.getMarriage_state(),marrigeStatus,sysUser.getFullname(),sysUser.getUserId(),sysUser.getUserId().toString(),new Date());
					changeHistoryService.save(changeHis);
					userInfo.setMarriage_state(marrigeStatus);
				}	
				userInfomationService.save(userInfo);
			}else{
				message="未找到流程数据";
				throw new Exception(message);
			}
		}catch (Exception e) {
			logger.error(message);
			throw new Exception(message);
		}
	}
	
	
}
