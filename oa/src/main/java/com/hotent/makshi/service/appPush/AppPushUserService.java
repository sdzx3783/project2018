package com.hotent.makshi.service.appPush;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.appPush.AppPushUserDao;
import com.hotent.makshi.model.appPush.AppPushUser;


@Service
public class AppPushUserService extends BaseService<AppPushUser>
{
	@Resource
	private AppPushUserDao dao;
	
	public AppPushUserService()
	{
	}
	
	@Override
	protected IEntityDao<AppPushUser,Long> getEntityDao() 
	{
		return dao;
	}
	
	@Transactional
	public List<AppPushUser> getByOrgId(Long orgId){
		return dao.getBySqlKey("getByOrgId", orgId);
	}
}
