package com.hotent.makshi.dao.doc;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.doc.DocLogs;

@Repository
public class DocLogsDao extends BaseDao<DocLogs> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return DocLogs.class;
	}

}
