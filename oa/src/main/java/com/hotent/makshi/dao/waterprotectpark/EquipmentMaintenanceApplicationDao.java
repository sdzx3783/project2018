
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.EquipmentMaintenanceApplication;

@Repository
public class EquipmentMaintenanceApplicationDao extends BaseDao<EquipmentMaintenanceApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EquipmentMaintenanceApplication.class;
	}

}