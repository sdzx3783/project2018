
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.RiverExperiment;

@Repository
public class RiverExperimentDao extends BaseDao<RiverExperiment>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RiverExperiment.class;
	}

}