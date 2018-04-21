
package com.hotent.makshi.dao.worksheet;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.worksheet.WorkSheet;

@Repository
public class WorkSheetDao extends BaseDao<WorkSheet>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WorkSheet.class;
	}
	
	
	/**
	 * 根据条件获取出勤记录
	 * @param map
	 * @return
	 */
	public List<WorkSheet> getWorkSheetList(Map<String,Object> map){
		List<WorkSheet> list=this.getBySqlKey("getAll", map);
		return list;
	}
	
	
	/**
	 * 批量替换出勤数据
	 * @param map
	 * @return
	 */
	public void batchInsert(Map<String,Object> map){
		this.insert("replaceByMap", map);
	}

}