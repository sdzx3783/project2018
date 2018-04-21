
package com.hotent.makshi.dao.party;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.party.PartyFileOutApplication;

@Repository
public class PartyFileOutApplicationDao extends BaseDao<PartyFileOutApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PartyFileOutApplication.class;
	}

}