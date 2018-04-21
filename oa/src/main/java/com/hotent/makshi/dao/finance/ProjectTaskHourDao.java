
package com.hotent.makshi.dao.finance;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.finance.ProjectTaskHour;

@Repository
public class ProjectTaskHourDao extends BaseDao<ProjectTaskHour>
{
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Override
	public Class<?> getEntityClass()
	{
		return ProjectTaskHour.class;
	}

	/**
	 * 根据外键获取项目任务工时列表
	 * @param refId
	 * @return
	 */
	public List<ProjectTaskHour> getByMainId(Long refId) {
		return this.getBySqlKey("getProjectTaskHourList", refId);
	}
	
	/**
	 * 根据外键删除项目任务工时
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
	
	public List<ProjectTaskHour> queryProHour(Map<String,Object>  filter){
		/*Object object = filter.get("orgid");
		String orgPath="-1";
		if(object!=null){
			String orgId=(String) object;
			String sql="select path from sys_org where orgid="+Long.valueOf(orgId);
			String string = jdbcTemplate.queryForObject(sql, String.class);
			if(StringUtils.isNotEmpty(string)){
				orgPath=string;
				filter.put("path", orgPath);
			}
		}*/
		return this.getBySqlKey("queryProHour", filter);
	}
	
	public List<Map<String,String>> getUserIds(Map<String,Object> filter){
		/*Object object = filter.get("orgid");
		String orgPath="-1";
		if(object!=null){
			String orgId=(String) object;
			String sql="select path from sys_org where orgid="+Long.valueOf(orgId);
			String string = jdbcTemplate.queryForObject(sql, String.class);
			if(StringUtils.isNotEmpty(string)){
				orgPath=string;
				filter.put("path", orgPath);
			}
		}*/
		List list = this.getListBySqlKey("getUserIds", filter);
		if(list!=null || list.size()>0){
			return list;
		}
		return null;
	}
	
}