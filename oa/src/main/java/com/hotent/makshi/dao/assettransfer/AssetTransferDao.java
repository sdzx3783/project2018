
package com.hotent.makshi.dao.assettransfer;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.assettransfer.AssetTransfer;

@Repository
public class AssetTransferDao extends WfBaseDao<AssetTransfer>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetTransfer.class;
	}

}