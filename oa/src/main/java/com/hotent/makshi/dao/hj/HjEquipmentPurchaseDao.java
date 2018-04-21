
package com.hotent.makshi.dao.hj;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.db.WfBaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.hj.HjEquipmentPurchase;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;

@Repository
public class HjEquipmentPurchaseDao extends WfBaseDao<HjEquipmentPurchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HjEquipmentPurchase.class;
	}
	public  List<HjEquipmentPurchase> getSelectByRecord(QueryFilter queryFilter) {
		return this.getBySqlKey("SelectByRecord",queryFilter);
	}
	public  HjEquipmentPurchase getSelectByWPID(Long id) {
		return this.getUnique("SelectByWPID",id);
	}
}