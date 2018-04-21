
package com.hotent.makshi.dao.reninfo;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.reninfo.RentInfo;

@Repository
public class RentInfoDao extends WfBaseDao<RentInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return RentInfo.class;
	}

	/**
	 * 根据外键获取拟租信息列表
	 * @param refId
	 * @return
	 */
	public List<RentInfo> getByMainId(Long refId) {
		return this.getBySqlKey("getRentInfoList", refId);
	}
	
	/**
	 * 根据外键删除拟租信息
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}