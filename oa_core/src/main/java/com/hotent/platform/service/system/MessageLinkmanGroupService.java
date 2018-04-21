package com.hotent.platform.service.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.model.bpm.ProcessRun;
import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.MessageLinkmanGroup;
import com.hotent.platform.dao.system.MessageLinkmanGroupDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:常用联系人组 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ouxb
 * 创建时间:2015-07-29 10:29:57
 *</pre>
 */
@Service
public class MessageLinkmanGroupService extends  BaseService<MessageLinkmanGroup>
{
	@Resource
	private MessageLinkmanGroupDao dao;
	
	
	
	public MessageLinkmanGroupService()
	{
	}
	
	@Override
	protected IEntityDao<MessageLinkmanGroup, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 保存 常用联系人组 信息
	 * @param messageLinkmanGroup
	 */
	public void save(MessageLinkmanGroup messageLinkmanGroup){
		Long id=messageLinkmanGroup.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			messageLinkmanGroup.setId(id);
			messageLinkmanGroup.setCreatorId(ContextUtil.getCurrentUserId());
			messageLinkmanGroup.setCreateTime(new Date());
			this.add(messageLinkmanGroup);
		}
		else{
			this.update(messageLinkmanGroup);
		}
	}
	
}
