package com.hotent.makshi.dao.finance;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.finance.UserInfoVo;

@Repository
public class UserInfoVoDao extends BaseDao<UserInfoVo> {

	@Override
	public Class<?> getEntityClass() {
		return UserInfoVo.class;
	}

}
