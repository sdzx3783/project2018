
package com.hotent.makshi.dao.abandonment;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.abandonment.AssetAbandonment;

@Repository
public class AssetAbandonmentDao extends WfBaseDao<AssetAbandonment>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetAbandonment.class;
	}

}