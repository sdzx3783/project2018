
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.PersonalSeal;

@Repository
public class PersonalSealDao extends BaseDao<PersonalSeal>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PersonalSeal.class;
	}
	
	public List<PersonalSeal> getByCertificateId(String certificateId) {
		return this.getBySqlKey("getByCertificateId", certificateId);
	}

	public List<PersonalSeal> getByLinkId(Long linkId) {
		return this.getBySqlKey("getByLinkId", linkId);
	}

	public List<PersonalSeal> getAllInfo() {
		return this.getBySqlKey("getAllInfo");
	}

}