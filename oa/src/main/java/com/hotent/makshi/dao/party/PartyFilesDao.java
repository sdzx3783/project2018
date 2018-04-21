
package com.hotent.makshi.dao.party;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.party.PartyFiles;

@Repository
public class PartyFilesDao extends BaseDao<PartyFiles>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PartyFiles.class;
	}

}