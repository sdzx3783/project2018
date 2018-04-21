package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.engine.IScript;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.Entry;
import com.hotent.makshi.model.hr.EntryGzjl;
import com.hotent.makshi.model.hr.EntryJtcy;
import com.hotent.makshi.model.hr.EntryXxjl;
import com.hotent.makshi.model.hr.EntryZyzc;
import com.hotent.makshi.model.hr.EntryZyzg;
import com.hotent.makshi.model.hr.EntryZzzg;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryFamily;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.EntryWorkHistory;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.impl.ScriptImpl;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.system.SysUserService;



@Service
public class EntryScript  implements IScript{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private EntryService entryService;
	@Resource
	private SysUserService userService;
	@Resource
	private UserInfomationService infomationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	
	public void addEntry(DelegateTask task) throws Exception{
		Map<String, Object> variables = task.getVariables();
		ProcessCmd processCmd = TaskThreadService.getProcessCmd();
		Short voteAgree = processCmd.getVoteAgree();
		String businessKey = processCmd.getBusinessKey();
		String message ="";
		if(voteAgree!=1){
			return;
		}
		try {
			if(!StringUtil.isEmpty(businessKey)){
				Entry entry = entryService.getById(Long.parseLong(businessKey));
				if(entry!=null){
					SysUser user = userService.getById(entry.getYgbh());
					if(user !=null){
						user.setFullname(entry.getXm());
						user.setSex(entry.getXb());
						user.setEmail(entry.getGryx());
						user.setWeixinid(entry.getWx());
						user.setMobile(entry.getSjhm());
						user.setPhone(entry.getJtdh());
						user.setUserStatus("实习");
						String photoJson = entry.getZp();
						if(!StringUtil.isEmpty(photoJson)){
							JSONObject jsStr = JSONObject.parseObject(photoJson);
							JSONArray jsonObject = jsStr.getJSONArray("jsonArry");
							String fileId="";
							for(Object obj :jsonObject){
								JSONObject json=JSONObject.parseObject(obj.toString());
								fileId=json.getString("id");
							}
							String picture = "/platform/system/sysFile/getFileById.ht?fileId="+fileId;
							user.setPicture(picture);
						}
						
						UserInfomation userInfomationByUid = infomationService.getUserInfomationByUid(entry.getYgbh());
						UserInfomation infomation = new UserInfomation(userInfomationByUid.getId(),entry.getYgbh(),entry.getCsrq(),entry.getHyzk(),entry.getCym(),entry.getMz()
								,entry.getJg(),entry.getZczy(),entry.getWhcd(),entry.getCjgzsj(),entry.getByyx(),entry.getZzmm(),entry.getZy()
								,entry.getSfzhm(),entry.getZc(),entry.getHj(),entry.getSfycrbs(),entry.getSfyycbs(),entry.getShbxdnh(),entry.getLs()
								,entry.getTzah(),entry.getHjszd(),entry.getPoxm(),entry.getFmjzd(),entry.getPosfzhm(),entry.getPojzd(),entry.getTxdz()
								,entry.getSjdh(),entry.getJjlxr(),entry.getJxkh(),entry.getJjlxrdh(),entry.getQQhm(),entry.getWx());
						
					
						UserInfomation temp = infomationService.getUserInfomationByUid(entry.getYgbh());
						
						//获取工作经历
						List<EntryGzjl> entryGzjlList = entryService.getEntryGzjlList(entry.getId());
						if(entryGzjlList!=null && entryGzjlList.size()>0){
							List<EntryWorkHistory> workList = new ArrayList<EntryWorkHistory>();
							for (EntryGzjl gzjl  : entryGzjlList) {
								EntryWorkHistory workHis = new EntryWorkHistory(temp.getId(), gzjl.getQzsj(),gzjl.getEndDate(), gzjl.getGzdw(), gzjl.getBmgw(), gzjl.getJszw(), flowToEntityService.flowToEntityFile(gzjl.getFj()));
								workList.add(workHis);
							}
							infomation.setEntryWorkHistoryList(workList);
						}
						//获取家庭成员
						List<EntryJtcy> entryJtcyList = entryService.getEntryJtcyList(entry.getId());
						if(entryJtcyList!=null && entryJtcyList.size()>0){
							List<EntryFamily> familyList = new ArrayList<EntryFamily>();
							for (EntryJtcy jtcy  : entryJtcyList) {
								EntryFamily family = new EntryFamily(temp.getId(), jtcy.getGx(), jtcy.getXm(), jtcy.getXb(), jtcy.getCsn(), jtcy.getCsny(), jtcy.getLxdh(), flowToEntityService.flowToEntityFile(jtcy.getFj()));
								familyList.add(family);
							}
							infomation.setEntryFamilyList(familyList);
						}
						//获取学习经历
						List<EntryXxjl> entryXxjlList = entryService.getEntryXxjlList(entry.getId());
						if(entryXxjlList!=null && entryXxjlList.size()>0){
							List<EntryEducationHistory> eduList = new ArrayList<EntryEducationHistory>();
							Date lastEndDate  =entryXxjlList.get(0).getEndDate();
							for (EntryXxjl xxjl  : entryXxjlList) {
								EntryEducationHistory edu = new EntryEducationHistory(temp.getId(),xxjl.getQzsj(),xxjl.getEndDate(),xxjl.getJdxxhjg(),xxjl.getZy(),xxjl.getShzs(),flowToEntityService.flowToEntityFile(xxjl.getFj()));
								Date temDate = edu.getEndDate();
								if(temDate.getTime()>lastEndDate.getTime()){
									lastEndDate = temDate;
								}
								eduList.add(edu);
							}
							//设置毕业时间
							infomation.setGraduate_time(lastEndDate);
							infomation.setEntryEducationHistoryList(eduList);
						}
						//获取专业职称
						List<EntryZyzc> entryZyzcList = entryService.getEntryZyzcList(entry.getId());
						if(entryZyzcList!=null && entryZyzcList.size()>0){
							List<EntryProfessional> professionalList = new ArrayList<EntryProfessional>();
							for (EntryZyzc zyzc  : entryZyzcList) {
								EntryProfessional professional = new EntryProfessional(temp.getId(),zyzc.getZcbh(), zyzc.getZcmc(),zyzc.getFzjg(),zyzc.getZczy(),zyzc.getQdzcsj(),flowToEntityService.flowToEntityFile(zyzc.getFj()));
								professionalList.add(professional);
							}
							infomation.setEntryProfessionalList(professionalList);
						}
						//获取执业资格
						List<EntryZyzg> entryZyzgList = entryService.getEntryZyzgList(entry.getId());
						if(entryZyzgList!=null && entryZyzgList.size()>0){
							List<EntryVocationQualification> vocationQualificationList = new ArrayList<EntryVocationQualification>();
							for (EntryZyzg zyzg  : entryZyzgList) {
								EntryVocationQualification vocationQualification = new EntryVocationQualification(temp.getId(),zyzg.getZyzcbh(),zyzg.getZyzgmc(),zyzg.getFzjg(),zyzg.getZyzgzzy(),zyzg.getQdzssj(),flowToEntityService.flowToEntityFile(zyzg.getFj()),zyzg.getSfzrbgs(),"1");
								vocationQualificationList.add(vocationQualification);
							}
							infomation.setEntryVocationQualificationList(vocationQualificationList);
						}
						//获取注册资格
						List<EntryZzzg> entryZzgList = entryService.getEntryZzzgList(entry.getId());
						if(entryZzgList!=null && entryZzgList.size()>0){
							List<RegistrationQualification> registrationQualificationList = new ArrayList<RegistrationQualification>();
							for (EntryZzzg zzzg  : entryZzgList) {
								RegistrationQualification registrationQualification = new RegistrationQualification();
								registrationQualification.setCertificate_regist_id(zzzg.getCertificate_regist_id());
								registrationQualification.setCreateBy(zzzg.getCreateBy());
								registrationQualification.setCreatetime(zzzg.getCreatetime());
								registrationQualification.setIsregist(zzzg.getIsregist());
								registrationQualification.setGet_date(zzzg.getGet_date());
								registrationQualification.setLast_effectice_date(zzzg.getLast_effectice_date());
								registrationQualification.setRegist_major(zzzg.getRegist_major());
								registrationQualification.setRegist_attachment(zzzg.getRegist_attachment());
								registrationQualification.setRegist_send_unit(zzzg.getRegist_send_unit());
								registrationQualificationList.add(registrationQualification);
							}
							infomation.setRegistrationQualificationList(registrationQualificationList);
						}
						userService.update(user);
						infomationService.save(infomation);
				}else{
					message = "用户不存在";
					throw new Exception(message);
				}
			}
		}else{
			message = "该流程数据已删除";
			throw new Exception(message);
		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw  new Exception(message);
		}
	}

}
