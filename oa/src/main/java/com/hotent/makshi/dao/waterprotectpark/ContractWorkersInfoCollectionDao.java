
package com.hotent.makshi.dao.waterprotectpark;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.waterprotectpark.ContractWorkersInfoCollection;

@Repository
public class ContractWorkersInfoCollectionDao extends BaseDao<ContractWorkersInfoCollection>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ContractWorkersInfoCollection.class;
	}

}