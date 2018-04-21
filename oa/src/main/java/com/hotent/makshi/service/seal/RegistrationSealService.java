package com.hotent.makshi.service.seal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.seal.RegistrationSeal;
import com.hotent.makshi.dao.seal.RegistrationSealDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class RegistrationSealService extends BaseService<RegistrationSeal>
{
	@Resource
	private RegistrationSealDao dao;
	
	public RegistrationSealService()
	{
	}
	
	@Override
	protected IEntityDao<RegistrationSeal,Long> getEntityDao() 
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
			RegistrationSeal registrationSeal=getRegistrationSeal(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				registrationSeal.setId(genId);
				this.add(registrationSeal);
			}else{
				registrationSeal.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(registrationSeal);
			}
			cmd.setBusinessKey(registrationSeal.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RegistrationSeal对象
	 * @param json
	 * @return
	 */
	public RegistrationSeal getRegistrationSeal(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		RegistrationSeal registrationSeal = (RegistrationSeal)JSONObject.toBean(obj, RegistrationSeal.class);
		return registrationSeal;
	}
	/**
	 * 保存 注册证印章 信息
	 * @param registrationSeal
	 */

	public void save(RegistrationSeal registrationSeal) throws Exception{
		Long id=registrationSeal.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			registrationSeal.setId(id);
		    this.add(registrationSeal);
		}
		else{
		    this.update(registrationSeal);
		}
	}
}
