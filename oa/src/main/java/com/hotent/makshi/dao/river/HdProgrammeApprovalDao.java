
package com.hotent.makshi.dao.river;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.db.WfBaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.model.river.HdProgrammeApproval;

@Repository
public class HdProgrammeApprovalDao extends WfBaseDao<HdProgrammeApproval>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HdProgrammeApproval.class;
	}
	public  List<HdProgrammeApproval> getSelect(QueryFilter queryFilter) {
		return this.getBySqlKey("getSelect",queryFilter);
	}

}