
package com.hotent.makshi.dao.party;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.party.PartyFileInApplication;

@Repository
public class PartyFileInApplicationDao extends BaseDao<PartyFileInApplication>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PartyFileInApplication.class;
	}

}