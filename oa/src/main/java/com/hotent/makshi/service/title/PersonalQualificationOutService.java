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
import com.hotent.makshi.model.title.PersonalQualificationOut;
import com.hotent.makshi.dao.title.PersonalQualificationOutDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PersonalQualificationOutService extends BaseService<PersonalQualificationOut>
{
	@Resource
	private PersonalQualificationOutDao dao;
	
	public PersonalQualificationOutService()
	{
	}
	
	@Override
	protected IEntityDao<PersonalQualificationOut,Long> getEntityDao() 
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
			PersonalQualificationOut personalQualificationOut=getPersonalQualificationOut(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				personalQualificationOut.setId(genId);
				this.add(personalQualificationOut);
			}else{
				personalQualificationOut.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personalQualificationOut);
			}
			cmd.setBusinessKey(personalQualificationOut.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PersonalQualificationOut对象
	 * @param json
	 * @return
	 */
	public PersonalQualificationOut getPersonalQualificationOut(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonalQualificationOut personalQualificationOut = (PersonalQualificationOut)JSONObject.toBean(obj, PersonalQualificationOut.class);
		return personalQualificationOut;
	}
	/**
	 * 保存 个人执业资格转出 信息
	 * @param personalQualificationOut
	 */

	public void save(PersonalQualificationOut personalQualificationOut) throws Exception{
		Long id=personalQualificationOut.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			personalQualificationOut.setId(id);
		    this.add(personalQualificationOut);
		}
		else{
		    this.update(personalQualificationOut);
		}
	}
}
