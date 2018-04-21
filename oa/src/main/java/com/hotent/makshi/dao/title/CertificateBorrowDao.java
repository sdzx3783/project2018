
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.title.CertificateBorrow;

@Repository
public class CertificateBorrowDao extends WfBaseDao<CertificateBorrow>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CertificateBorrow.class;
	}

}