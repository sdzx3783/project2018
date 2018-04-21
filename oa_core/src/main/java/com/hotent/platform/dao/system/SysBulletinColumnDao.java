
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysBulletinColumn;

@Repository
public class SysBulletinColumnDao extends BaseDao<SysBulletinColumn>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysBulletinColumn.class;
	}

	public List<SysBulletinColumn> getPublic(QueryFilter queryFilter) {
		return this.getBySqlKey("getPublicColumn", queryFilter);
	}	

	public List<SysBulletinColumn> getColumn(Long companyId,
			Boolean isSuperAdmin) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyId", companyId);
		params.put("isSuperAdmin", isSuperAdmin);
		return this.getBySqlKey("getAll", params);
	}
	
	/**
	 * 根据栏目获取数量。
	 * @param bulletinColumn
	 * @return
	 */
	public Integer getAmountByAlias(SysBulletinColumn bulletinColumn){
		Map<String,Object> params=new HashMap<String,Object>();
		String alias=bulletinColumn.getAlias();
		Long id=bulletinColumn.getId();
		params.put("alias", alias);
		if(id!=null && id>0){
			params.put("id", id);
		}
		return (Integer)this.getOne("getAmountByAlias", params);
	}
}