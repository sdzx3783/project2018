package com.hotent.makshi.dao.template;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.template.DocTemplate;

@Repository
public class DocTemplateDao extends BaseDao<DocTemplate> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return DocTemplate.class;
	}

}
