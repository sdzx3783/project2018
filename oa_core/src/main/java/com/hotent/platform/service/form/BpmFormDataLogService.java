package com.hotent.platform.service.form;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.form.BpmFormDataLogDao;
import com.hotent.platform.model.form.BpmFormDataLog;

/**
 * <pre>
 * 对象功能:在线表单数据日志记录，记录为什么会丢失数据 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ouxb
 * 创建时间:2015-11-30 17:15:23
 * </pre>
 */
@Service
public class BpmFormDataLogService extends BaseService<BpmFormDataLog> {
	@Resource
	private BpmFormDataLogDao dao;

	public BpmFormDataLogService() {
	}

	@Override
	protected IEntityDao<BpmFormDataLog, Long> getEntityDao() {
		return dao;
	}
	
	/**
	 *	插入数据 
	 */
	public void insertFormDataLog(String businessKey,String formData,String actDefId,String nodeId){
		BpmFormDataLog entity = new BpmFormDataLog();
		entity.setId(UniqueIdUtil.genId());
		entity.setBusinessKey(businessKey);
		entity.setFormData(formData);
		entity.setActDefId(actDefId);
		entity.setNodeId(nodeId);
		entity.setCreateBy(ContextUtil.getCurrentUserId());
		entity.setCreatetime(new Date());
		this.add(entity);
	}
	
}










