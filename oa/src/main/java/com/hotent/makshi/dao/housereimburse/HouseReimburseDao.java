
package com.hotent.makshi.dao.housereimburse;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.housereimburse.HouseReimburse;

@Repository
public class HouseReimburseDao extends BaseDao<HouseReimburse> {
	@Override
	public Class<?> getEntityClass() {
		return HouseReimburse.class;
	}

}