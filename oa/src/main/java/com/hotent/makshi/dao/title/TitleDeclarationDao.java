
package com.hotent.makshi.dao.title;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.title.TitleDeclaration;

@Repository
public class TitleDeclarationDao extends BaseDao<TitleDeclaration>
{
	@Override
	public Class<?> getEntityClass()
	{
		return TitleDeclaration.class;
	}

}