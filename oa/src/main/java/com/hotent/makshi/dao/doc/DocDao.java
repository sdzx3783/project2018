package com.hotent.makshi.dao.doc;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.doc.Doc;

@Repository
public class DocDao extends BaseDao<Doc> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return Doc.class;
	}

}
