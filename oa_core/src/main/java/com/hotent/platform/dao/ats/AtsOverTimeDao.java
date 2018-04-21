package com.hotent.platform.dao.ats;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.ats.AtsOverTime;
/**
 *<pre>
 * 对象功能:考勤加班单 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2015-07-15 15:00:33
 *</pre>
 */
@Repository
public class AtsOverTimeDao extends BaseDao<AtsOverTime>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AtsOverTime.class;
	}

	
	
	
	
}