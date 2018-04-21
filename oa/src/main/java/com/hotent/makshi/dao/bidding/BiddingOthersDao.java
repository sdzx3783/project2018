
package com.hotent.makshi.dao.bidding;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.bidding.BiddingOthers;

@Repository
public class BiddingOthersDao extends WfBaseDao<BiddingOthers>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BiddingOthers.class;
	}

}