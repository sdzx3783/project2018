package com.hotent.platform.dao.form;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.form.BpmMobileFormDef;
/**
 *<pre>
 * 对象功能:手机表单 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2015-10-28 10:52:49
 *</pre>
 */
@Repository
public class BpmMobileFormDefDao extends BaseDao<BpmMobileFormDef>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BpmMobileFormDef.class;
	}

	
	/**
	 * 根据表单key获取手机表单定义。
	 * @param formKey
	 * @return
	 */
	public BpmMobileFormDef getDefaultByFormKey(String formKey){
		BpmMobileFormDef formDef=this.getUnique("getDefaultByFormKey", formKey);
		return formDef;
	}
	
	
	
	
	
	
	
}