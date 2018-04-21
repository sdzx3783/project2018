package com.hotent.platform.service.form;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.dao.form.SubTableSortDao;
import com.hotent.platform.model.form.SubTableSort;

/**
 *<pre>
 * 对象功能:bpm_subtable_sort Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2015-03-12 13:56:01
 *</pre>
 */
@Service
public class SubTableSortService extends  BaseService<SubTableSort>
{
	@Resource
	private SubTableSortDao dao;
	
	
	
	public SubTableSortService()
	{
	}
	
	@Override
	protected IEntityDao<SubTableSort, Long> getEntityDao() 
	{
		return dao;
	}

	public List<SubTableSort> getByActDefKeyAndTableName(String actDefKey,
			List<String> tableName) {
		
		return dao.getByActDefKeyAndTableName(actDefKey,tableName);
	}

	public void addList(String actDefKey,List<SubTableSort> subTableSortList) {
		dao.delByActDefKey(actDefKey);	// 先删除，后保存
		for (SubTableSort subTableSort : subTableSortList) {
			dao.add(subTableSort);
		}
		
	}

	public SubTableSort getByAKeyAndTName(String actDefKey, String tableName) {
		return dao.getByAKeyAndTName(actDefKey,tableName);
	}
	
}
