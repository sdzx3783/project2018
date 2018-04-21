
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.VisitorRegisterInfo;

@Repository
public class VisitorRegisterInfoDao extends BaseDao<VisitorRegisterInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return VisitorRegisterInfo.class;
	}

}