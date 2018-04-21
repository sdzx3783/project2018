
package com.hotent.makshi.dao.assetregistration;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;


import com.hotent.makshi.model.assetregistration.AssetRegistration;

@Repository
public class AssetRegistrationDao extends BaseDao<AssetRegistration>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetRegistration.class;
	}

}