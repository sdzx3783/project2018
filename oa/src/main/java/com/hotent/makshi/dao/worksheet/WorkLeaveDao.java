
package com.hotent.makshi.dao.worksheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.worksheet.WorkLeave;

@Repository
public class WorkLeaveDao extends BaseDao<WorkLeave>
{
	@Override
	public Class<?> getEntityClass()
	{
		return WorkLeave.class;
	}

	
	/**
	 * 根据条件获取出勤记录
	 * @param map
	 * @return
	 */
	public List<WorkLeave> getWorkLeaveList(Map<String,Object> map){
		List<WorkLeave> list=this.getBySqlKey("getAll", map);
		return list;
	}
	
	
	/**
	 * 批量替换缺勤数据
	 * @param map
	 * @return
	 */
	public void batchInsert(Map<String,Object> map){
		this.insert("replaceByMap", map);
	}
	

	public Map<String,Object> getVacationById(Long id){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		String stament=this.getIbatisMapperNamespace() + ".getVacation";
		return this.getSqlSessionTemplate().selectOne(stament, params);
	}
	
	/**
	 * 获取流程中的请假数据
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> geProcesstVacation(Map<String,Object> params){
		String stament=this.getIbatisMapperNamespace() + ".getVacation";
		return this.getSqlSessionTemplate().selectList(stament, params);
	}
}