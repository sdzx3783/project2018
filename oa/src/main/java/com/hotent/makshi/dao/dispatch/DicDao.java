
package com.hotent.makshi.dao.dispatch;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.dispatch.Dic;

@Repository
public class DicDao extends WfBaseDao<Dic>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Dic.class;
	}

}