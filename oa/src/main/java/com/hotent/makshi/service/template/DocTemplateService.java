package com.hotent.makshi.service.template;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.template.DocTemplateDao;
import com.hotent.makshi.model.template.DocTemplate;

@Service
public class DocTemplateService extends BaseService<DocTemplate> {
	@Resource
	private DocTemplateDao dao;
	
	public DocTemplateService(){
		
	}
	
	@Override
	protected IEntityDao<DocTemplate, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}
