
package com.hotent.makshi.dao.userinfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.userinfo.EntryVocationQualification;

@Repository
public class EntryVocationQualificationDao extends BaseDao<EntryVocationQualification>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryVocationQualification.class;
	}
	
	public List<EntryVocationQualification> getByCertificateId(String certificateId) {
		return this.getBySqlKey("getByCertificateId", certificateId);
	}
	
	public List<EntryVocationQualification> getByLinkId(Long linkId) {
		return this.getBySqlKey("getByLinkId", linkId);
	}
	public int updateByLinkId(Long linkId) {
		return this.update("updateByLinkId", linkId);
	}
	
	/**
	 * 根据外键获取执业资格列表
	 * @param refId
	 * @return
	 */
	public List<EntryVocationQualification> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryVocationQualificationList", refId);
	}
	
	/**
	 * 根据外键删除执业资格
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}

	public List<EntryVocationQualification> updateStatus(String certificateId) {
		return this.getBySqlKey("updateStatus", certificateId);
	}
	
	public List<EntryVocationQualification> getByUserId(Long userId) {
		return this.getBySqlKey("getByUserId", userId);
	}

}