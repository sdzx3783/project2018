
package com.hotent.makshi.dao.bidding;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.bidding.BiddingPublicity;

@Repository
public class BiddingPublicityDao extends WfBaseDao<BiddingPublicity>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BiddingPublicity.class;
	}

}