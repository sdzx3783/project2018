
package com.hotent.makshi.dao.hj;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.hj.HjTechnicalReportExamin;

@Repository
public class HjTechnicalReportExaminDao extends WfBaseDao<HjTechnicalReportExamin>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjTechnicalReportExamin.class;
	}

}