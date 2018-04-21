package com.hotent.makshi.service.dispatch;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.dispatch.DocDicDao;
import com.hotent.makshi.model.dispatch.DocDic;

@Service
public class DocDicService extends WfBaseService<DocDic> {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private DocDicDao dao;

	public DocDicService() {
	}

	@Override
	protected IEntityDao<DocDic, Long> getEntityDao() {
		return dao;
	}

	public void save(DocDic docDic) throws Exception {
		Long id = docDic.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			docDic.setId(id);
			this.add(docDic);
		} else {
			this.update(docDic);
		}
	}

	public List<DocDic> getAllList(QueryFilter queryFilter) {
		return dao.getAll(queryFilter);
	}

	public DocDic getByDisId(Long DisId) {
		List<DocDic> list = dao.getBySqlKey("getByDisId",DisId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
