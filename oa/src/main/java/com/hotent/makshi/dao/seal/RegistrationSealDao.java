
package com.hotent.makshi.dao.seal;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.seal.RegistrationSeal;

@Repository
public class RegistrationSealDao extends BaseDao<RegistrationSeal>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RegistrationSeal.class;
	}
	public List<RegistrationSeal> getByCertificateId(String certificateId) {
		return this.getBySqlKey("getByCertificateId", certificateId);
	}
}