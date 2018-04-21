
package com.hotent.makshi.dao.river;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.db.WfBaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.model.river.HdOvertimeApplications;

@Repository
public class HdOvertimeApplicationsDao extends WfBaseDao<HdOvertimeApplications>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HdOvertimeApplications.class;
	}
	public  List<HdOvertimeApplications> getUser(QueryFilter queryFilter) {
		return this.getBySqlKey("getUser",queryFilter);
	}

}