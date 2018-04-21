
package com.hotent.makshi.dao.assetrepair;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.assetrepair.AssetRepair;

@Repository
public class AssetRepairDao extends WfBaseDao<AssetRepair>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetRepair.class;
	}

}