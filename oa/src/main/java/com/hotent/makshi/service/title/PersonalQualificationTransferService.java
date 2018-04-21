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
import com.hotent.makshi.model.title.PersonalQualificationTransfer;
import com.hotent.makshi.dao.title.PersonalQualificationTransferDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PersonalQualificationTransferService extends BaseService<PersonalQualificationTransfer>
{
	@Resource
	private PersonalQualificationTransferDao dao;
	
	public PersonalQualificationTransferService()
	{
	}
	
	@Override
	protected IEntityDao<PersonalQualificationTransfer,Long> getEntityDao() 
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
			PersonalQualificationTransfer personalQualificationTransfer=getPersonalQualificationTransfer(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				personalQualificationTransfer.setId(genId);
				this.add(personalQualificationTransfer);
			}else{
				personalQualificationTransfer.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personalQualificationTransfer);
			}
			cmd.setBusinessKey(personalQualificationTransfer.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PersonalQualificationTransfer对象
	 * @param json
	 * @return
	 */
	public PersonalQualificationTransfer getPersonalQualificationTransfer(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonalQualificationTransfer personalQualificationTransfer = (PersonalQualificationTransfer)JSONObject.toBean(obj, PersonalQualificationTransfer.class);
		return personalQualificationTransfer;
	}
	/**
	 * 保存 个人执业资格转入 信息
	 * @param personalQualificationTransfer
	 */

	public void save(PersonalQualificationTransfer personalQualificationTransfer) throws Exception{
		Long id=personalQualificationTransfer.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			personalQualificationTransfer.setId(id);
		    this.add(personalQualificationTransfer);
		}
		else{
		    this.update(personalQualificationTransfer);
		}
	}
}
