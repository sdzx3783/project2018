
package com.hotent.makshi.dao.inuserinfo;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.inuserinfo.UserInfo;

@Repository
public class UserInfoDao extends BaseDao<UserInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserInfo.class;
	}

	/**
	 * 根据外键获取员工入住信息列表
	 * @param refId
	 * @return
	 */
	public List<UserInfo> getByMainId(Long refId) {
		return this.getBySqlKey("getUserInfoList", refId);
	}
	
	/**
	 * 根据外键删除员工入住信息
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}

	public List<UserInfo> getByParam(HashMap param) {
		return this.getBySqlKey("getUserInfoListByParam", param);
	}
}