
package com.hotent.makshi.dao.template;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.template.RuleManager;

@Repository
public class RuleManagerDao extends BaseDao<RuleManager>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RuleManager.class;
	}

}