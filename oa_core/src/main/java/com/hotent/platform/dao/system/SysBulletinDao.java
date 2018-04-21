
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Repository;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.BaseDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageList;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysBulletin;

@Repository
public class SysBulletinDao extends BaseDao<SysBulletin>
{
	@Resource 
	SysReadRecodeDao readRecodeDao;
	
	@Override
	public Class<?> getEntityClass()
	{
		return SysBulletin.class;
	}
	
	@Override
	public int delById(Long id) {
		readRecodeDao.deleteByParam(null, id, null);
		return super.delById(id);
	}
	
	public void delByColumnId(Long columnId) {
		readRecodeDao.deleteByParam(columnId, null, null);
		this.delBySqlKey("delByColumnId", columnId)  ;
	}

	public List<SysBulletin> getAllByAlias(String alias, PageBean pb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", ContextUtil.getCurrentUserId());
		map.put("alias", alias);
		return getBySqlKey("getAllByAlias",map,pb);
	}

	public List<SysBulletin> getAllByAlias(QueryFilter queryFilter) {
		return getBySqlKey("getAllByAlias",queryFilter);
	}
	
	
	/***修改公告状态**/
	public void updateStatus(Long id, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		this.update("updateStatus",map);
	}

	public PageList<SysBulletin> getByColumnId(QueryFilter queryFilter){
		PageList<SysBulletin> bulletins= (PageList<SysBulletin>) this.getBySqlKey("getByColumnId",queryFilter);
		return bulletins;
		
	}
}