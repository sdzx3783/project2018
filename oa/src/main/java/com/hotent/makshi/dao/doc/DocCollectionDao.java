
package com.hotent.makshi.dao.doc;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.doc.DocCollection;

@Repository
public class DocCollectionDao extends BaseDao<DocCollection>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DocCollection.class;
	}

}