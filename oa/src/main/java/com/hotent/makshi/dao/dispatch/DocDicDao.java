
package com.hotent.makshi.dao.dispatch;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.dispatch.DocDic;

@Repository
public class DocDicDao extends WfBaseDao<DocDic>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DocDic.class;
	}

}