
package com.hotent.makshi.dao.userinfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.RegistrationQualification;

@Repository
public class RegistrationQualificationDao extends BaseDao<RegistrationQualification>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RegistrationQualification.class;
	}
	public List<RegistrationQualification> getByCertificateId(String certificateId) {
		return this.getBySqlKey("getByCertificateId", certificateId);
	}
	/**
	 * 根据外键获取执业资格列表
	 * @param refId
	 * @return
	 */
	public List<RegistrationQualification> getByMainId(Long refId) {
		return this.getBySqlKey("getRegistrationQualificationList", refId);
	}
	
	/**
	 * 根据外键删除执业资格
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
	
	public List<RegistrationQualification> getByLinkId(Long linkId) {
		return this.getBySqlKey("getByLinkId", linkId);
	}
	public int updateByLinkId(Long linkId) {
		return this.update("updateByLinkId", linkId);
		
	}
}