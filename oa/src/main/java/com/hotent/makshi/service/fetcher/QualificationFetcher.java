/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.userinfo.EntryVocationQualificationDao;
import com.hotent.makshi.dao.userinfo.RegistrationQualificationDao;
import com.hotent.makshi.model.qualification.PersonalQualification;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.qualification.PersonalQualificationService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

@Component("qualificationFetcher")
public class QualificationFetcher implements IFetcher {
	@Autowired
	private UserInfomationService userInfomationService;
	@Autowired
	private PersonalQualificationService personalQualificationService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private EntryVocationQualificationDao entryVocationQualificationDao;
	@Resource
	private RegistrationQualificationDao registrationQualificationDao;

	private static Logger logger = Logger.getLogger(QualificationFetcher.class);

	@Override
	public void fetcheData(String tableName, String businessKey) {
		int result = registAndOut(tableName, businessKey);
		String msg = "成功";
		try {
			if (1 == result || 0 == result) {
				msg = "该流程数据已删除";
				throw new Exception(msg);
			}
			if (2 == result) {
				msg = "该证书已注册或转入";
				throw new Exception(msg);
			}
		} catch (Exception e) {
			// e.getMessage();
			logger.error(msg);
			msg = "数据同步失败";
			new Exception(msg);
		}

	}

	public int registAndOut(String tableName, String businessKey) {
		logger.info("--------流程数据转移到业务表开始---------------");
		try {
			if (!StringUtil.isEmpty(businessKey)) {
				// 获取流程数据
				PersonalQualification personalQualification = personalQualificationService.getById(Long.parseLong(businessKey));
				if (null == personalQualification) {
					return 1;
				}
				// 判断流程类别,默认为注册流程
				boolean isOut = false;
				boolean isIn = false;
				if (personalQualification.getOut_date() != null && personalQualification.getOut_date().toString().length() > 0) {
					isOut = true;
				}
				if (personalQualification.getIn_date() != null && personalQualification.getIn_date().toString().length() > 0) {
					isIn = true;
				}
				String switchs = null;
				if (isIn) {
					switchs = "1";
				}
				if (isOut) {
					switchs = "0";
				}
				// 获取是否注册
				String isregist = personalQualification.getIsregist();
				// 获取证书编号编号和证书类型
				String certificateId = personalQualification.getCertificate_id();
				String certificateType = personalQualification.getCertificate_type();
				// 通过证书编号和证书类型查询是否有在册记录
				List<PersonalQualification> perList = personalQualificationService.getByIdRegist(certificateId, certificateType, 1);
				if (!isOut && perList.size() > 0) {
					// 该证书已发起过注册或转入
					return 2;
				}
				// 获取申请人信息
				SysUser sysUser = sysUserService.getByAccount(personalQualification.getAccount());
				// 外单位转出
				PersonalQualification personalQualificationRegist = null;
				if (null == sysUser && perList.size() > 0) {
					// 跟新证书信息表
					personalQualificationRegist = perList.get(0);
					personalQualificationRegist.setOut_date(personalQualification.getOut_date());
					personalQualificationRegist.setRegist_out_date(personalQualification.getRegist_out_date());
					personalQualificationService.saveBinding(personalQualificationRegist);
					return 3;
				}
				Long userId = sysUser.getUserId();
				UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
				// 获取申请人所拥有证书（资格证，注册证）
				List<EntryVocationQualification> eqList = userInfomationService.getEntryVocationQualificationList(userInfomation.getId());
				List<RegistrationQualification> rqList = userInfomationService.getRegistrationQualificationList(userInfomation.getId());
				// 创建人员档案执业资格对象
				EntryVocationQualification newEntryVocationQualification = new EntryVocationQualification();
				RegistrationQualification newRegistrationQualification = new RegistrationQualification();
				// 设置对象属性
				// 执业资格
				newEntryVocationQualification = personalQualificationService.setEntryVocatonQualification(personalQualification, newEntryVocationQualification);
				// 注册资格
				newRegistrationQualification = personalQualificationService.setRegistrationQualification(personalQualification, newRegistrationQualification);
				// 转出流程（资格证、注册证、执业印章同时转出）
				if (isOut) {
					if (perList.size() > 0) {
						// 跟新证书信息表
						personalQualificationRegist = perList.get(0);
						Long linkId = personalQualificationRegist.getId();
						personalQualificationRegist.setOut_date(personalQualification.getOut_date());
						personalQualificationRegist.setRegist_out_date(personalQualification.getRegist_out_date());
						personalQualificationService.save(personalQualificationRegist);
						personalQualificationService.addInfo(personalQualification);
						// 跟新个人信息中的证书（根据personalQualificationRegist的Id值）
						entryVocationQualificationDao.updateByLinkId(linkId);
						registrationQualificationDao.updateByLinkId(linkId);
					}
				} else {
					if (isIn) {
						// 转入流程
						try {
							if (null != newEntryVocationQualification) {
								newEntryVocationQualification.setSwitchs("1");
							}
							if (null != newRegistrationQualification) {
								newRegistrationQualification.setSwitchs("1");
							}
							personalQualification.setSwitchs(1);
							personalQualificationService.addQualificated(personalQualification, userInfomation, eqList, rqList, newEntryVocationQualification, newRegistrationQualification);
							personalQualificationService.addInfo(personalQualification);
						} catch (Exception e) {
							logger.error("错误信息", e);
						}
					} else {
						// 登记流程
						personalQualificationService.addInfo(personalQualification);
						personalQualificationService.addQualificated(personalQualification, userInfomation, eqList, rqList, newEntryVocationQualification, newRegistrationQualification);
					}
				}
			}
			return 3;
		} catch (Exception e) {
			logger.error("数据异常", e);
		}
		return 0;
	}
}
