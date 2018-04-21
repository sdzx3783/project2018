package com.hotent.platform.service.system;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.jsonobject.JSONObjectUtil;
import com.hotent.platform.dao.system.SysKnowMarkRelDao;
import com.hotent.platform.model.system.SysKnowMarkRel;

/**
 *<pre>
 * 对象功能:SYS_KNOW_MARK_REL Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:dyg
 * 创建时间:2015-12-31 15:26:01
 *</pre>
 */
@Service
public class SysKnowMarkRelService extends  BaseService<SysKnowMarkRel>
{
	@Resource
	private SysKnowMarkRelDao dao;
	
	
	
	public SysKnowMarkRelService()
	{
	}
	
	@Override
	protected IEntityDao<SysKnowMarkRel, Long> getEntityDao() 
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
			SysKnowMarkRel sysKnowMarkRel=getSysKnowMarkRel(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				sysKnowMarkRel.setId(genId);
				this.add(sysKnowMarkRel);
			}else{
				sysKnowMarkRel.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(sysKnowMarkRel);
			}
			cmd.setBusinessKey(sysKnowMarkRel.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取SysKnowMarkRel对象
	 * @param json
	 * @return
	 */
	public SysKnowMarkRel getSysKnowMarkRel(String json){
		
		if(StringUtil.isEmpty(json))return null;
		
		SysKnowMarkRel sysKnowMarkRel = JSONObjectUtil.toBean(json, SysKnowMarkRel.class);
		return sysKnowMarkRel;
	}
	
	/**
	 * 保存 SYS_KNOW_MARK_REL 信息
	 * @param sysKnowMarkRel
	 */
	public void save(SysKnowMarkRel sysKnowMarkRel){
		Long id=sysKnowMarkRel.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			sysKnowMarkRel.setId(id);
			this.add(sysKnowMarkRel);
		}
		else{
			this.update(sysKnowMarkRel);
		}
	}

	public void deleteByMarkIds(Long[] lAryId) {
		for(Long markId :lAryId){
			dao.delBySqlKey("deleteByMarkId", markId);
		}
	}
	
}
