package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysHistoryData;
/**
 *<pre>
 * 对象功能:历史数据 Dao类
 * 开发公司:宏天软件
 * 开发人员:ray
 * 创建时间:2015-06-26 22:47:29
 *</pre>
 */
@Repository
public class SysHistoryDataDao extends BaseDao<SysHistoryData>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysHistoryData.class;
	}

	/**
	 * 根据管理ID获取数据
	 * @param relateId
	 * @return
	 */
	public List<SysHistoryData> getByObjId(Long relateId){
		return this.getBySqlKey("getByObjId", relateId);
	}
	
	
	
}