
package com.hotent.makshi.dao.worksheet;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.worksheet.WorkCount;

@Repository
public class WorkCountDao extends BaseDao<WorkCount>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WorkCount.class;
	}

	
	
	/**
	 * 批量插入出勤统计数据
	 * @param map
	 * @return
	 */
	public void batchInsert(Map<String,Object> map){
		this.insert("replaceByMap", map);
	}
}