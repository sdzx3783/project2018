package com.hotent.platform.service.system;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysHistoryDataDao;
import com.hotent.platform.model.system.SysHistoryData;
import com.hotent.platform.model.system.SysUser;

/**
 *<pre>
 * 对象功能:历史数据 Service类
 * 开发公司:宏天软件
 * 开发人员:ray
 * 创建时间:2015-06-26 22:47:29
 *</pre>
 */
@Service
public class SysHistoryDataService extends  BaseService<SysHistoryData>
{
	@Resource
	private SysHistoryDataDao dao;
	
	
	
	public SysHistoryDataService()
	{
	}
	
	@Override
	protected IEntityDao<SysHistoryData, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 添加历史数据。
	 * @param dataType
	 * @param content
	 * @param relateId
	 */
	public void add(String dataType,String subject, String content,Long relateId){
		SysUser sysUser=(SysUser) ContextUtil.getCurrentUser();
		
		SysHistoryData data=new SysHistoryData();
		data.setId(UniqueIdUtil.genId());
		data.setContent(content);
		data.setSubject(subject);
		data.setType(dataType);
		data.setObjId(relateId);
		data.setCreatetime(new Date());
		data.setCreator(sysUser.getFullname());
		
		dao.add(data);
		
	}
	
	public List<SysHistoryData> getByObjId(Long relateId){
		return dao.getByObjId(relateId);
	}
	
	
	
	
}
