
package com.hotent.makshi.dao.title;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.title.CertificatePerson;

@Repository
public class CertificatePersonDao extends WfBaseDao<CertificatePerson>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CertificatePerson.class;
	}

}