package com.hotent.makshi.service.title;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.title.TitleDeclaration;
import com.hotent.makshi.dao.title.TitleDeclarationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class TitleDeclarationService extends BaseService<TitleDeclaration>
{
	@Resource
	private TitleDeclarationDao dao;
	
	public TitleDeclarationService()
	{
	}
	
	@Override
	protected IEntityDao<TitleDeclaration,Long> getEntityDao() 
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
			TitleDeclaration titleDeclaration=getTitleDeclaration(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				titleDeclaration.setId(genId);
				this.add(titleDeclaration);
			}else{
				titleDeclaration.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(titleDeclaration);
			}
			cmd.setBusinessKey(titleDeclaration.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取TitleDeclaration对象
	 * @param json
	 * @return
	 */
	public TitleDeclaration getTitleDeclaration(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		TitleDeclaration titleDeclaration = (TitleDeclaration)JSONObject.toBean(obj, TitleDeclaration.class);
		return titleDeclaration;
	}
	/**
	 * 保存 职称申报 信息
	 * @param titleDeclaration
	 */

	public void save(TitleDeclaration titleDeclaration) throws Exception{
		Long id=titleDeclaration.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			titleDeclaration.setId(id);
		    this.add(titleDeclaration);
		}
		else{
		    this.update(titleDeclaration);
		}
	}
}
