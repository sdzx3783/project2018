package com.hotent.makshi.dao.doc;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.doc.DocFile;

@Repository
public class DocFileDao extends BaseDao<DocFile> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return DocFile.class;
	}

}
