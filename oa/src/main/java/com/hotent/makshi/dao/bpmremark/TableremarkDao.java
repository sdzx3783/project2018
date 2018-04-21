
package com.hotent.makshi.dao.bpmremark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.bpmremark.Tableremark;

@Repository
public class TableremarkDao extends BaseDao<Tableremark>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Tableremark.class;
	}

}