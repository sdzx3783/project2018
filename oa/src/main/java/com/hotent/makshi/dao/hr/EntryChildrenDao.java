
package com.hotent.makshi.dao.hr;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.hr.EntryChildren;

@Repository
public class EntryChildrenDao extends WfBaseDao<EntryChildren>
{
	@Override
	public Class<?> getEntityClass()
	{
		return EntryChildren.class;
	}

	public List<EntryChildren> getByMainId(Long refId) {
		return this.getBySqlKey("getEntryChildrenList", refId);
	}
	
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}