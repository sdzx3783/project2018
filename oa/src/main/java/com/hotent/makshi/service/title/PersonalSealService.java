package com.hotent.makshi.service.title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.qualification.CertificateAndBorrowDao;
import com.hotent.makshi.dao.title.PersonalSealDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;
import com.hotent.makshi.model.title.PersonalSeal;
import com.hotent.makshi.service.qualification.CertificateAndBorrowService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class PersonalSealService extends BaseService<PersonalSeal> {
	@Resource
	private PersonalSealDao dao;
	@Resource
	private CertificateAndBorrowDao certificateAndBorrowDao;
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;
	@Resource
	private SysUserService sysUserService;

	public PersonalSealService() {
	}

	@Override
	protected IEntityDao<PersonalSeal, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			PersonalSeal personalSeal = getPersonalSeal(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				personalSeal.setId(genId);
				this.add(personalSeal);
			} else {
				personalSeal.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personalSeal);
			}
			cmd.setBusinessKey(personalSeal.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取PersonalSeal对象
	 * 
	 * @param json
	 * @return
	 */
	public PersonalSeal getPersonalSeal(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonalSeal personalSeal = (PersonalSeal) JSONObject.toBean(obj, PersonalSeal.class);
		return personalSeal;
	}

	/**
	 * 保存 个人执业印章 信息
	 * 
	 * @param personalSeal
	 */

	public void save(PersonalSeal personalSeal) throws Exception {
		Long id = personalSeal.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			// 通过用户名获取userID
			String userId = personalSeal.getHolderID();
			if (userId == null || userId.length() == 0) {
				String userName = personalSeal.getHolder();
				// 通过userName查找userId
				List<SysUser> list = sysUserService.getUserIdByUserName(userName);
				// 有重名
				if (list.size() > 0) {
					personalSeal.setHolderID(list.get(0).getUserId().toString());
				}
			}
			personalSeal.setId(id);
			personalSeal.setSwitchs("1");
			if (null == personalSeal.getSeal_num() || personalSeal.getSeal_num().length() == 0) {
				return;
			}
			this.add(personalSeal);
			List<CertificateAndBorrow> list = certificateAndBorrowService.getByTypeAndCertificateId(3, personalSeal.getSeal_name(), personalSeal.getSeal_num());
			CertificateAndBorrow certificateAndBorrow = new CertificateAndBorrow();
			Long cerId = 0L;
			if (list.size() > 0) {
				certificateAndBorrow = list.get(0);
				cerId = certificateAndBorrow.getId();
			}
			certificateAndBorrow = setCertificateAndBorrow(personalSeal);
			certificateAndBorrow.setId(cerId);
			certificateAndBorrowService.save(certificateAndBorrow);
		} else {
			if (null == personalSeal.getSeal_num() || personalSeal.getSeal_num().length() == 0) {
				return;
			}
			this.update(personalSeal);
			CertificateAndBorrow certificateAndBorrow = new CertificateAndBorrow();
			/*
			 * Long linkId = personalSeal.getLinkId(); if(null!=linkId && linkId>0){ //跟新执业证书管理 }
			 */
			Long cerId = 0L;
			certificateAndBorrow = certificateAndBorrowService.getByLinkId(personalSeal.getId(), 3);
			if (null != certificateAndBorrow) {
				cerId = certificateAndBorrow.getId();
			}
			certificateAndBorrow = setCertificateAndBorrow(personalSeal);
			certificateAndBorrow.setId(cerId);
			certificateAndBorrowService.save(certificateAndBorrow);
		}
	}

	public CertificateAndBorrow setCertificateAndBorrow(PersonalSeal personalSeal) {
		CertificateAndBorrow certificateAndBorrow = new CertificateAndBorrow();
		certificateAndBorrow.setCertifateId(personalSeal.getSeal_num());
		certificateAndBorrow.setName(personalSeal.getSeal_name());
		certificateAndBorrow.setType(3);
		certificateAndBorrow.setDeleted(0);
		certificateAndBorrow.setSwitchs(Integer.valueOf(personalSeal.getSwitchs()));
		certificateAndBorrow.setBorrower(personalSeal.getBorrower());
		certificateAndBorrow.setBorrowerId(personalSeal.getBorrowerID());
		certificateAndBorrow.setEffictivedate(personalSeal.getEffictiveDate());
		certificateAndBorrow.setLinkId(personalSeal.getId());
		if (personalSeal.getHolderID() == null || personalSeal.getHolderID().length() == 0) {
			certificateAndBorrow.setUserId(null);
		} else {
			certificateAndBorrow.setUserId(Long.valueOf(personalSeal.getHolderID()));
		}
		return certificateAndBorrow;
	}

	public List<PersonalSeal> getByBorrowUid(Long uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		List<PersonalSeal> personalSealList = dao.getBySqlKey("getByBorrowUid", params);
		return personalSealList;
	}

	public PersonalSeal getBySealNum(String num) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		PersonalSeal personalSeal = dao.getUnique("getBySealNum", params);
		return personalSeal;
	}

	public PersonalSeal getBySealNumAndName(String num, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("name", name);
		List<PersonalSeal> personalSealList = dao.getBySqlKey("getBySealNumAndName", params);
		if (personalSealList.size() > 0) {
			return personalSealList.get(0);
		}
		return null;
	}

	public PersonalSeal getByLinkId(Long linkId) {
		List<PersonalSeal> personalSealList = dao.getBySqlKey("getByLinkId", linkId);
		if (personalSealList.size() > 0) {
			return personalSealList.get(0);
		}
		return null;
	}

	public PersonalSeal getByHolderId(Long holderId) {
		if (null == holderId)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("holderId", holderId);
		return (PersonalSeal) dao.getOne("getByHolderId", params);
	}
}
