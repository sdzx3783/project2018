package com.hotent.makshi.dao.addrBook;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.addrBook.CompanyBook;
import com.hotent.makshi.model.assetapplication.AssetAll;
import com.hotent.platform.model.system.UserPosition;


@Repository
public class CompanyBookDao extends WfBaseDao<CompanyBook> {

	@Override
	public Class getEntityClass() {
		return CompanyBook.class;
	}

}
