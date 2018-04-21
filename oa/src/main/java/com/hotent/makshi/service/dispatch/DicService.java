package com.hotent.makshi.service.dispatch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.dispatch.DicDao;
import com.hotent.makshi.model.dispatch.Dic;
import com.hotent.platform.model.system.Dictionary;

@Service
public class DicService extends WfBaseService<Dic> {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private DicDao dao;

	public DicService() {
	}

	@Override
	protected IEntityDao<Dic, Long> getEntityDao() {
		return dao;
	}

	public void save(Dic dic) throws Exception {
		Long id = dic.getDicId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			dic.setDicId(id);
			this.add(dic);
		} else {
			this.update(dic);
		}
	}

	public List<Dic> getByDocId(Long docId) {
		return dao.getBySqlKey("getByDocId", docId);
	}

	public List<Dic> getByDocIdAndDicName(Map param) {
		return dao.getBySqlKey("getByDocIdAndDicName", param);
	}

	/*public List<DocDic> getAllList(QueryFilter queryFilter) {
		return dao.getAll(queryFilter);
	}

	public DocDic getByDisId(Long DisId) {
		List<DocDic> list = dao.getBySqlKey("getByDisId",DisId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}*/

}
