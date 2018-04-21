
package com.hotent.makshi.dao.hj;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hj.HjSupervisionMonthlyReport;

@Repository
public class HjSupervisionMonthlyReportDao extends WfBaseDao<HjSupervisionMonthlyReport>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjSupervisionMonthlyReport.class;
	}

}