
package com.hotent.makshi.dao.assetapplication;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.assetapplication.AssetAll;

@Repository
public class AssetAllDao extends WfBaseDao<AssetAll>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetAll.class;
	}

}