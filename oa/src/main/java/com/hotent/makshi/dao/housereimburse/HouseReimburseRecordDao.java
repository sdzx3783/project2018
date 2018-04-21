package com.hotent.makshi.dao.housereimburse;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.housereimburse.HouseReimburseRecord;

@Repository
public class HouseReimburseRecordDao extends BaseDao<HouseReimburseRecord> {
	@Override
	public Class<?> getEntityClass() {
		return HouseReimburseRecord.class;
	}

}
