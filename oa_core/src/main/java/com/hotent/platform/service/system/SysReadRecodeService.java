package com.hotent.platform.service.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.model.bpm.ProcessRun;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;

import net.sf.json.JSONObject;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.SysReadRecode;
import com.hotent.platform.dao.system.SysReadRecodeDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:已读记录 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miaojf
 * 创建时间:2015-07-13 18:44:29
 *</pre>
 */
@Service
public class SysReadRecodeService extends  BaseService<SysReadRecode>
{
	@Resource
	private SysReadRecodeDao dao;
	
	
	
	public SysReadRecodeService()
	{
	}
	
	@Override
	protected IEntityDao<SysReadRecode, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			SysReadRecode sysReadRecode=getSysReadRecode(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				sysReadRecode.setId(genId);
				this.add(sysReadRecode);
			}else{
				sysReadRecode.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(sysReadRecode);
			}
			cmd.setBusinessKey(sysReadRecode.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取SysReadRecode对象
	 * @param json
	 * @return
	 */
	public SysReadRecode getSysReadRecode(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		SysReadRecode sysReadRecode = (SysReadRecode)JSONObject.toBean(obj, SysReadRecode.class);
		return sysReadRecode;
	}
	
	/**
	 * 保存 已读记录 信息
	 * @param sysReadRecode
	 */
	public void save(SysReadRecode sysReadRecode){
		Long id=sysReadRecode.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			sysReadRecode.setId(id);
			this.add(sysReadRecode);
		}
		else{
			this.update(sysReadRecode);
		}
	}
	/**判断是否已经存在*/
	public boolean hasRead(Long id, Long userId) {
		return dao.hasRead(id,userId);
	}
	
	
	/**通过参数删除已读记录，至少有一个参数不为空*/
	public void deleteByParam(Long groupId,Long objectId,Long userId){
		dao.deleteByParam(groupId,objectId,userId);
	}
	
}
