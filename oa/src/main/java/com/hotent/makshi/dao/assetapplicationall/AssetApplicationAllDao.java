
package com.hotent.makshi.dao.assetapplicationall;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.assetapplicationall.AssetApplicationAll;

@Repository
public class AssetApplicationAllDao extends WfBaseDao<AssetApplicationAll>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetApplicationAll.class;
	}

}