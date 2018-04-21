
package com.hotent.makshi.dao.makechapter;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.makechapter.MakeChapter;

@Repository
public class MakeChapterDao extends WfBaseDao<MakeChapter>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MakeChapter.class;
	}

}