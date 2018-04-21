
package com.hotent.demo.dao.test;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.demo.model.test.Szys;

@Repository
public class SzysDao extends WfBaseDao<Szys>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Szys.class;
	}

}