package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.PwdStrategy;
/**
 *<pre>
 * 对象功能:sys_pwd_strategy Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-06-25 14:30:17
 *</pre>
 */
@Repository
public class PwdStrategyDao extends BaseDao<PwdStrategy>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PwdStrategy.class;
	}

	
	
	
	
}