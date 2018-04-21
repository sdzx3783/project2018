package com.hotent.platform.service.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.system.SysBulletinTemplate;
import com.hotent.platform.dao.system.SysBulletinTemplateDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class SysBulletinTemplateService extends BaseService<SysBulletinTemplate>
{
	@Resource
	private SysBulletinTemplateDao dao;
	
	public SysBulletinTemplateService()
	{
	}
	
	@Override
	protected IEntityDao<SysBulletinTemplate,Long> getEntityDao() 
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
			SysBulletinTemplate sysBulletinTemplate=getSysBulletinTemplate(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				sysBulletinTemplate.setId(genId);
				this.add(sysBulletinTemplate);
			}else{
				sysBulletinTemplate.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(sysBulletinTemplate);
			}
			cmd.setBusinessKey(sysBulletinTemplate.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取SysBulletinTemplate对象
	 * @param json
	 * @return
	 */
	public SysBulletinTemplate getSysBulletinTemplate(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		SysBulletinTemplate sysBulletinTemplate = (SysBulletinTemplate)JSONObject.toBean(obj, SysBulletinTemplate.class);
		return sysBulletinTemplate;
	}
	/**
	 * 保存 测试表单 信息
	 * @param sysBulletinTemplate
	 */
	public void save(SysBulletinTemplate sysBulletinTemplate) throws Exception{
		Long id=sysBulletinTemplate.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			sysBulletinTemplate.setId(id);	
		    this.add(sysBulletinTemplate);
		}
		else{
		   this.update(sysBulletinTemplate);
		}
	}

	public List<SysBulletinTemplate> getAllList(QueryFilter filter) {
		return dao.getBySqlKey("getAllList", filter);
	}
}
