package com.hotent.makshi.service.bpmremark;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.bpmremark.Tableremark;
import com.hotent.makshi.dao.bpmremark.TableremarkDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class TableremarkService extends BaseService<Tableremark>
{
	@Resource
	private TableremarkDao dao;
	
	public TableremarkService()
	{
	}
	
	@Override
	protected IEntityDao<Tableremark,Long> getEntityDao() 
	{
		return dao;
	}

	public Tableremark getRemark(Map<String, Object> param) {
		 List<Tableremark> list = dao.getBySqlKey("getRemarkById",param);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
