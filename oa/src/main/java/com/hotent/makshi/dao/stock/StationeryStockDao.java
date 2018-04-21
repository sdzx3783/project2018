
package com.hotent.makshi.dao.stock;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.stock.StationeryStock;

@Repository
public class StationeryStockDao extends WfBaseDao<StationeryStock>
{
	@Override
	public Class<?> getEntityClass()
	{
		return StationeryStock.class;
	}

	/**
	 * 根据外键获取文具库存表列表
	 * @param refId
	 * @return
	 */
	public List<StationeryStock> getByMainId(Long refId) {
		return this.getBySqlKey("getStationeryStockList", refId);
	}
	
	/**
	 * 根据外键删除文具库存表
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}