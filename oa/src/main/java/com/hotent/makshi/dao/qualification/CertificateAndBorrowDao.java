
package com.hotent.makshi.dao.qualification;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;

@Repository
public class CertificateAndBorrowDao extends BaseDao<CertificateAndBorrow>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CertificateAndBorrow.class;
	}

}