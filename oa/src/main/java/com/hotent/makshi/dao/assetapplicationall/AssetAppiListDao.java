
package com.hotent.makshi.dao.assetapplicationall;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.assetapplicationall.AssetAppiList;

@Repository
public class AssetAppiListDao extends BaseDao<AssetAppiList>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AssetAppiList.class;
	}

	/**
	 * 根据外键获取申购列表列表
	 * @param refId
	 * @return
	 */
	public List<AssetAppiList> getByMainId(Long refId) {
		return this.getBySqlKey("getAssetAppiListList", refId);
	}
	
	/**
	 * 根据外键删除申购列表
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}