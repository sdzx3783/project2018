
package com.hotent.makshi.dao.qualification;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.qualification.Jyzz;

@Repository
public class JyzzDao extends BaseDao<Jyzz>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Jyzz.class;
	}

	/**
	 * 根据外键获取借阅资质列表
	 * @param refId
	 * @return
	 */
	public List<Jyzz> getByMainId(Long refId) {
		return this.getBySqlKey("getJyzzList", refId);
	}
	
	/**
	 * 根据外键删除借阅资质
	 * @param refId
	 * @return
	 */
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
}