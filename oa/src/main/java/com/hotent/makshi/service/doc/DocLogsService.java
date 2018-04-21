package com.hotent.makshi.service.doc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.doc.DocLogsDao;
import com.hotent.makshi.model.doc.DocLogs;

@Service
public class DocLogsService extends BaseService<DocLogs> {
	@Resource
	private DocLogsDao dao;
	
	public DocLogsService(){
		
	}
	
	@Override
	protected IEntityDao<DocLogs, Long> getEntityDao() {
		return dao;
	}

	public void save(DocLogs doclogs) throws Exception{
		Long id=doclogs.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			doclogs.setId(id);
		    this.add(doclogs);
		}
		else{
		    this.update(doclogs);
		}
	}
	
	
	public List<DocLogs> getDocLogs(Map<String,Object> params,QueryFilter queryFilter){
		
		List<DocLogs> docList = dao.getBySqlKey("getDocLogs", params,queryFilter.getPageBean());
		return docList;
	}
}
