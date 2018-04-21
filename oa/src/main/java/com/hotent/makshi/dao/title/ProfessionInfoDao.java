
package com.hotent.makshi.dao.title;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.title.ProfessionInfo;

@Repository
public class ProfessionInfoDao extends BaseDao<ProfessionInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ProfessionInfo.class;
	}

}