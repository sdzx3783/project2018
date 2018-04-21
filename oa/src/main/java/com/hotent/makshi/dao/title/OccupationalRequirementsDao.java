
package com.hotent.makshi.dao.title;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.title.OccupationalRequirements;

@Repository
public class OccupationalRequirementsDao extends BaseDao<OccupationalRequirements>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OccupationalRequirements.class;
	}

	public List<OccupationalRequirements> getByLinkId(Long linkId) {
		return this.getBySqlKey("getByLinkId", linkId);
	}
	
	public List<OccupationalRequirements> getByUserId(Long userId) {
		return this.getBySqlKey("getByUserId", userId);
	}

}