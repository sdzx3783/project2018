
package com.hotent.makshi.dao.hj;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hj.HjAnnualReport;

@Repository
public class HjAnnualReportDao extends WfBaseDao<HjAnnualReport>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjAnnualReport.class;
	}

}