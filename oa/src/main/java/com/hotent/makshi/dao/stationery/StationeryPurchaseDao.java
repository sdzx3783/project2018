
package com.hotent.makshi.dao.stationery;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.stationery.StationeryPurchase;

@Repository
public class StationeryPurchaseDao extends WfBaseDao<StationeryPurchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return StationeryPurchase.class;
	}

}