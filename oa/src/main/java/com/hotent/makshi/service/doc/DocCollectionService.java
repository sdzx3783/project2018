package com.hotent.makshi.service.doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.doc.DocCollectionDao;
import com.hotent.makshi.model.doc.DocCollection;


@Service
public class DocCollectionService extends BaseService<DocCollection>
{
	@Resource
	private DocCollectionDao dao;
	
	public DocCollectionService()
	{
	}
	
	@Override
	protected IEntityDao<DocCollection,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void save(DocCollection docCollection) throws Exception{
		Long id=docCollection.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			docCollection.setId(id);
		    this.add(docCollection);
		}
		else{
		    this.update(docCollection);
		}
	}

	public DocCollection getByIdAndUserid(Map<String,Object> params) {
		List<DocCollection> list = dao.getBySqlKey("getByIdAndUserId", params);
		if(list==null || list.size()<=0){
			return null;
		}
		return list.get(0);
	}
	

	public boolean isCollection(Long userid,Long dfid){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", dfid);
		params.put("userid", userid);
		DocCollection collection = getByIdAndUserid(params);
		if(collection!=null && collection.getState()==1){
			return true;
		}else{
			return false;
		}
	}
}
