
package com.hotent.makshi.dao.qualification;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.qualification.CompanyQualificationCertificate;

@Repository
public class CompanyQualificationCertificateDao extends BaseDao<CompanyQualificationCertificate>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CompanyQualificationCertificate.class;
	}

}