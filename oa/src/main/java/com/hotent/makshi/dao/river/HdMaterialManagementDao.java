
package com.hotent.makshi.dao.river;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.river.HdMaterialManagement;

@Repository
public class HdMaterialManagementDao extends BaseDao<HdMaterialManagement>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HdMaterialManagement.class;
	}

}