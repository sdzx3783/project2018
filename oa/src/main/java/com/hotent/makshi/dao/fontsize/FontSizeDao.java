
package com.hotent.makshi.dao.fontsize;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.fontsize.FontSize;

@Repository
public class FontSizeDao extends BaseDao<FontSize>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FontSize.class;
	}

}