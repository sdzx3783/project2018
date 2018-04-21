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
import com.hotent.makshi.model.title.PersonalQualificationRegist;
import com.hotent.makshi.dao.title.PersonalQualificationRegistDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PersonalQualificationRegistService extends BaseService<PersonalQualificationRegist>
{
	@Resource
	private PersonalQualificationRegistDao dao;
	
	public PersonalQualificationRegistService()
	{
	}
	
	@Override
	protected IEntityDao<PersonalQualificationRegist,Long> getEntityDao() 
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
			PersonalQualificationRegist personalQualificationRegist=getPersonalQualificationRegist(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				personalQualificationRegist.setId(genId);
				this.add(personalQualificationRegist);
			}else{
				personalQualificationRegist.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personalQualificationRegist);
			}
			cmd.setBusinessKey(personalQualificationRegist.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PersonalQualificationRegist对象
	 * @param json
	 * @return
	 */
	public PersonalQualificationRegist getPersonalQualificationRegist(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonalQualificationRegist personalQualificationRegist = (PersonalQualificationRegist)JSONObject.toBean(obj, PersonalQualificationRegist.class);
		return personalQualificationRegist;
	}
	/**
	 * 保存 个人执业资格初始注册 信息
	 * @param personalQualificationRegist
	 */

	public void save(PersonalQualificationRegist personalQualificationRegist) throws Exception{
		Long id=personalQualificationRegist.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			personalQualificationRegist.setId(id);
		    this.add(personalQualificationRegist);
		}
		else{
		    this.update(personalQualificationRegist);
		}
	}
}
