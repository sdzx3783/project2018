package com.hotent.makshi.service.telList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.telList.PhoneList;
import com.hotent.makshi.dao.telList.PhoneListDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PhoneListService extends BaseService<PhoneList>
{
	@Resource
	private PhoneListDao dao;
	
	public PhoneListService()
	{
	}
	
	@Override
	protected IEntityDao<PhoneList,Long> getEntityDao() 
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
			PhoneList phoneList=getPhoneList(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				phoneList.setId(genId);
				this.add(phoneList);
			}else{
				phoneList.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(phoneList);
			}
			cmd.setBusinessKey(phoneList.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PhoneList对象
	 * @param json
	 * @return
	 */
	public PhoneList getPhoneList(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PhoneList phoneList = (PhoneList)JSONObject.toBean(obj, PhoneList.class);
		return phoneList;
	}
	/**
	 * 保存 手机号码列表 信息
	 * @param phoneList
	 */

	public void save(PhoneList phoneList) throws Exception{
		Long id=phoneList.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			phoneList.setId(id);
		    this.add(phoneList);
		}
		else{
		    this.update(phoneList);
		}
	}
}
