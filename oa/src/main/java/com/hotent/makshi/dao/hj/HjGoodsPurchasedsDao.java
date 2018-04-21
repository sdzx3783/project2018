
package com.hotent.makshi.dao.hj;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.db.WfBaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;

@Repository
public  class HjGoodsPurchasedsDao extends WfBaseDao<HjGoodsPurchaseds>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjGoodsPurchaseds.class;
	}
	public  List<HjGoodsPurchaseds> getSelectByRecord(QueryFilter queryFilter) {
		return this.getBySqlKey("SelectByRecord",queryFilter);
	}
	public  HjGoodsPurchaseds getSelectByWPID(Long id) {
		return this.getUnique("SelectByWPID",id);
	}

		
	

}