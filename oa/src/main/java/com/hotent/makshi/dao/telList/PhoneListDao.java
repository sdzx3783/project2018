
package com.hotent.makshi.dao.telList;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.telList.PhoneList;

@Repository
public class PhoneListDao extends BaseDao<PhoneList>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PhoneList.class;
	}

}