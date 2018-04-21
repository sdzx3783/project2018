
package com.hotent.makshi.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.common.FriendshipLink;

@Repository
public class FriendshipLinkDao extends BaseDao<FriendshipLink>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FriendshipLink.class;
	}
	
	
	/**
	 * 根据条件获取出勤记录
	 * @param map
	 * @return
	 */
	public List<FriendshipLink> getWorkSheetList(Map<String,Object> map){
		List<FriendshipLink> list=this.getBySqlKey("getAll", map);
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