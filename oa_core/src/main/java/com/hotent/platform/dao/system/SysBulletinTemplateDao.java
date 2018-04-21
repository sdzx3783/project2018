
package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.platform.model.system.SysBulletinTemplate;

@Repository
public class SysBulletinTemplateDao extends BaseDao<SysBulletinTemplate>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysBulletinTemplate.class;
	}

}