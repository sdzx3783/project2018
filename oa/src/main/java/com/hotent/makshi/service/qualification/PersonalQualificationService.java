package com.hotent.makshi.service.qualification;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.qualification.PersonalQualificationDao;
import com.hotent.makshi.dao.title.OccupationalRequirementsDao;
import com.hotent.makshi.dao.userinfo.EntryVocationQualificationDao;
import com.hotent.makshi.dao.userinfo.RegistrationQualificationDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;
import com.hotent.makshi.model.qualification.PersonalQualification;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.title.OccupationalRequirementsService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

@Service
public class PersonalQualificationService extends BaseService<PersonalQualification> {
	@Resource
	private PersonalQualificationDao dao;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private EntryVocationQualificationDao entryVocationQualificationDao;
	@Resource
	private RegistrationQualificationDao registrationQualificationDao;
	@Resource
	private OccupationalRequirementsDao occupationalRequirementsDao;
	@Resource
	private OccupationalRequirementsService occupationalRequirementsService;
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;

	public PersonalQualificationService() {
	}

	@Override
	protected IEntityDao<PersonalQualification, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 保存 个人执业资格 信息
	 * 
	 * @param personalQualification
	 */

	public void save(PersonalQualification personalQualification) throws Exception {
		Long id = personalQualification.getId();
		Date outDate = personalQualification.getRegist_out_date();
		Boolean flag = outDate != null ? true : false;
		if (personalQualification.getIn_date() != null) {
			personalQualification.setSwitchs(1);
		}
		if (flag) {
			personalQualification.setSwitchs(0);
		}
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			personalQualification.setId(id);
			changeInfo(personalQualification);
			this.add(personalQualification);
			addInfo(personalQualification);
		} else {
			changeInfo(personalQualification);
			this.update(personalQualification);
			addInfo(personalQualification);
		}
	}

	public void changeInfo(PersonalQualification personalQualification) throws Exception {
		// 是否转出
		Date outDate = personalQualification.getRegist_out_date();
		// 是否转入
		Date inDate = personalQualification.getIn_date();
		Boolean isOut = outDate != null ? true : false;
		Boolean isIn = inDate != null ? true : false;
		String switchs = null;
		if (isIn) {
			switchs = "1";
		}
		if (isOut) {
			switchs = "0";
		}
		// 获取申请人信息
		SysUser sysUser = sysUserService.getByAccount(personalQualification.getAccount());
		Long userId = sysUser.getUserId();
		personalQualification.setUserId(userId);
		UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
		if (userInfomation == null) {
			return;
		}
		// 获取申请人所拥有证书（资格证，注册证）
		List<EntryVocationQualification> eqList = userInfomationService.getEntryVocationQualificationList(userInfomation.getId());
		List<RegistrationQualification> rqList = userInfomationService.getRegistrationQualificationList(userInfomation.getId());
		// 创建人员档案执业资格对象
		EntryVocationQualification entryVocationQualification = new EntryVocationQualification();
		RegistrationQualification registrationQualification = new RegistrationQualification();
		OccupationalRequirements occupationalRequirements = new OccupationalRequirements();
		// 获取证书编号
		Long linkId = personalQualification.getId();
		List<EntryVocationQualification> entryVocationQualificationList = entryVocationQualificationDao.getByLinkId(linkId);
		List<RegistrationQualification> registrationQualificationList = registrationQualificationDao.getByLinkId(linkId);
		List<OccupationalRequirements> occupationalRequirementsList = occupationalRequirementsDao.getByLinkId(linkId);
		if (entryVocationQualificationList.size() > 0) {
			entryVocationQualification = setNewEntryVocationQualification(personalQualification, entryVocationQualificationList);
			if (null != entryVocationQualification) {
				entryVocationQualification.setSwitchs(switchs);
				entryVocationQualificationDao.update(entryVocationQualification);
			}
		} else {
			if (null != setEntryVocatonQualification(personalQualification, entryVocationQualification)) {
				entryVocationQualification.setSwitchs(switchs);
				eqList.add(setEntryVocatonQualification(personalQualification, entryVocationQualification));
			}
			userInfomation.setEntryVocationQualificationList(eqList);
		}
		if (registrationQualificationList.size() > 0) {
			registrationQualification = setNewRegistrationQualification(personalQualification, registrationQualificationList);
			if (null != registrationQualification) {
				registrationQualification.setSwitchs(switchs);
				registrationQualificationDao.update(registrationQualification);
			}
		} else {
			if (null != setRegistrationQualification(personalQualification, registrationQualification)) {
				registrationQualification.setSwitchs(switchs);
				rqList.add(setRegistrationQualification(personalQualification, registrationQualification));
			}
			userInfomation.setRegistrationQualificationList(rqList);
		}
		// 跟新个人资料
		userInfomationService.save(userInfomation);
		// 跟新从业证
		if (occupationalRequirementsList.size() > 0) {
			occupationalRequirements = setNewOccupationalRequirements(personalQualification, occupationalRequirementsList);
			occupationalRequirementsDao.update(occupationalRequirements);
		} else {
			occupationalRequirements = setOccupationalRequirements(personalQualification, occupationalRequirements);
			if (null != occupationalRequirements) {
				occupationalRequirementsService.save(occupationalRequirements);
			}
		}
		// 跟新借阅表

	}

	public void saveBinding(PersonalQualification personalQualification) throws Exception {
		Long id = personalQualification.getId();
		// 资格注册印章转出日期
		Date outDate = personalQualification.getRegist_out_date();
		Boolean flag = outDate != null ? true : false;
		// 从业证转出日期
		// Date occOutDate = personalQualification.getOcc_out_date();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			personalQualification.setId(id);
			// 设置为挂靠状态
			personalQualification.setIsBinding(1);
			personalQualification.setSwitchs(1);
			this.add(personalQualification);
			addInfo(personalQualification);
		} else {
			if (flag) {
				personalQualification.setSwitchs(0);
			}
			this.update(personalQualification);
			// 证书
			addInfo(personalQualification);
		}
	}

	public void addInfo(PersonalQualification personalQualification) throws Exception {
		String certificateId = personalQualification.getCertificate_id();
		String registficateId = personalQualification.getCertificate_regist_id();
		String occId = personalQualification.getOcc_certificate_id();
		String sealId = personalQualification.getSeal_id();
		if (!StringUtil.isEmpty(certificateId)) {
			certificateAndBorrowService.save(setCerBor(personalQualification, 1, certificateId));
		}
		if (!StringUtil.isEmpty(registficateId)) {
			certificateAndBorrowService.save(setCerBor(personalQualification, 2, registficateId));
		}
		if (!StringUtil.isEmpty(sealId)) {
			certificateAndBorrowService.save(setCerBor(personalQualification, 3, sealId));
		}
		if (!StringUtil.isEmpty(occId)) {
			certificateAndBorrowService.save(setCerBor(personalQualification, 4, occId));
		}
	}

	public CertificateAndBorrow setCerBor(PersonalQualification personalQualification, Integer num, String certificateId) {
		Long linkId = personalQualification.getId();
		Integer switchs = personalQualification.getSwitchs();
		Long userId = personalQualification.getUserId();
		CertificateAndBorrow certificateAndBorrow = certificateAndBorrowService.getByLinkId(linkId, num);
		if (null == certificateAndBorrow) {
			certificateAndBorrow = new CertificateAndBorrow();
		}
		certificateAndBorrow.setCertifateId(certificateId);
		if (num == 4) {
			certificateAndBorrow.setName(personalQualification.getOcc_type());
		} else {
			certificateAndBorrow.setName(personalQualification.getCertificate_type());
		}
		certificateAndBorrow.setUserId(userId);
		certificateAndBorrow.setType(num);
		certificateAndBorrow.setSwitchs(switchs);
		certificateAndBorrow.setEffictivedate(personalQualification.getEffictiveDate());
		certificateAndBorrow.setLinkId(personalQualification.getId());
		return certificateAndBorrow;
	}

	public List<PersonalQualification> getByIdRegist(String certificateId, String certificateType, Integer type) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (certificateId != null && certificateId.length() > 0) {
			param.put("certificateId", certificateId);
		}
		if (certificateType != null && certificateType.length() > 0) {
			param.put("certificateType", certificateType);
		}
		if (type == 1) {
			return dao.getBySqlKey("getAllPersonal", param);
		} else {
			return dao.getBySqlKey("getAllOccPersonal", param);
		}
	}

	public PersonalQualification getByIdTable(Long id) {
		List<PersonalQualification> list = dao.getBySqlKey("getByIdTable", id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void addQualificated(PersonalQualification personalQualification, UserInfomation userInfomation, List<EntryVocationQualification> eqList, List<RegistrationQualification> rqList,
			EntryVocationQualification newEntryVocationQualification, RegistrationQualification newRegistrationQualification) throws Exception {
		newEntryVocationQualification.setStatus("1");
		if (null != newRegistrationQualification) {
			rqList.add(newRegistrationQualification);
		}
		if (null != newEntryVocationQualification) {
			eqList.add(newEntryVocationQualification);
		}
		userInfomation.setRegistrationQualificationList(rqList);
		userInfomation.setEntryVocationQualificationList(eqList);
		userInfomationService.save(userInfomation);
		// personalSealService.save(newPersonalSeal);
		this.add(personalQualification);
	}

	private OccupationalRequirements setNewOccupationalRequirements(PersonalQualification personalQualification, List<OccupationalRequirements> occupationalRequirementsList) {
		OccupationalRequirements occupationalRequirements = occupationalRequirementsList.get(0);
		occupationalRequirements = setOccupationalRequirements(personalQualification, occupationalRequirements);
		return occupationalRequirements;
	}

	private OccupationalRequirements setOccupationalRequirements(PersonalQualification personalQualification, OccupationalRequirements occupationalRequirements) {
		occupationalRequirements.setUserId(personalQualification.getUserId());
		occupationalRequirements.setLinkId(personalQualification.getId());
		occupationalRequirements.setOcc_attachment(personalQualification.getOcc_attachment());
		if (personalQualification.getOcc_certificate_id() == null || personalQualification.getOcc_certificate_id().length() == 0) {
			return new OccupationalRequirements();
		}
		occupationalRequirements.setOcc_certificate_id(personalQualification.getOcc_certificate_id());
		occupationalRequirements.setOcc_compulsory(personalQualification.getOcc_compulsory());
		occupationalRequirements.setOcc_contine_edu_comple(personalQualification.getOcc_contine_edu_comple());
		occupationalRequirements.setOcc_degree_work(personalQualification.getOcc_degree_work());
		occupationalRequirements.setOcc_elective(personalQualification.getOcc_elective());
		occupationalRequirements.setOcc_get_date(personalQualification.getOcc_get_date());
		occupationalRequirements.setOcc_major(personalQualification.getOcc_major());
		occupationalRequirements.setOcc_out_date(personalQualification.getOcc_out_date());
		occupationalRequirements.setOcc_in_date(personalQualification.getOcc_in_date());
		occupationalRequirements.setOcc_period(personalQualification.getOcc_period());
		occupationalRequirements.setOcc_period_of_validity(personalQualification.getOcc_period_of_validity());
		occupationalRequirements.setOcc_remark(personalQualification.getOcc_remark());
		occupationalRequirements.setOcc_send_unit(personalQualification.getOcc_send_unit());
		occupationalRequirements.setOcc_type(personalQualification.getOcc_type());
		occupationalRequirements.setOcc_type_work(personalQualification.getOcc_type());
		occupationalRequirements.setOcc_secondMajor(personalQualification.getOcc_secondMajor());
		return occupationalRequirements;
	}

	public RegistrationQualification setNewRegistrationQualification(PersonalQualification personalQualification, List<RegistrationQualification> registrationQualificationList) {
		RegistrationQualification registrationQualification = registrationQualificationList.get(0);
		registrationQualification = setRegistrationQualification(personalQualification, registrationQualification);
		return registrationQualification;
	}

	public RegistrationQualification setRegistrationQualification(PersonalQualification personalQualification, RegistrationQualification newRegistrationQualification) {
		if (null == personalQualification.getCertificate_regist_id() || personalQualification.getCertificate_regist_id().length() == 0) {
			return null;
		}
		newRegistrationQualification.setCertificate_regist_id(personalQualification.getCertificate_regist_id());
		newRegistrationQualification.setGet_date(personalQualification.getGet_date());
		newRegistrationQualification.setIsregist(personalQualification.getIsregist());
		newRegistrationQualification.setLast_effectice_date(personalQualification.getLast_effectice_date());
		newRegistrationQualification.setRegist_major(personalQualification.getRegist_major());
		newRegistrationQualification.setRegist_attachment(personalQualification.getRegist_attachment());
		newRegistrationQualification.setRegist_send_unit(personalQualification.getRegist_send_unit());
		newRegistrationQualification.setUserId(personalQualification.getUserId());
		newRegistrationQualification.setLinkId(personalQualification.getId());
		newRegistrationQualification.setRegist_secondMajor(personalQualification.getRegist_secondMajor());
		newRegistrationQualification.setRegist_thirdMajor(personalQualification.getRegist_thirdMajor());
		return newRegistrationQualification;
	}

	public EntryVocationQualification setNewEntryVocationQualification(PersonalQualification personalQualification, List<EntryVocationQualification> entryVocationQualificationList) {
		EntryVocationQualification entryVocationQualification = entryVocationQualificationList.get(0);
		entryVocationQualification = setEntryVocatonQualification(personalQualification, entryVocationQualification);
		return entryVocationQualification;
	}

	public EntryVocationQualification setEntryVocatonQualification(PersonalQualification personalQualification, EntryVocationQualification newEntryVocationQualification) {
		if (null == personalQualification.getCertificate_id() || personalQualification.getCertificate_id().length() == 0) {
			return null;
		}
		newEntryVocationQualification.setNum(personalQualification.getCertificate_id());
		newEntryVocationQualification.setName(personalQualification.getCertificate_type());
		newEntryVocationQualification.setOrganization(personalQualification.getSend_unit());
		newEntryVocationQualification.setMajor(personalQualification.getCertificate_major());
		newEntryVocationQualification.setAchieve_time(personalQualification.getCertificate_date());
		newEntryVocationQualification.setAttachment(personalQualification.getAttachment());
		newEntryVocationQualification.setUserId(personalQualification.getUserId());
		newEntryVocationQualification.setLinkId(personalQualification.getId());
		return newEntryVocationQualification;
	}

	public void delCertificates(Long[] lAryId) {
		for (int i = 0; i < lAryId.length; i++) {
			Long id = lAryId[i];
			PersonalQualification personalQualification = getByIdTable(id);
			;
			// 删除资格证
			if (personalQualification.getAccount() != null) {
				entryVocationQualificationDao.delBySqlKey("delByLinkId", id);
				registrationQualificationDao.delBySqlKey("delByLinkId", id);
				// personalSealDao.delBySqlKey("delByLinkId", id);
				occupationalRequirementsDao.delBySqlKey("delByLinkId", id);
				// 从借阅表中删除
				certificateAndBorrowService.updateInfoDelete(personalQualification.getId());
			}
		}
	}

	public void completeInfo(SysUser user, String idNumber) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", user.getAccount());
		params.put("name", user.getFullname());
		params.put("sex", user.getSex());
		params.put("userId", (user.getUserId()).toString());
		params.put("nation", user.getEmail());
		params.put("education", user.getMobile());
		params.put("school", user.getPhone());
		params.put("major", user.getWeixinid());
		params.put("idNumber", idNumber);
		dao.update("completeInfo", params);
	}

	public void backInfo(String idNumber) {
		dao.update("backInfo", idNumber);
	}

	public List<PersonalQualification> getAllInfo() {
		return dao.getBySqlKey("getAllInfo");
	}

	public List<PersonalQualification> getByName(String name) {
		return dao.getBySqlKey("getByName", name);
	}

	public List<PersonalQualification> getAllInfoSeal() {
		return dao.getBySqlKey("getBySeal");
	}

	public List<PersonalQualification> getAllRegist() {
		return dao.getBySqlKey("getAllRegist");
	}

	public void changeInfoTest(PersonalQualification personalQualification) throws Exception {
		Long userId = personalQualification.getUserId();
		if (userId == null) {
			return;
		}
		UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
		// 获取申请人所拥有证书（资格证，注册证）
		List<EntryVocationQualification> eqList = userInfomationService.getEntryVocationQualificationList(userInfomation.getId());
		List<RegistrationQualification> rqList = userInfomationService.getRegistrationQualificationList(userInfomation.getId());
		// 创建人员档案执业资格对象
		EntryVocationQualification entryVocationQualification = new EntryVocationQualification();
		RegistrationQualification registrationQualification = new RegistrationQualification();
		if (null != setEntryVocatonQualification(personalQualification, entryVocationQualification)) {
			eqList.add(setEntryVocatonQualification(personalQualification, entryVocationQualification));
		}
		userInfomation.setEntryVocationQualificationList(eqList);
		if (null != setRegistrationQualification(personalQualification, registrationQualification)) {
			rqList.add(setRegistrationQualification(personalQualification, registrationQualification));
		}
		userInfomation.setRegistrationQualificationList(rqList);
		// 跟新资格表和注册表
		userInfomationService.save(userInfomation);
	}

	public List<PersonalQualification> getOccByInfo(Map<String, Object> param) {
		return dao.getBySqlKey("getOccByInfo", param);
	}

	public List<PersonalQualification> getByLinkId(Long linkId) {
		return dao.getBySqlKey("getByLinkId", linkId);
	}

	public PersonalQualification getByAccount(String account) {
		if (StringUtils.isEmpty(account))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		return (PersonalQualification) dao.getOne("getByAccount", params);
	}

	public List<PersonalQualification> getAllDialog(String account, boolean registNull, boolean sealNull) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("registNull", registNull);
		params.put("sealNull", sealNull);
		return dao.getBySqlKey("getAllDialog", params);
	}
}
